header{
package org.mint.src;

import java.math.BigInteger;
import java.util.*;
import java.io.*;

import org.mint.complex.*;
import org.mint.util.*;
import org.mint.gui.*;

import jmat.data.*;
}

class MintTree extends TreeParser;
options {
	importVocab = MintParser;
	defaultErrorHandler = true;
}

{
	public void listEnvironment(){
		Enumeration e;
		StringBuffer print = new StringBuffer();
		
		print.append("-------Variables-------" + "\n");
		
		e = varList.keys();
		while(e.hasMoreElements())
			print.append(e.nextElement() + "\n");

		print.append("-------Constants-------" + "\n");
		e = constantList.keys();
		while(e.hasMoreElements())
			print.append(e.nextElement() + "\n");
			
		//output.println(print.toString());
		output.setMessage(print.toString());
	}

	public void listConstants(){
		Enumeration e;
		StringBuffer print = new StringBuffer();

		print.append("-------Constants-------" + "\n");
		e = constantList.keys();
		while(e.hasMoreElements())
			print.append(e.nextElement() + "\n");
			
		//output.println(print.toString());
		output.setMessage(print.toString());
	}

	public void listVariables(){
		Enumeration e;
		StringBuffer print = new StringBuffer();
		
		print.append("-------Variables-------" + "\n");
		
		e = varList.keys();
		while(e.hasMoreElements())
			print.append(e.nextElement() + "\n");

		//output.println(print.toString());
		output.setMessage(print.toString());
	}
	
	private String removeQuotes(String s){
		String s1 = new String();
		char c = '\'';
		int start = s.indexOf(c) + 1;
		int end = s.lastIndexOf(c);

		if( start != -1 && end != -1 )
			s1 = s.substring(start,end);
		else
			s1 = s;
			
		return new String(s1);
	}


	private Object getValue(Object x){
	  
	  if( x != null ){
		if( isConst(x) )
			return getConstValue(x);
		else if( isId(x) ){
			Object o = getIdValue(x);
			if( o instanceof String ){
				String s = o.toString();
				o = removeQuotes(s);
				//o = s;
			}
			return o;
		}
		else
			return x;
	 }
	 else
		return null;
	 
	}
	
	private Object getIdValue(Object idName){
		return varList.get(idName);
	}
	
	private Object getConstValue(Object constName){
			return constantList.get(constName);
	}
	
	private void setIdValue(Object idName, Object v){
		if( ! isConst(idName) )
			varList.put(idName,getValue(v));
	}
	
	private void setConstValue(String constName,Object o){
		constantList.put(constName,o);
	}
	
	private boolean isId(Object idName){
		return varList.containsKey(idName);
	}

	private boolean isConst(Object cName){
		return constantList.containsKey(cName);
	}

	private void loadConstants(){
		constantList = new Hashtable();
		
		setConstValue("PI", new Double(Math.PI));//mathematical value of pi
		setConstValue("E", new Double(Math.E));	//mathematical value of e
		setConstValue("i",new Complex(0,1));	//a constant to represent complex imaginary number
		setConstValue("Inf",new Double(Double.POSITIVE_INFINITY));
	}
	
	public Vector idToValues(Vector v){
		Vector temp = new Vector();
		Enumeration e = v.elements();
		
		while( e.hasMoreElements() ){
			temp.add( getValue( e.nextElement() ) );
		}
		
		return temp;
	}

	public void initAll(){
		intiatedAll = true;
		varList = new Hashtable();
		printItems = new Vector();
		loadConstants();
	}
	
	
	/* start all constants from 100*/
	private final int ROW  = 0x100;
	private final int COL  = 0x101;
	
	private final String RAND = "RAND";
	
	/*output stream*/
	private PrintStream out = new PrintStream(System.out);
	//private OutputConsole output = new OutputConsole();
	
	private OutputConsole output = ProgramEditor.getOutputConsole();
	
	private Hashtable varList;	/* a list of variables stored used in the program*/
	private Vector printItems;	/* a list of items to be printed in a std output*/
	private Hashtable constantList;	/* a list of constants stored used in the program*/
	private boolean intiatedAll = false;
}

program returns [Object obj]
{ obj = null; initAll(); }
:	( obj = stat )*
	;

compound_statement
{ Object o = null; }
	:	#(SLIST ( o = stat)* )
	;

stat returns [Object obj]
{ obj = null; }
	:	compound_statement
	|	if_statement
	|	obj = expression
	|	obj = return_statement
	|   forLoop
	;


forLoop!
{ Object var = null, init = null, cond = null, iter = null; }
	:
		#("for"

			(init = expression )
			{ System.out.println(init); }
			(cond = expression )
			{ System.out.println(cond); }
			(iter = expression )
			{ System.out.println(iter); }

/*
			(#FOR_INIT init = expression )
			(#FOR_CONDITION cond = expression )
			(#FOR_ITERATOR iter = expression )
*/			
		)
		
		stat
	;

if_statement!
{ Object e = null; }
	: #("if" e=expression 
		{ 
			if( ((Boolean)e).booleanValue() ) 
				stat(_t);
		}
		
		(stat)?
	   )
	;
return_statement returns [Object o=null]
{	Object e; }
	:	#( "return" e = expression ){
			o = getValue(e);
		}
	;

expression returns [Object obj]
{ obj = null; }
	:	#(EXPR obj = expr )
	;

