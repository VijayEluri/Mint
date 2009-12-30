header{
	package org.mint.src;
	
	import org.mint.util.*;
	
	import java.util.*;
	import java.io.*;
}



class MintParser extends Parser;
options {
	k = 2;                           // two token lookahead
	exportVocab=MintParser;                // Call its vocabulary "Java"
	codeGenMakeSwitchThreshold = 2;  // Some optimizations
	codeGenBitsetTestThreshold = 3;
	defaultErrorHandler = false;     // Don't generate parser error handlers
	buildAST = true;
}

tokens {
	MODIFIERS; SLIST; ALIST; PROGRAM; ARRAY;
	PARAMETERS; PARAMETER_DEF;FUNCTION_CALL;
	POST_INC; POST_DEC; METHOD_CALL; EXPR;
	UNARY_MINUS; UNARY_PLUS; ELIST; FOR_INIT; FOR_CONDITION; 
	FOR_ITERATOR; EMPTY_STAT; CTOR_CALL; COMPLEX;
	REAL_PART; IMAG_PART;
}

{
	StringBuffer src;
	MintEngine compiler;
	
	public void setSourceBuffer(String s){
		src = new StringBuffer(s);
	}
	
	
	
	public void setCompiler(MintEngine compiler_){
		compiler = compiler_;
	}
	
	private int getLineCount(){
		String FileName = this.getFilename();
		int count = 0;
		try{
			FileReader reader = new FileReader(FileName);
			LineNumberReader r = new LineNumberReader( reader );
			
			while( r.readLine() != null)
				++count;

				r.close();
				reader.close();
		}
		
		catch( IOException ioe){
			System.out.println(ioe);
		}
		
		return count;
	}
	
	private String getErrorLine(int lineNo, int columnNo){
		
		String msg = new String();
		StringBuffer pointTo = new StringBuffer();
		String FileName = this.getFilename();

		try{
			FileReader reader = new FileReader(FileName);
			LineNumberReader r = new LineNumberReader( reader );

			for(int i = 0; i < lineNo - 1; i++)
				msg = r.readLine();
		
				r.close();
				reader.close();
		}
		
		catch( IOException ioe){
			System.out.println(ioe);
		}
		
		String errorMessage = "Error, Line: " + lineNo +"\n";
		errorMessage += "Here: " + msg;
		
		return  ( errorMessage );
	}
}
	
program	:	(statement)* EOF!
	;


// Compound statement.  This is used in many contexts:
//   Inside a class definition prefixed with "static":
//      it is a class initializer
//   Inside a class definition without "static":
//      it is an instance initializer
//   As the body of a method
//   As a completely indepdent braced block of code inside a method
//      it starts a new scope for variable definitions

