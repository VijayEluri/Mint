// $ANTLR 2.7.2a2 (20020112-1): "MintParser.g" -> "MintParser.java"$

	package org.mint.src;
	
	import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.mint.util.MintEngine;

import antlr.ASTPair;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.collections.impl.ASTArray;
import antlr.collections.impl.BitSet;

public class MintParser extends antlr.LLkParser
       implements MintParserTokenTypes
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

protected MintParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public MintParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected MintParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public MintParser(TokenStream lexer) {
  this(lexer,2);
}

public MintParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
}

	public final void program() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST program_AST = null;
		
		{
		_loop3:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				statement();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop3;
			}
			
		} while (true);
		}
		match(Token.EOF_TYPE);
		program_AST = (AST)currentAST.root;
		returnAST = program_AST;
	}
	
	public final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		Token  s = null;
		AST s_AST = null;
		
		switch ( LA(1)) {
		case LCURLY:
		{
			compoundStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LPAREN:
		case IDENT:
		case PLUS:
		case MINUS:
		case INC:
		case DEC:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		case LBRACK:
		case DOUBLE:
		case STRING:
		{
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_if:
		{
			AST tmp3_AST = null;
			tmp3_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp3_AST);
			match(LITERAL_if);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			statement();
			astFactory.addASTChild(currentAST, returnAST);
			{
			if ((LA(1)==LITERAL_else) && (_tokenSet_0.member(LA(2)))) {
				match(LITERAL_else);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_for:
		{
			AST tmp7_AST = null;
			tmp7_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp7_AST);
			match(LITERAL_for);
			match(LPAREN);
			forInit();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			forCond();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			forIter();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			statement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_while:
		{
			AST tmp12_AST = null;
			tmp12_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp12_AST);
			match(LITERAL_while);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			statement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_do:
		{
			AST tmp15_AST = null;
			tmp15_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp15_AST);
			match(LITERAL_do);
			statement();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_while);
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			match(SEMI);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_break:
		{
			AST tmp20_AST = null;
			tmp20_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp20_AST);
			match(LITERAL_break);
			{
			switch ( LA(1)) {
			case IDENT:
			{
				AST tmp21_AST = null;
				tmp21_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp21_AST);
				match(IDENT);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_continue:
		{
			AST tmp23_AST = null;
			tmp23_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp23_AST);
			match(LITERAL_continue);
			{
			switch ( LA(1)) {
			case IDENT:
			{
				AST tmp24_AST = null;
				tmp24_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp24_AST);
				match(IDENT);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_return:
		{
			AST tmp26_AST = null;
			tmp26_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp26_AST);
			match(LITERAL_return);
			{
			switch ( LA(1)) {
			case LPAREN:
			case IDENT:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case LBRACK:
			case DOUBLE:
			case STRING:
			{
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case SEMI:
		{
			s = LT(1);
			s_AST = (AST)astFactory.create(s);
			astFactory.addASTChild(currentAST, s_AST);
			match(SEMI);
			s_AST.setType(EMPTY_STAT);
			statement_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = statement_AST;
	}
	
	public final void compoundStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST compoundStatement_AST = null;
		Token  lc = null;
		AST lc_AST = null;
		
		lc = LT(1);
		lc_AST = (AST)astFactory.create(lc);
		astFactory.makeASTRoot(currentAST, lc_AST);
		match(LCURLY);
		lc_AST.setType(SLIST);
		{
		_loop6:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				statement();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop6;
			}
			
		} while (true);
		}
		match(RCURLY);
		compoundStatement_AST = (AST)currentAST.root;
		returnAST = compoundStatement_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		assignmentExpression();
		astFactory.addASTChild(currentAST, returnAST);
		expression_AST = (AST)currentAST.root;
		expression_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(EXPR,"EXPR")).add(expression_AST));
		currentAST.root = expression_AST;
		currentAST.child = expression_AST!=null &&expression_AST.getFirstChild()!=null ?
			expression_AST.getFirstChild() : expression_AST;
		currentAST.advanceChildToEnd();
		expression_AST = (AST)currentAST.root;
		returnAST = expression_AST;
	}
	
	public final void forInit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST forInit_AST = null;
		
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		forInit_AST = (AST)currentAST.root;
		forInit_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(FOR_INIT,"FOR_INIT")).add(forInit_AST));
		currentAST.root = forInit_AST;
		currentAST.child = forInit_AST!=null &&forInit_AST.getFirstChild()!=null ?
			forInit_AST.getFirstChild() : forInit_AST;
		currentAST.advanceChildToEnd();
		forInit_AST = (AST)currentAST.root;
		returnAST = forInit_AST;
	}
	
	public final void forCond() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST forCond_AST = null;
		
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		forCond_AST = (AST)currentAST.root;
		forCond_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(FOR_CONDITION,"FOR_CONDITION")).add(forCond_AST));
		currentAST.root = forCond_AST;
		currentAST.child = forCond_AST!=null &&forCond_AST.getFirstChild()!=null ?
			forCond_AST.getFirstChild() : forCond_AST;
		currentAST.advanceChildToEnd();
		forCond_AST = (AST)currentAST.root;
		returnAST = forCond_AST;
	}
	
	public final void forIter() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST forIter_AST = null;
		
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		forIter_AST = (AST)currentAST.root;
		forIter_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(FOR_ITERATOR,"FOR_ITERATOR")).add(forIter_AST));
		currentAST.root = forIter_AST;
		currentAST.child = forIter_AST!=null &&forIter_AST.getFirstChild()!=null ?
			forIter_AST.getFirstChild() : forIter_AST;
		currentAST.advanceChildToEnd();
		forIter_AST = (AST)currentAST.root;
		returnAST = forIter_AST;
	}
	
	public final void assignmentExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignmentExpression_AST = null;
		
		logicalOrExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case ASSIGN:
		case PLUS_ASSIGN:
		case MINUS_ASSIGN:
		case STAR_ASSIGN:
		case DIV_ASSIGN:
		case MOD_ASSIGN:
		{
			{
			switch ( LA(1)) {
			case ASSIGN:
			{
				AST tmp29_AST = null;
				tmp29_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp29_AST);
				match(ASSIGN);
				break;
			}
			case PLUS_ASSIGN:
			{
				AST tmp30_AST = null;
				tmp30_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp30_AST);
				match(PLUS_ASSIGN);
				break;
			}
			case MINUS_ASSIGN:
			{
				AST tmp31_AST = null;
				tmp31_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp31_AST);
				match(MINUS_ASSIGN);
				break;
			}
			case STAR_ASSIGN:
			{
				AST tmp32_AST = null;
				tmp32_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp32_AST);
				match(STAR_ASSIGN);
				break;
			}
			case DIV_ASSIGN:
			{
				AST tmp33_AST = null;
				tmp33_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp33_AST);
				match(DIV_ASSIGN);
				break;
			}
			case MOD_ASSIGN:
			{
				AST tmp34_AST = null;
				tmp34_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp34_AST);
				match(MOD_ASSIGN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			assignmentExpression();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case SEMI:
		case RPAREN:
		case COMMA:
		case RBRACK:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		assignmentExpression_AST = (AST)currentAST.root;
		returnAST = assignmentExpression_AST;
	}
	
	public final void expressionList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expressionList_AST = null;
		
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop18:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop18;
			}
			
		} while (true);
		}
		expressionList_AST = (AST)currentAST.root;
		expressionList_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ELIST,"ELIST")).add(expressionList_AST));
		currentAST.root = expressionList_AST;
		currentAST.child = expressionList_AST!=null &&expressionList_AST.getFirstChild()!=null ?
			expressionList_AST.getFirstChild() : expressionList_AST;
		currentAST.advanceChildToEnd();
		expressionList_AST = (AST)currentAST.root;
		returnAST = expressionList_AST;
	}
	
	public final void logicalOrExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST logicalOrExpression_AST = null;
		
		logicalAndExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop24:
		do {
			if ((LA(1)==LOR)) {
				AST tmp36_AST = null;
				tmp36_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp36_AST);
				match(LOR);
				logicalAndExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop24;
			}
			
		} while (true);
		}
		logicalOrExpression_AST = (AST)currentAST.root;
		returnAST = logicalOrExpression_AST;
	}
	
	public final void logicalAndExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST logicalAndExpression_AST = null;
		
		equalityExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop27:
		do {
			if ((LA(1)==LAND)) {
				AST tmp37_AST = null;
				tmp37_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp37_AST);
				match(LAND);
				equalityExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop27;
			}
			
		} while (true);
		}
		logicalAndExpression_AST = (AST)currentAST.root;
		returnAST = logicalAndExpression_AST;
	}
	
	public final void equalityExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST equalityExpression_AST = null;
		
		relationalExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop31:
		do {
			if ((LA(1)==NOT_EQUAL||LA(1)==EQUAL)) {
				{
				switch ( LA(1)) {
				case NOT_EQUAL:
				{
					AST tmp38_AST = null;
					tmp38_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp38_AST);
					match(NOT_EQUAL);
					break;
				}
				case EQUAL:
				{
					AST tmp39_AST = null;
					tmp39_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp39_AST);
					match(EQUAL);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				relationalExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop31;
			}
			
		} while (true);
		}
		equalityExpression_AST = (AST)currentAST.root;
		returnAST = equalityExpression_AST;
	}
	
	public final void relationalExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST relationalExpression_AST = null;
		
		additiveExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		{
		_loop36:
		do {
			if (((LA(1) >= LT && LA(1) <= GE))) {
				{
				switch ( LA(1)) {
				case LT:
				{
					AST tmp40_AST = null;
					tmp40_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp40_AST);
					match(LT);
					break;
				}
				case GT:
				{
					AST tmp41_AST = null;
					tmp41_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp41_AST);
					match(GT);
					break;
				}
				case LE:
				{
					AST tmp42_AST = null;
					tmp42_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp42_AST);
					match(LE);
					break;
				}
				case GE:
				{
					AST tmp43_AST = null;
					tmp43_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp43_AST);
					match(GE);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				additiveExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop36;
			}
			
		} while (true);
		}
		}
		relationalExpression_AST = (AST)currentAST.root;
		returnAST = relationalExpression_AST;
	}
	
	public final void additiveExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST additiveExpression_AST = null;
		
		multiplicativeExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop40:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AST tmp44_AST = null;
					tmp44_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp44_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					AST tmp45_AST = null;
					tmp45_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp45_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				multiplicativeExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop40;
			}
			
		} while (true);
		}
		additiveExpression_AST = (AST)currentAST.root;
		returnAST = additiveExpression_AST;
	}
	
	public final void multiplicativeExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST multiplicativeExpression_AST = null;
		
		powerExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop44:
		do {
			if (((LA(1) >= STAR && LA(1) <= MOD))) {
				{
				switch ( LA(1)) {
				case STAR:
				{
					AST tmp46_AST = null;
					tmp46_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp46_AST);
					match(STAR);
					break;
				}
				case DIV:
				{
					AST tmp47_AST = null;
					tmp47_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp47_AST);
					match(DIV);
					break;
				}
				case MOD:
				{
					AST tmp48_AST = null;
					tmp48_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp48_AST);
					match(MOD);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				powerExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop44;
			}
			
		} while (true);
		}
		multiplicativeExpression_AST = (AST)currentAST.root;
		returnAST = multiplicativeExpression_AST;
	}
	
	public final void powerExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST powerExpression_AST = null;
		
		unaryExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop47:
		do {
			if ((LA(1)==POWER)) {
				AST tmp49_AST = null;
				tmp49_AST = (AST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp49_AST);
				match(POWER);
				unaryExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop47;
			}
			
		} while (true);
		}
		powerExpression_AST = (AST)currentAST.root;
		returnAST = powerExpression_AST;
	}
	
	public final void unaryExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unaryExpression_AST = null;
		
		switch ( LA(1)) {
		case INC:
		{
			AST tmp50_AST = null;
			tmp50_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp50_AST);
			match(INC);
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case DEC:
		{
			AST tmp51_AST = null;
			tmp51_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp51_AST);
			match(DEC);
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp52_AST = null;
			tmp52_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp52_AST);
			match(MINUS);
			tmp52_AST.setType(UNARY_MINUS);
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case PLUS:
		{
			AST tmp53_AST = null;
			tmp53_AST = (AST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp53_AST);
			match(PLUS);
			tmp53_AST.setType(UNARY_PLUS);
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LPAREN:
		case IDENT:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		case LBRACK:
		case DOUBLE:
		case STRING:
		{
			postfixExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = unaryExpression_AST;
	}
	
	public final void postfixExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST postfixExpression_AST = null;
		Token  lp = null;
		AST lp_AST = null;
		Token  in = null;
		AST in_AST = null;
		Token  de = null;
		AST de_AST = null;
		
		primaryExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop51:
		do {
			if ((LA(1)==LPAREN)) {
				lp = LT(1);
				lp_AST = (AST)astFactory.create(lp);
				match(LPAREN);
				lp_AST.setType(FUNCTION_CALL);
				argList();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				postfixExpression_AST = (AST)currentAST.root;
				postfixExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(FUNCTION_CALL,"FUNCTION_CALL")).add(postfixExpression_AST));
				currentAST.root = postfixExpression_AST;
				currentAST.child = postfixExpression_AST!=null &&postfixExpression_AST.getFirstChild()!=null ?
					postfixExpression_AST.getFirstChild() : postfixExpression_AST;
				currentAST.advanceChildToEnd();
			}
			else {
				break _loop51;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case INC:
		{
			in = LT(1);
			in_AST = (AST)astFactory.create(in);
			astFactory.makeASTRoot(currentAST, in_AST);
			match(INC);
			in_AST.setType(POST_INC);
			break;
		}
		case DEC:
		{
			de = LT(1);
			de_AST = (AST)astFactory.create(de);
			astFactory.makeASTRoot(currentAST, de_AST);
			match(DEC);
			de_AST.setType(POST_DEC);
			break;
		}
		case SEMI:
		case RPAREN:
		case COMMA:
		case ASSIGN:
		case PLUS_ASSIGN:
		case MINUS_ASSIGN:
		case STAR_ASSIGN:
		case DIV_ASSIGN:
		case MOD_ASSIGN:
		case LOR:
		case LAND:
		case NOT_EQUAL:
		case EQUAL:
		case LT:
		case GT:
		case LE:
		case GE:
		case PLUS:
		case MINUS:
		case STAR:
		case DIV:
		case MOD:
		case POWER:
		case RBRACK:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		postfixExpression_AST = (AST)currentAST.root;
		returnAST = postfixExpression_AST;
	}
	
	public final void primaryExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primaryExpression_AST = null;
		
		switch ( LA(1)) {
		case IDENT:
		{
			AST tmp55_AST = null;
			tmp55_AST = (AST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp55_AST);
			match(IDENT);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case DOUBLE:
		case STRING:
		{
			constant();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_true:
		{
			AST tmp56_AST = null;
			tmp56_AST = (AST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp56_AST);
			match(LITERAL_true);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_false:
		{
			AST tmp57_AST = null;
			tmp57_AST = (AST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp57_AST);
			match(LITERAL_false);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_null:
		{
			AST tmp58_AST = null;
			tmp58_AST = (AST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp58_AST);
			match(LITERAL_null);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LPAREN:
		{
			match(LPAREN);
			assignmentExpression();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LBRACK:
		{
			array();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = primaryExpression_AST;
	}
	
	public final void argList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST argList_AST = null;
		
		{
		switch ( LA(1)) {
		case LPAREN:
		case IDENT:
		case PLUS:
		case MINUS:
		case INC:
		case DEC:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		case LBRACK:
		case DOUBLE:
		case STRING:
		{
			expressionList();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case SEMI:
		case RPAREN:
		case RBRACK:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		argList_AST = (AST)currentAST.root;
		returnAST = argList_AST;
	}
	
	public final void constant() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_AST = null;
		
		switch ( LA(1)) {
		case DOUBLE:
		{
			AST tmp61_AST = null;
			tmp61_AST = (AST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp61_AST);
			match(DOUBLE);
			constant_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			AST tmp62_AST = null;
			tmp62_AST = (AST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp62_AST);
			match(STRING);
			constant_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = constant_AST;
	}
	
	public final void array() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST array_AST = null;
		Token  lp = null;
		AST lp_AST = null;
		
		lp = LT(1);
		lp_AST = (AST)astFactory.create(lp);
		astFactory.makeASTRoot(currentAST, lp_AST);
		match(LBRACK);
		arrayList();
		astFactory.addASTChild(currentAST, returnAST);
		match(RBRACK);
		lp_AST.setType(ARRAY);
		array_AST = (AST)currentAST.root;
		returnAST = array_AST;
	}
	
	public final void arrayList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arrayList_AST = null;
		
		argList();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop58:
		do {
			if ((LA(1)==SEMI)) {
				match(SEMI);
				argList();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop58;
			}
			
		} while (true);
		}
		arrayList_AST = (AST)currentAST.root;
		arrayList_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ALIST,"ALIST")).add(arrayList_AST));
		currentAST.root = arrayList_AST;
		currentAST.child = arrayList_AST!=null &&arrayList_AST.getFirstChild()!=null ?
			arrayList_AST.getFirstChild() : arrayList_AST;
		currentAST.advanceChildToEnd();
		arrayList_AST = (AST)currentAST.root;
		returnAST = arrayList_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"MODIFIERS",
		"SLIST",
		"ALIST",
		"PROGRAM",
		"ARRAY",
		"PARAMETERS",
		"PARAMETER_DEF",
		"FUNCTION_CALL",
		"POST_INC",
		"POST_DEC",
		"METHOD_CALL",
		"EXPR",
		"UNARY_MINUS",
		"UNARY_PLUS",
		"ELIST",
		"FOR_INIT",
		"FOR_CONDITION",
		"FOR_ITERATOR",
		"EMPTY_STAT",
		"CTOR_CALL",
		"COMPLEX",
		"REAL_PART",
		"IMAG_PART",
		"LCURLY",
		"RCURLY",
		"SEMI",
		"\"if\"",
		"LPAREN",
		"RPAREN",
		"\"else\"",
		"\"for\"",
		"\"while\"",
		"\"do\"",
		"\"break\"",
		"IDENT",
		"\"continue\"",
		"\"return\"",
		"COMMA",
		"ASSIGN",
		"PLUS_ASSIGN",
		"MINUS_ASSIGN",
		"STAR_ASSIGN",
		"DIV_ASSIGN",
		"MOD_ASSIGN",
		"LOR",
		"LAND",
		"NOT_EQUAL",
		"EQUAL",
		"LT",
		"GT",
		"LE",
		"GE",
		"PLUS",
		"MINUS",
		"STAR",
		"DIV",
		"MOD",
		"POWER",
		"INC",
		"DEC",
		"\"true\"",
		"\"false\"",
		"\"null\"",
		"LBRACK",
		"RBRACK",
		"DOUBLE",
		"STRING",
		"WS",
		"COLON",
		"DOT",
		"POWER_ASSIGN",
		"NOT",
		"ESC",
		"SL_COMMENT",
		"ML_COMMENT",
		"VOCAB",
		"DIGIT",
		"EXPONENT"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { -4395511050577903616L, 111L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { -4395511041719533566L, 111L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { -2203452440574L, 127L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
