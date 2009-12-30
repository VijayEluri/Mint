/**
 * <p>
 * Title: jCalculus
 * </p>
 * <p>
 * Description: Java Calculus
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003 Maheshwaran.S
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Maheshwaran.S
 * @version 1.0
 */

package org.mint.calculus;

public abstract class Calculus {
	static String path;
	static PrologEngine e;

	abstract public String eval(String a, String b);
	abstract public String eval(String a);

	public static PrologEngine getEngine() throws Exception {
		e = new PrologEngine("libs/calculus.pl");
		return e;
	}
}