compoundStatement
	:	lc:LCURLY^ {#lc.setType(SLIST);}
			// include the (possibly-empty) list of statements
			(statement)*
		RCURLY!
	;


statement
	// A list of statements in curly braces -- start a new scope!
	:	compoundStatement

	// An expression statement.  This could be a method call,
	// assignment statement, or any other expression evaluated for
	// side-effects.
	|	expression SEMI!

	// If-else statement
	|	"if"^ LPAREN! expression RPAREN! statement
		(
			// CONFLICT: the old "dangling-else" problem...
			//           ANTLR generates proper code matching
			//			 as soon as possible.  Hush warning.
			options {
				warnWhenFollowAmbig = false;
			}
		:
			"else"! statement
		)?

	// For statement
	|	"for"^
			LPAREN!
				forInit SEMI!   // initializer
				forCond	SEMI!   // condition test
				forIter         // updater
			RPAREN!
			statement                     // statement to loop over

	// While statement
	|	"while"^ LPAREN! expression RPAREN! statement

	// do-while statement
	|	"do"^ statement "while"! LPAREN! expression RPAREN! SEMI!

	// get out of a loop (or switch)
	|	"break"^ (IDENT)? SEMI!

	// do next iteration of a loop
	|	"continue"^ (IDENT)? SEMI!

	// Return an expression
	|	"return"^ (expression)? SEMI!


	// empty statement
	|	s:SEMI {#s.setType(EMPTY_STAT);}
	;

// The initializer for a for loop
forInit
		// if it looks like a declaration, it is
	:	expression
		{#forInit = #(#[FOR_INIT,"FOR_INIT"],#forInit);}
	;

forCond
	:	expression
		{#forCond = #(#[FOR_CONDITION,"FOR_CONDITION"],#forCond);}
	;

forIter
	:
		expression
		{#forIter = #(#[FOR_ITERATOR,"FOR_ITERATOR"],#forIter);}
	;


// the mother of all expressions
expression
	:	assignmentExpression
		{#expression = #(#[EXPR,"EXPR"],#expression);}
	;


// This is a list of expressions.
expressionList
	:	expression (COMMA! expression)*
		{#expressionList = #(#[ELIST,"ELIST"], expressionList);}
	;


// assignment expression (level 13)
assignmentExpression
	:	logicalOrExpression
	(   (	ASSIGN^
            |   PLUS_ASSIGN^
            |   MINUS_ASSIGN^
            |   STAR_ASSIGN^
            |   DIV_ASSIGN^
            |   MOD_ASSIGN^
            )
			assignmentExpression
	)?
	;



// logical or (||)  (level 11)
logicalOrExpression
	:	logicalAndExpression (LOR^ logicalAndExpression)*
	;


// logical and (&&)  (level 10)
logicalAndExpression
	:	equalityExpression (LAND^ equalityExpression)*
	;


// equality/inequality (==/!=) (level 6)
equalityExpression
	:	relationalExpression ((NOT_EQUAL^ | EQUAL^) relationalExpression)*
	;


// boolean relational expressions (level 5)
relationalExpression
	:	additiveExpression
		(	(	(	LT^
				|	GT^
				|	LE^
				|	GE^
				)
				additiveExpression
			)*
		)
	;



// binary addition/subtraction (level 3)
additiveExpression
	:	multiplicativeExpression ((PLUS^ | MINUS^) multiplicativeExpression)*
	;


// multiplication/division/modulo (level 2)
multiplicativeExpression
	:	powerExpression ((STAR^ | DIV^ | MOD^ ) powerExpression)*
	;

powerExpression	
	:	unaryExpression ( POWER^ unaryExpression)*
	;

unaryExpression
	:	INC^ unaryExpression
	|	DEC^ unaryExpression
	|	MINUS^ {#MINUS.setType(UNARY_MINUS);} unaryExpression
	|	PLUS^  {#PLUS.setType(UNARY_PLUS);} unaryExpression
	|	postfixExpression
	;


// qualified names, array expressions, method invocation, post inc/dec
postfixExpression
	:	primaryExpression // start with a primary
		(

		lp:LPAREN! {#lp.setType(FUNCTION_CALL);}
				argList
			RPAREN!
		{#postfixExpression = #(#[FUNCTION_CALL,"FUNCTION_CALL"],#postfixExpression);}
		)*

		// possibly add on a post-increment or post-decrement.
		// allows INC/DEC on too much, but semantics can check
		(	in:INC^ {#in.setType(POST_INC);}
	 	|	de:DEC^ {#de.setType(POST_DEC);}
		|	// nothing
		)
		
	
	;

// the basic element of an expression
primaryExpression
	:	IDENT		
	|	constant
	|	"true"
	|	"false"
	|	"null"
	|	LPAREN! assignmentExpression RPAREN!
	|	array
	;

argList
	:	(	expressionList
		|	/*nothing*/
		)
	;


arrayList
	:	argList ( SEMI! argList)*
	{#arrayList = #(#[ALIST,"ALIST"],#arrayList);}
	;

array	: lp:LBRACK^ arrayList RBRACK!
	{ #lp.setType(ARRAY); }
	
	;

constant:	
	DOUBLE 
	|	STRING
	;


class MintLexer extends Lexer;

options{
exportVocab = MintParser;
defaultErrorHandler = false;
k = 3;
}


WS	:	
	(	' '
	|	'\t'
	|	'\n' {  newline(); }
	|	'\r' 
	)
		{ _ttype = Token.SKIP; }
	;

// OPERATORS
LPAREN			:	'('		;	RPAREN			:	')'		;
LBRACK			:	'['		;	RBRACK			:	']'		;
LCURLY			:	'{'		;	RCURLY			:	'}'		;
COLON			:	':'		;	COMMA			:	','		;
SEMI			:	';'		;	DOT				:	'.'		;
ASSIGN			:	'='		;	DIV				:	'/'		;	
DIV_ASSIGN		:	"/="	;	PLUS			:	'+'		;	
PLUS_ASSIGN		:	"+="	;	MINUS			:	'-'		;	
MINUS_ASSIGN	:	"-="	;	STAR			:	'*'		;	
STAR_ASSIGN		:	"*="	;	MOD				:	'%'		;	
MOD_ASSIGN		:	"%="	;	POWER			:	'^'		;	
POWER_ASSIGN	:	"^="	;	NOT				:	'!'		;
INC				:	"++"	;	DEC				:	"--"	;
LT				:	'<'		;	GT				:	'>'		;
LE				:	"<="	;	GE				:	">="	;
NOT_EQUAL		:	"!="	;	EQUAL			:	"=="	;	
LAND			:	"&&"	;	LOR				:	"||"	;


DOUBLE	:(DIGIT)+ ( '.' (DIGIT)+ )?
	;

IDENT	
	:
	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|DIGIT)*
	;
 
ESC	:	
		'\\'
		(	'n' { $setText('\n'); }
		|	'r' { $setText('\r'); }
		|	't' { $setText('\t'); }
		|	'"' { $setText('\"'); }
		|	'\'' { $setText('\''); }
		|	'\\' { $setText('\\'); }
		)
	;

STRING
	:	'\''	 ( ESC | ~'\'' )* 	'\''
/*	{
		String s = $getText;
		String s1 = new String();
		char c = '\'';
		int start = s.indexOf(c) + 1;
		int end = s.lastIndexOf(c);

		if( start != -1 && end != -1 )
			s1 = s.substring(start,end);
		else
			s1 = s;

		$setText(s1);
	}
*/
	;

// Single-line comments
SL_COMMENT
	:	"//"
		(~('\n'|'\r'))* ('\n'|'\r'('\n')?)
		{$setType(Token.SKIP); newline();}
	;

// multiple-line comments
ML_COMMENT
	:	"/*"
		(	options {
				generateAmbigWarnings=false;
			}
		:
			{ LA(2)!='/' }? '*'
		|	'\r' '\n'		{newline();}
		|	'\r'			{newline();}
		|	'\n'			{newline();}
		|	~('*'|'\n'|'\r')
		)*
		"*/"
		{$setType(Token.SKIP);}
	;

protected
VOCAB
	:	'\3'..'\377'
	;

protected
DIGIT
	:	'0'..'9'
	;

protected
EXPONENT
	:	('e' | 'E') ('+'|'-')?
	;	