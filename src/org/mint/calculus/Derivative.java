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

public class Derivative extends Calculus {
	private PrologEngine m;

	public Derivative() throws Exception {
		m = Calculus.getEngine();
	}

	public String eval(String s) {
		return eval(s, "x");
	}

	public String eval(String s, String var) {
		try {
			String formula = "d(" + s + "," + var + ",R)";
			m.exec(formula);
			return m.getResult("R");
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}
}