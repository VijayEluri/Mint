package org.mint.util;

import java.math.BigInteger;

import jmat.data.Matrix;

import org.mint.complex.Complex;
import org.mint.src.MintTreeTokenTypes;

public class Arith implements MintTreeTokenTypes {
	private BigInteger a, b;

	public Arith() {
		a = BigInteger.ZERO;
		b = BigInteger.ZERO;

	}

	public static Boolean evalBoolean(Object a, Object b, int op) {
		Boolean res = null;

		try {
			if (a instanceof BigInteger && b instanceof BigInteger)
				res = evalBoolean((BigInteger) a, (BigInteger) b, op);
			if (a instanceof BigInteger && b instanceof Double) {
				Double x = (Double) b;
				String val = String.valueOf(x.longValue());
				res = evalBoolean((BigInteger) a, new BigInteger(val), op);
			}
			if (a instanceof Double && b instanceof BigInteger) {
				Double x = (Double) a;
				String val = String.valueOf(x.longValue());
				res = evalBoolean(new BigInteger(val), (BigInteger) a, op);
			}

			/**
			 * String and String String and Double operations
			 */

			if (a instanceof String && b instanceof String)
				res = evalBoolean((String) a, (String) b, op);
			if (a instanceof String && b instanceof Double)
				res = evalBoolean((String) a, (Double) b, op);

			/**
			 * Double and Double
			 */
			if (a instanceof Double && b instanceof Double)
				res = evalBoolean((Double) a, (Double) b, op);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public static Boolean evalBoolean(Double a, Double b, int op) {
		boolean value = false;
		int check = a.compareTo(b);
		return getEvalBoolValue(op, check);
	}

	private static Boolean getEvalBoolValue(int op, int check) {
		boolean value = false;
		switch (op) {
			case GT :
				value = check > 0;
				break;
			case LT :
				value = check < 0;
				break;
			case GE :
				value = check >= 0;
				break;
			case LE :
				value = check <= 0;
				break;
			case EQUAL :
				value = check == 0;
				break;
			case NOT_EQUAL :
				value = check != 0;
				break;
		}
		return new Boolean(value);
	}

	public static Boolean evalBoolean(BigInteger a, BigInteger b, int op) {
		boolean value = false;
		int check = a.compareTo(b);
		return getEvalBoolValue(op, check);
	}

	public static Boolean evalBoolean(String a, String b, int op) {
		boolean value = false;
		int check = a.compareTo(b);
		return getEvalBoolValue(op, check);
	}

	public static Object evalNum(Object a, Object b, int op) {
		Object res = null;

		try {
			/**
			 * Matrix and Matrix Matrix and Double
			 */

			if (a instanceof Matrix && b instanceof Matrix)
				res = evalNum((Matrix) a, (Matrix) b, op);
			else if (a instanceof Matrix && b instanceof Double)
				res = evalNum((Matrix) a, (Double) b, op);

			/**
			 * BigInteger and BigInteger BigInteger and Double Double and
			 * BigInteger
			 */

			else if (a instanceof BigInteger && b instanceof BigInteger)
				res = evalNum((BigInteger) a, (BigInteger) b, op);
			else if (a instanceof BigInteger && b instanceof Double) {
				Double x = (Double) b;
				String val = String.valueOf(x.longValue());
				res = evalNum((BigInteger) a, new BigInteger(val), op);
			} else if (a instanceof Double && b instanceof BigInteger) {
				Double x = (Double) a;
				String val = String.valueOf(x.longValue());
				res = evalNum(new BigInteger(val), (BigInteger) a, op);
			}

			/**
			 * Complex and Complex Complex and Double Double and Complex
			 */
			else if (a instanceof Complex && b instanceof Complex)
				res = evalNum((Complex) a, (Complex) b, op);
			else if (a instanceof Complex && b instanceof Double) {
				Double x = (Double) b;
				res = evalNum((Complex) a, new Complex(x.doubleValue()), op);
			} else if (a instanceof Double && b instanceof Complex) {
				Double x = (Double) a;
				res = evalNum(new Complex(x.doubleValue()), (Complex) b, op);
			}

			/**
			 * String and String String and Double operations
			 */

			else if (a instanceof String && b instanceof String)
				res = evalNum((String) a, (String) b, op);
			else if (a instanceof String && b instanceof Double)
				res = evalNum((String) a, (Double) b, op);

			/**
			 * Double and Double
			 */
			else if (a instanceof Double && (b instanceof Double || b == null))
				res = evalNum((Double) a, (Double) b, op);
			else
				res = evalNum(a.toString(), b.toString(), op);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public static Matrix evalNum(Matrix v1, Double v2, int op) {
		Matrix c = null;
		double d = v2.doubleValue();

		switch (op) {
			case PLUS :
				c = v1.plus(d);
				break;
			case MINUS :
				c = v1.minus(d);
				break;
			case STAR :
				c = v1.times(d);
				break;
			case DIV :
				c = v1.divide(d);
				break;
			case POWER :
				c = v1.ebePow(d);
				break;
		}

		return c;
	}

	public static Matrix evalNum(Matrix v1, Matrix v2, int op) {
		Matrix c = new Matrix(1, 1);

		switch (op) {
			case PLUS :
				c = v1.plus(v2);
				break;
			case MINUS :
				c = v1.minus(v2);
				break;
			case STAR :
				c = v1.times(v2);
				break;
			case DIV :
				c = v1.divide(v2);
				break;
		}

		return c;
	}

	public static BigInteger evalNum(BigInteger x, BigInteger y, int op) {
		BigInteger c = null;

		switch (op) {
			case UNARY_MINUS :
				c = x.negate();
				break;
			case PLUS :
				c = x.add(y);
				break;
			case MINUS :
				c = x.subtract(y);
				break;
			case STAR :
				c = x.multiply(y);
				break;
			case DIV :
				c = x.divide(y);
				break;
			case POWER :
				c = x.pow(y.intValue());
				break;
			case MOD :
				c = x.mod(y);
				break;
		}
		return c;
	}

	public static Complex evalNum(Complex x, Complex y, int op) {
		Complex c = null;

		switch (op) {
			case UNARY_MINUS :
				c = x.neg();
				break;
			case PLUS :
				c = x.add(y);
				break;
			case MINUS :
				c = x.sub(y);
				break;
			case STAR :
				c = x.mul(y);
				break;
			case DIV :
				c = x.div(y);
				break;
			case POWER :
				c = x.pow(y);
				break;
		}
		return c;
	}

	public static String evalNum(String a, Double b, int op) {
		String c = new String();
		a = removeQuotes(a);

		if (op == STAR) {
			int count = 0;
			count = b.intValue();

			for (int i = 1; i <= count; i++) {
				c += a;
			}
		} else if (op == PLUS) {
			c = a + b.toString();
		}

		return c;
	}

	public static String evalNum(String a, String b, int op) {
		String c = null;
		a = removeQuotes(a);
		b = removeQuotes(b);

		if (op == PLUS) {
			c = a + b;
		}
		return c;
	}

	public static Double evalNum(Double a, Double b, int op) {
		double x, y, z;
		x = y = z = Double.NaN;

		x = a.doubleValue();
		if (b != null) {
			y = b.doubleValue();
		}

		switch (op) {
			case INC :
				z = ++x;
				break;
			case DEC :
				z = --z;
				break;
			case POST_INC :
				z = x++;
				break;
			case POST_DEC :
				z = x--;
				break;
			case UNARY_MINUS :
				z = -x;
				break;
			case UNARY_PLUS :
				z = +x;
				break;
			case PLUS :
				z = x + y;
				break;
			case MINUS :
				z = x - y;
				break;
			case STAR :
				z = x * y;
				break;
			case DIV :
				z = x / y;
				break;
			case POWER :
				z = Math.pow(x, y);
				break;
			case MOD :
				long x1,
				y1;
				x1 = a.longValue();
				y1 = b.longValue();
				z = x1 % y1;
				break;
		}

		return new Double(z);
	}

	private static String removeQuotes(String x) {
		char c = '\'';
		int pos1;
		pos1 = x.indexOf(c);

		if (pos1 < 0)
			return x;
		else {
			int pos2 = x.lastIndexOf(c);
			return (x.substring(pos1 + 1, pos2));
		}
	}
}