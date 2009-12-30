// $ANTLR 2.7.2a2 (20020112-1): "MintTree.g" -> "MintTree.java"$

package org.mint.src;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.mint.complex.Complex;
import org.mint.gui.OutputConsole;
import org.mint.gui.ProgramEditor;
import org.mint.util.Arith;
import org.mint.util.Functions;
import org.mint.util.MintEngine;
import org.mint.util.MintException;
import org.mint.util.NumberList;

import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.collections.AST;
import antlr.collections.impl.BitSet;


public class MintTree extends antlr.TreeParser
       implements MintTreeTokenTypes
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
public MintTree() {
	tokenNames = _tokenNames;
}

	public final Object  program(AST _t) throws RecognitionException {
		Object obj;
		
		AST program_AST_in = (AST)_t;
		obj = null; initAll();
		
		try {      // for error handling
			{
			_loop3:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					obj=stat(_t);
					_t = _retTree;
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final Object  stat(AST _t) throws RecognitionException {
		Object obj;
		
		AST stat_AST_in = (AST)_t;
		obj = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SLIST:
			{
				compound_statement(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_if:
			{
				if_statement(_t);
				_t = _retTree;
				break;
			}
			case EXPR:
			{
				obj=expression(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_return:
			{
				obj=return_statement(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_for:
			{
				forLoop(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final void compound_statement(AST _t) throws RecognitionException {
		
		AST compound_statement_AST_in = (AST)_t;
		Object o = null;
		
		try {      // for error handling
			AST __t5 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,SLIST);
			_t = _t.getFirstChild();
			{
			_loop7:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					o=stat(_t);
					_t = _retTree;
				}
				else {
					break _loop7;
				}
				
			} while (true);
			}
			_t = __t5;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void if_statement(AST _t) throws RecognitionException {
		
		AST if_statement_AST_in = (AST)_t;
		Object e = null;
		
		try {      // for error handling
			AST __t15 = _t;
			AST tmp2_AST_in = (AST)_t;
			match(_t,LITERAL_if);
			_t = _t.getFirstChild();
			e=expression(_t);
			_t = _retTree;
			
						if( ((Boolean)e).booleanValue() ) 
							stat(_t);
					
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SLIST:
			case EXPR:
			case LITERAL_if:
			case LITERAL_for:
			case LITERAL_return:
			{
				stat(_t);
				_t = _retTree;
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t15;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final Object  expression(AST _t) throws RecognitionException {
		Object obj;
		
		AST expression_AST_in = (AST)_t;
		obj = null;
		
		try {      // for error handling
			AST __t20 = _t;
			AST tmp3_AST_in = (AST)_t;
			match(_t,EXPR);
			_t = _t.getFirstChild();
			obj=expr(_t);
			_t = _retTree;
			_t = __t20;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final Object  return_statement(AST _t) throws RecognitionException {
		Object o=null;
		
		AST return_statement_AST_in = (AST)_t;
			Object e;
		
		try {      // for error handling
			AST __t18 = _t;
			AST tmp4_AST_in = (AST)_t;
			match(_t,LITERAL_return);
			_t = _t.getFirstChild();
			e=expression(_t);
			_t = _retTree;
			_t = __t18;
			_t = _t.getNextSibling();
			
						o = getValue(e);
					
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return o;
	}
	
	public final void forLoop(AST _t) throws RecognitionException {
		
		AST forLoop_AST_in = (AST)_t;
		Object var = null, init = null, cond = null, iter = null;
		
		try {      // for error handling
			AST __t10 = _t;
			AST tmp5_AST_in = (AST)_t;
			match(_t,LITERAL_for);
			_t = _t.getFirstChild();
			{
			init=expression(_t);
			_t = _retTree;
			}
			System.out.println(init);
			{
			cond=expression(_t);
			_t = _retTree;
			}
			System.out.println(cond);
			{
			iter=expression(_t);
			_t = _retTree;
			}
			System.out.println(iter);
			_t = __t10;
			_t = _t.getNextSibling();
			stat(_t);
			_t = _retTree;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final Object  expr(AST _t) throws RecognitionException {
		Object obj = null;;
		
		AST expr_AST_in = (AST)_t;
		Object a,b,temp;
		a = null;
		b = null;
		temp = null;
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ASSIGN:
			{
				AST __t22 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t22;
				_t = _t.getNextSibling();
				setIdValue( a, b );	
				break;
			}
			case PLUS_ASSIGN:
			{
				AST __t23 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,PLUS_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t23;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, PLUS); 
						  setIdValue( a, temp);
						
				break;
			}
			case MINUS_ASSIGN:
			{
				AST __t24 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,MINUS_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t24;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, MINUS); 
						  setIdValue( a, temp);
						
				break;
			}
			case STAR_ASSIGN:
			{
				AST __t25 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,STAR_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t25;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, STAR); 
						  setIdValue( a, temp);
						
				break;
			}
			case DIV_ASSIGN:
			{
				AST __t26 = _t;
				AST tmp10_AST_in = (AST)_t;
				match(_t,DIV_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t26;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, DIV); 
						  setIdValue( a, temp);
						
				break;
			}
			case MOD_ASSIGN:
			{
				AST __t27 = _t;
				AST tmp11_AST_in = (AST)_t;
				match(_t,MOD_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t27;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, MOD); 
						  setIdValue( a, temp);
						
				break;
			}
			case POWER_ASSIGN:
			{
				AST __t28 = _t;
				AST tmp12_AST_in = (AST)_t;
				match(_t,POWER_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t28;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); temp = Arith.evalNum( a , b, POWER); 
						  setIdValue( a, temp);
						
				break;
			}
			case PLUS:
			{
				AST __t29 = _t;
				AST tmp13_AST_in = (AST)_t;
				match(_t,PLUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t29;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, PLUS);
				break;
			}
			case MINUS:
			{
				AST __t30 = _t;
				AST tmp14_AST_in = (AST)_t;
				match(_t,MINUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t30;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, MINUS);
				break;
			}
			case POWER:
			{
				AST __t31 = _t;
				AST tmp15_AST_in = (AST)_t;
				match(_t,POWER);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t31;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, POWER);
				break;
			}
			case DIV:
			{
				AST __t32 = _t;
				AST tmp16_AST_in = (AST)_t;
				match(_t,DIV);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t32;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, DIV);
				break;
			}
			case MOD:
			{
				AST __t33 = _t;
				AST tmp17_AST_in = (AST)_t;
				match(_t,MOD);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t33;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, MOD);
				break;
			}
			case STAR:
			{
				AST __t34 = _t;
				AST tmp18_AST_in = (AST)_t;
				match(_t,STAR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t34;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalNum( a , b, STAR);
				break;
			}
			case INC:
			{
				AST __t35 = _t;
				AST tmp19_AST_in = (AST)_t;
				match(_t,INC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t35;
				_t = _t.getNextSibling();
				a = getValue(a); obj = Arith.evalNum( a , null, INC);
				break;
			}
			case DEC:
			{
				AST __t36 = _t;
				AST tmp20_AST_in = (AST)_t;
				match(_t,DEC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t36;
				_t = _t.getNextSibling();
				a = getValue(a); obj = Arith.evalNum( a , null, DEC);
				break;
			}
			case POST_INC:
			{
				AST __t37 = _t;
				AST tmp21_AST_in = (AST)_t;
				match(_t,POST_INC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t37;
				_t = _t.getNextSibling();
				a = getValue(a); obj = Arith.evalNum( a , null, POST_INC);
				break;
			}
			case POST_DEC:
			{
				AST __t38 = _t;
				AST tmp22_AST_in = (AST)_t;
				match(_t,POST_DEC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t38;
				_t = _t.getNextSibling();
				a = getValue(a); obj = Arith.evalNum( a , null, POST_DEC);
				break;
			}
			case UNARY_MINUS:
			{
				AST __t39 = _t;
				AST tmp23_AST_in = (AST)_t;
				match(_t,UNARY_MINUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t39;
				_t = _t.getNextSibling();
				a = getValue(a); obj = Arith.evalNum( a , null, UNARY_MINUS);
				break;
			}
			case UNARY_PLUS:
			{
				AST __t40 = _t;
				AST tmp24_AST_in = (AST)_t;
				match(_t,UNARY_PLUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t40;
				_t = _t.getNextSibling();
				a = getValue(a); obj = Arith.evalNum( a , null, UNARY_PLUS);
				break;
			}
			case GT:
			{
				AST __t41 = _t;
				AST tmp25_AST_in = (AST)_t;
				match(_t,GT);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t41;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, GT);
				break;
			}
			case LT:
			{
				AST __t42 = _t;
				AST tmp26_AST_in = (AST)_t;
				match(_t,LT);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t42;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, LT);
				break;
			}
			case GE:
			{
				AST __t43 = _t;
				AST tmp27_AST_in = (AST)_t;
				match(_t,GE);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t43;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, GE);
				break;
			}
			case LE:
			{
				AST __t44 = _t;
				AST tmp28_AST_in = (AST)_t;
				match(_t,LE);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t44;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, LE);
				break;
			}
			case EQUAL:
			{
				AST __t45 = _t;
				AST tmp29_AST_in = (AST)_t;
				match(_t,EQUAL);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t45;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, EQUAL);
				break;
			}
			case NOT_EQUAL:
			{
				AST __t46 = _t;
				AST tmp30_AST_in = (AST)_t;
				match(_t,NOT_EQUAL);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t46;
				_t = _t.getNextSibling();
				a = getValue(a); b = getValue(b); obj = Arith.evalBoolean( a , b, NOT_EQUAL);
				break;
			}
			case ARRAY:
			case FUNCTION_CALL:
			case IDENT:
			case DOUBLE:
			case STRING:
			{
				obj=primaryExpression(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final Object  primaryExpression(AST _t) throws RecognitionException {
		Object obj = null;;
		
		AST primaryExpression_AST_in = (AST)_t;
		Object a = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				obj=id(_t);
				_t = _retTree;
				break;
			}
			case DOUBLE:
			case STRING:
			{
				obj=constant(_t);
				_t = _retTree;
				break;
			}
			case FUNCTION_CALL:
			{
				AST __t59 = _t;
				AST tmp31_AST_in = (AST)_t;
				match(_t,FUNCTION_CALL);
				_t = _t.getFirstChild();
				obj=function(_t);
				_t = _retTree;
				_t = __t59;
				_t = _t.getNextSibling();
				break;
			}
			case ARRAY:
			{
				AST __t60 = _t;
				AST tmp32_AST_in = (AST)_t;
				match(_t,ARRAY);
				_t = _t.getFirstChild();
				obj=array(_t);
				_t = _retTree;
				_t = __t60;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final Object  function(AST _t) throws RecognitionException {
		Object obj;
		
		AST function_AST_in = (AST)_t;
		obj = null;
		Object list = null;
		Object name = new Object();
		Vector val = new Vector();
		Vector v = new Vector();
		
		
		try {      // for error handling
			name=primaryExpression(_t);
			_t = _retTree;
			v=argList(_t);
			_t = _retTree;
				String funcName = name.toString();
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
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final Vector  argList(AST _t) throws RecognitionException {
		Vector v = new Vector();
		
		AST argList_AST_in = (AST)_t;
		Object temp = null;
		
		try {      // for error handling
			AST __t54 = _t;
			AST tmp33_AST_in = (AST)_t;
			match(_t,ELIST);
			_t = _t.getFirstChild();
			temp=argList1(_t,v);
			_t = _retTree;
			_t = __t54;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return v;
	}
	
	public final Object  array(AST _t) throws RecognitionException {
		Object o = null;
		
		AST array_AST_in = (AST)_t;
		Vector v = new Vector(); 
		Object  temp = null;
		NumberList list = new NumberList();
		
		
		try {      // for error handling
			AST __t49 = _t;
			AST tmp34_AST_in = (AST)_t;
			match(_t,ALIST);
			_t = _t.getFirstChild();
			arrayList(_t,list);
			_t = _retTree;
			_t = __t49;
			_t = _t.getNextSibling();
			
					o = list;
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return o;
	}
	
	public final void arrayList(AST _t,
		NumberList list
	) throws RecognitionException {
		
		AST arrayList_AST_in = (AST)_t;
		Object o = null;
		
		try {      // for error handling
			{
			_loop52:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ELIST)) {
					o=argList(_t);
					_t = _retTree;
					list.add(o);
				}
				else {
					break _loop52;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final Object  argList1(AST _t,
		Vector v
	) throws RecognitionException {
		Object obj = null;
		
		AST argList1_AST_in = (AST)_t;
		
		try {      // for error handling
			obj=expression(_t);
			_t = _retTree;
			v.addElement(obj);
			{
			_loop57:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==EXPR)) {
					obj=expression(_t);
					_t = _retTree;
					v.addElement(obj);
				}
				else {
					break _loop57;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final Object  id(AST _t) throws RecognitionException {
		Object obj;
		
		AST id_AST_in = (AST)_t;
		AST i = null;
		obj = null; 
		Object a = null, b = null;
		
		
		try {      // for error handling
			i = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
				obj = i.getText();
					if( ! isId(obj) ){
						setIdValue(i.getText(),new Object());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
	}
	
	public final Object  constant(AST _t) throws RecognitionException {
		Object obj;
		
		AST constant_AST_in = (AST)_t;
		AST d = null;
		AST s = null;
		obj = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case DOUBLE:
			{
				d = (AST)_t;
				match(_t,DOUBLE);
				_t = _t.getNextSibling();
				String val = d.getText(); obj = Double.valueOf(val);
				break;
			}
			case STRING:
			{
				s = (AST)_t;
				match(_t,STRING);
				_t = _t.getNextSibling();
				obj = removeQuotes(s.getText());
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return obj;
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
		long[] data = { 1117765271584L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
