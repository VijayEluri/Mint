/*
 * Created on Feb 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.mint.util;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MintException extends Exception {

	int lineno, colno;

	public MintException(String error, int lineno, int colno) {
		super(error);
		this.lineno = lineno;
		this.colno = colno;
	}

	public String toString() {
		return "Error: <Line: " + lineno + ", Column:" + colno + ">: "
				+ this.getMessage();
	}

}