expr returns [Object obj = null;]
{ Object a,b,temp;
a = null;
b = null;
temp = null;
}
	:	#(ASSIGN a=expr b=expr)
		{  setIdValue( a, b );	}
	|	#(PLUS_ASSIGN a=expr b=expr)
		{ a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, PLUS); 
		  setIdValue( a, temp);
		}
	|	#(MINUS_ASSIGN a=expr b=expr)
		{ a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, MINUS); 
		  setIdValue( a, temp);
		}
	|	#(STAR_ASSIGN a=expr b=expr)
		{ a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, STAR); 
		  setIdValue( a, temp);
		}
	|	#(DIV_ASSIGN a=expr b=expr)
		{ a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, DIV); 
		  setIdValue( a, temp);
		}
	|	#(MOD_ASSIGN a=expr b=expr)
		{ a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, MOD); 
		  setIdValue( a, temp);
		}
	|	#(POWER_ASSIGN a=expr b=expr)
		{ a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, POWER); 
		  setIdValue( a, temp);
		}
	|	#(PLUS a=expr b=expr) 
		{ a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, PLUS); }
	|	#(MINUS  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, MINUS); }
	|	#(POWER   a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, POWER); }
	|	#(DIV  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, DIV); }
	|	#(MOD  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, MOD); }
	|	#(STAR  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, STAR); }
	|	#(INC  a=expr)	
		{ a = getValue(a); obj = Arith.evalNum( a , null, INC); }
	|	#(DEC  a=expr)
		{ a = getValue(a); obj = Arith.evalNum( a , null, DEC); }
	|	#(POST_INC  a=expr)
		{ a = getValue(a); obj = Arith.evalNum( a , null, POST_INC); }
	|	#(POST_DEC  a=expr)
		{ a = getValue(a); obj = Arith.evalNum( a , null, POST_DEC); }
	|	#(UNARY_MINUS  a=expr)
		{ a = getValue(a); obj = Arith.evalNum( a , null, UNARY_MINUS); }
	|	#(UNARY_PLUS  a=expr)
		{ a = getValue(a); obj = Arith.evalNum( a , null, UNARY_PLUS); }
	|	#(GT  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, GT); }
	|	#(LT  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, LT); }
	|	#(GE  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, GE); }
	|	#(LE  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, LE); }
	|	#(EQUAL  a=expr b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, EQUAL); }
	|	#(NOT_EQUAL  a=expr  b=expr)
		{ a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, NOT_EQUAL); }
	|	obj = primaryExpression		
	;

function returns [Object obj]
{ obj = null;
 Object list = null;
 Object name = new Object();
 Vector val = new Vector();
 Vector v = new Vector();
}
	:  name = primaryExpression v = argList
	
	{	String funcName = name.toString();
		String out = null;
		
		val = idToValues(v); /* convert the id to values */
		Object[] objs = val.toArray();
		
		if( 	funcName.equalsIgnoreCase("print") && 
			objs != null && objs.length == 1
		  ){
		  	/* for print function */
		  	out = objs[0].toString();
		  	//output.setOutput( out );
		  	output.setMessage(out);
		  	obj = new Double(out.length());
			
		}
		else if	( 	funcName.equalsIgnoreCase("eval") && 
				objs != null && objs.length == 1
		  	){
		  	/* eval functions code */
		  	//String expr = removeQuotes(getValue(objs[0]).toString());
		  	String expr = getValue(objs[0]).toString();
		  	try {
				return MintEngine.compile(expr);
			} catch (MintException e) {
				ProgramEditor.getErrorConsole().setError(e.toString());
			}		  	
		}
		else{
			try{
				obj = Functions.evalFunction( funcName , objs );
			}
			catch( IllegalArgumentException ill){
				
				out = "Invalid Arguments for function : " + funcName;
				output.setError( out );
			}
			catch( NoSuchMethodException nsm){
				out = "Invalid Arguments for function : " + funcName;
				output.setError( out );
			}
			catch( Exception e ){
				out = e.getMessage();
				output.setError( out );
			}

		}
	}
	;

array returns [Object o = null]
{Vector v = new Vector(); 
Object  temp = null;
NumberList list = new NumberList();
}
	: #( ALIST arrayList[list] )
	{
		o = list;
	}
	;

arrayList [NumberList list]
{ Object o = null; }
	: ( o = argList { list.add(o);  } )*
	;

argList returns [Vector v = new Vector()]
{ Object temp = null; }
	: #(ELIST temp = argList1[v] )
	;

argList1 [Vector v] returns [Object obj = null]
	:	obj = expression { v.addElement(obj); }
		( obj = expression { v.addElement(obj); } )* 
	;
	
primaryExpression returns [Object obj = null;]
{ Object a = null; }
	:	obj = id
	|	obj = constant
	|	#(FUNCTION_CALL obj = function)
	|	#(ARRAY obj = array)
	;


	
id returns [Object obj]
{ obj = null; 
Object a = null, b = null;
}
	: i:IDENT
	{ 	obj = i.getText();
		if( ! isId(obj) ){
			setIdValue(i.getText(),new Object());
		}
	}
	;
	

constant returns [Object obj]
{ obj = null;}
	:
	d:DOUBLE { String val = d.getText(); obj = Double.valueOf(val); }
	|	s:STRING { obj = removeQuotes(s.getText()); }
	;
	