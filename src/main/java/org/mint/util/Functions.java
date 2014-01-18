package org.mint.util;

import java.io.FileWriter;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Vector;

import jmat.data.Matrix;
import jmat.data.matrixDecompositions.EigenvalueDecomposition;
import jmat.data.matrixDecompositions.LUDecomposition;

import org.mint.calculus.Derivative;
import org.mint.calculus.Integral;
import org.mint.combinatrics.Combination;
import org.mint.combinatrics.Permutation;
import org.mint.complex.Complex;
import org.mint.graph.AnimationGraph;
import org.mint.graph.Graph3D;
import org.mint.graph.ParametricGraph;
import org.mint.gui.OutputConsole;
import org.mint.number.Fibonacci;
import org.mint.number.Prime;
import org.mint.number.Series;

class WriteFile {
	Object obj;
	String fileName;
	public WriteFile(String fileName, Object o) {
		this.fileName = fileName;
		this.obj = o;
	}

	public void write() throws Exception {
		FileWriter w = new FileWriter(fileName);
		w.write(obj.toString());
	}
}

public class Functions {
	private static OutputConsole op;
	public static void setOutputConsole(OutputConsole op_) {
		op = op_;
	}

	public static OutputConsole getOutputConsole() {
		return op;
	}

	public static void evalFunc(String Name) throws Exception {
	}

	public static Object evalFunc(String Name, Complex x) throws Exception {
		Object v = null;

		if (Name.equalsIgnoreCase("abs")) {
			return Complex.real(x.abs());
		} else

		if (Name.equalsIgnoreCase("sin")) {
			v = x.sin();
		} else if (Name.equalsIgnoreCase("cos")) {
			v = x.cos();
		} else if (Name.equalsIgnoreCase("tan")) {
			v = x.tan();
		} else if (Name.equalsIgnoreCase("arctan")) {
			v = x.atan();
		} else if (Name.equalsIgnoreCase("arcsin")) {
			v = x.asin();
		} else if (Name.equalsIgnoreCase("arccos")) {
			v = x.acos();
		} else if (Name.equalsIgnoreCase("cot")) {
			v = x.cot();
		} else if (Name.equalsIgnoreCase("sec")) {
			v = x.sec();
		} else if (Name.equalsIgnoreCase("cosec")) {
			v = x.cosec();
		} else

		/** hyperbolic functions */
		if (Name.equalsIgnoreCase("sinh")) {
			v = x.sinh();
		} else if (Name.equalsIgnoreCase("cosh")) {
			v = x.cosh();
		} else if (Name.equalsIgnoreCase("tanh")) {
			v = x.tanh();
		} else if (Name.equalsIgnoreCase("arcsinh")) {
			v = x.asinh();
		} else if (Name.equalsIgnoreCase("arccosh")) {
			v = x.acosh();
		} else if (Name.equalsIgnoreCase("arctanh")) {
			v = x.atanh();
		} else if (Name.equalsIgnoreCase("ln")) {
			v = x.log();
		} else

		if (Name.equalsIgnoreCase("conju")) {
			v = x.conj();
		} else

		if (Name.equalsIgnoreCase("real")) {
			v = new Double(x.re());
		} else if (Name.equalsIgnoreCase("imag")) {
			v = new Double(x.im());
		} else if (Name.equalsIgnoreCase("norm")) {
			v = new Double(x.norm());
		} else if (Name.equalsIgnoreCase("mod")) {
			v = new Double(Math.pow(x.norm(), 1.0 / 2.0));
		} else if (Name.equalsIgnoreCase("arg")) {
			v = new Double(x.arg());
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;
	}

	public static Object evalFunc(String Name, Double a) throws Exception {
		Object v = null;
		double x = a.doubleValue();

		if (Name.equalsIgnoreCase("sin")) {
			v = new Double(Math.sin(x));
		} else if (Name.equalsIgnoreCase("cos")) {
			v = new Double(Math.cos(x));
		} else if (Name.equalsIgnoreCase("tan")) {
			v = new Double(Math.tan(x));
		} else if (Name.equalsIgnoreCase("arctan")) {
			v = new Double(Math.atan(x));
		} else if (Name.equalsIgnoreCase("arcsin")) {
			v = new Double(Math.asin(x));
		} else if (Name.equalsIgnoreCase("arccos")) {
			v = new Double(Math.acos(x));
		} else if (Name.equalsIgnoreCase("ceil")) {
			v = new Double(Math.ceil(x));
		} else if (Name.equalsIgnoreCase("floor")) {
			v = new Double(Math.floor(x));
		} else if (Name.equalsIgnoreCase("abs")) {
			v = new Double(Math.abs(x));
		} else if (Name.equalsIgnoreCase("cot")) {
			v = new Double(1 / Math.tan(x));
		} else if (Name.equalsIgnoreCase("sec")) {
			v = new Double(1 / Math.cos(x));
		} else if (Name.equalsIgnoreCase("cosec")) {
			v = new Double(1 / Math.sin(x));
		} else if (Name.equalsIgnoreCase("arccot")) {
			v = new Double(Math.atan(x) + 2 * Math.atan(1));
		} else if (Name.equalsIgnoreCase("arccosec")) {
			v = new Double(Math.atan(x / Math.pow(x * x - 1, 0.5))
					+ (sgn(x) - 1) * (2 * Math.atan(1)));
		} else if (Name.equalsIgnoreCase("arcsec")) {
			v = new Double(Math.atan(x / Math.pow(x * x - 1, 0.5)) + sgn(x - 1)
					* (2 * Math.atan(1)));
		} else

		/** hyperbolic functions */
		if (Name.equalsIgnoreCase("sinh")) {
			v = new Double((Math.exp(x) - Math.exp(-x)) / 2);
		} else if (Name.equalsIgnoreCase("cosh")) {
			v = new Double((Math.exp(x) + Math.exp(-x)) / 2);
		} else if (Name.equalsIgnoreCase("tanh")) {
			v = new Double((Math.exp(x) - Math.exp(-x))
					/ (Math.exp(x) + Math.exp(-x)));
		} else if (Name.equalsIgnoreCase("sech")) {
			v = new Double(2 / (Math.exp(x) + Math.exp(-x)));
		} else if (Name.equalsIgnoreCase("cosech")) {
			v = new Double(1 / (Math.exp(x) - Math.exp(-x)));
		} else if (Name.equalsIgnoreCase("coth")) {
			v = new Double((Math.exp(x) + Math.exp(-x))
					/ (Math.exp(x) - Math.exp(-x)));
		} else if (Name.equalsIgnoreCase("arcsinh")) {
			v = new Double(Math.log(x + Math.sqrt(x * x + 1)));
		} else if (Name.equalsIgnoreCase("arccosh")) {
			v = new Double(Math.log(x + Math.sqrt(x * x - 1)));
		} else if (Name.equalsIgnoreCase("arctanh")) {
			v = new Double(Math.log((1 + x) / (1 - x)) / 2);
		} else if (Name.equalsIgnoreCase("arccoth")) {
			v = new Double(Math.log((x + 1) / (x - 1)) / 2);
		} else if (Name.equalsIgnoreCase("arccosech")) {
			v = new Double(Math.log(((sgn(x) * Math.sqrt(x * x + 1) + 1) + 1)
					/ x));
		} else if (Name.equalsIgnoreCase("arcsech")) {
			v = new Double(Math.log((Math.sqrt(-x * x + 1) + 1) / x));
		} else if (Name.equalsIgnoreCase("log")) {
			v = new Double(Math.log(x) / Math.log(10));
		} else if (Name.equalsIgnoreCase("exp")) {
			v = new Double(Math.exp(x));
		} else if (Name.equalsIgnoreCase("ln")) {
			v = new Double(Math.log(x));
		} else if (Name.equalsIgnoreCase("sqrt")) {
			v = new Double(Math.sqrt(x));
		} else if (Name.equalsIgnoreCase("cubert")) {
			v = new Double(Math.pow(x, 1.0 / 3.0));
		} else

		/**
		 * Big Number Functions
		 */

		if (Name.equalsIgnoreCase("big")) {
			String s = a.longValue() + "";
			v = new BigInteger(s);
		} else

		/**
		 * Matrix Functions
		 */
		if (Name.equalsIgnoreCase("eye")) {
			int l = a.intValue();

			double[] d = new double[l];
			for (int i = 0; i < l; i++) {
				d[i] = 1.0;
			}
			v = Matrix.diagonal(d);
		} else if (Name.equalsIgnoreCase("fibo")) {
			Fibonacci f = new Fibonacci();
			v = f.fib(a.intValue());
		} else if (Name.equalsIgnoreCase("fibolist")) {
			Fibonacci f = new Fibonacci();
			f.setList(true);
			f.fib(a.intValue());
			v = f.getList();
		} else if (Name.equalsIgnoreCase("isprime")) {
			Prime p = new Prime();
			v = p.isPrime(a.longValue());
		} else if (Name.equalsIgnoreCase("primes")) {
			Prime p = new Prime();
			v = p.getPrimes(a.intValue());
		} else if (Name.equalsIgnoreCase("fact")) {
			long val = a.longValue();
			v = Series.fact(val);
		} else if (Name.equalsIgnoreCase("sumseries")) {
			v = Series.sumSeries(a.longValue());
		} 
		else {
			throw new Exception(Name + " : Function Not Found ");
		}
		return v;
	}

	public static Object evalFunc(String Name, BigInteger a, BigInteger b)
			throws Exception {
		BigInteger c = null;
		if (Name.equalsIgnoreCase("min")) {
			c = a.min(b);
		} else if (Name.equalsIgnoreCase("max")) {
			c = a.max(b);
		} else if (Name.equalsIgnoreCase("gcd")) {
			c = a.gcd(b);
		}
		else if (Name.equalsIgnoreCase("sumseriesn")) {
			c = Series.sumNSeries(a,b);
		}
		else {
			throw new Exception(Name + " : Function Not Found ");
		}
		return c;
	}

	public static Object evalFunc(String Name, EigenvalueDecomposition a)
			throws Exception {
		Object v = null;
		if (Name.equalsIgnoreCase("getD")) {
			v = a.getD();
		} else if (Name.equalsIgnoreCase("getRealD")) {
			v = a.getRealD();
		} else if (Name.equalsIgnoreCase("getImagD")) {
			v = a.getImagD();
		} else if (Name.equalsIgnoreCase("getV")) {
			v = a.getV();
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;
	}

	public static Object evalFunc(String Name, LUDecomposition a)
			throws Exception {
		Object v = null;

		if (Name.equalsIgnoreCase("getL")) {
			v = a.getL();
		} else if (Name.equalsIgnoreCase("getU")) {
			v = a.getU();
		} else if (Name.equalsIgnoreCase("getP")) {
			v = a.getP();
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;
	}

	public static Object evalFunc(String Name, Matrix a) throws Exception {
		Object c = null;
		if (Name.equalsIgnoreCase("det")) {
			c = new Double(a.determinant());
		} else if (Name.equalsIgnoreCase("inv")) {
			c = a.inverse();
		} else if (Name.equalsIgnoreCase("size")) {
			int n = a.getRowDimension();
			int m = a.getColumnDimension();

			double[][] size = {{n, m}};

			c = new Matrix(size);
		} else if (Name.equalsIgnoreCase("trace")) {
			c = new Double(a.trace());
		} else

		/* checks if is symmetric */
		if (Name.equalsIgnoreCase("issymmetric")) {
			c = new Boolean(a.isSymetric());
		} else

		/* checks if is diagonal */
		if (Name.equalsIgnoreCase("isdiagonal")) {
			c = new Boolean(a.isDiagonal());
		} else

		/* gets correlation */
		if (Name.equalsIgnoreCase("corr")) {
			c = a.correlation();
		} else

		/* gets covariance */
		if (Name.equalsIgnoreCase("cov")) {
			c = a.covariance();
		} else

		/* gets Eigen Values */
		if (Name.equalsIgnoreCase("eig")) {
			c = a.eig();
		} else

		/* gets Lower and Upper triangle decomposition */
		if (Name.equalsIgnoreCase("low")) {
			c = a.lu().getL();
		}else if (Name.equalsIgnoreCase("up")) {
			c = a.lu().getU();
		}  
		else if (Name.equalsIgnoreCase("max")) {
			c = a.max();
		} else if (Name.equalsIgnoreCase("min")) {
			c = a.min();
		} else if (Name.equalsIgnoreCase("mean")) {
			c = a.mean();
		} else if (Name.equalsIgnoreCase("var")) {
			c = a.variance();
		} else if (Name.equalsIgnoreCase("rank")) {
			c = new Double(a.rank());
		} else if (Name.equalsIgnoreCase("transpose")) {
			c = a.transpose();
		} 
		else if (Name.equalsIgnoreCase("getD")) {
			c = a.eig().getD();
		} else if (Name.equalsIgnoreCase("getRealD")) {
			c = a.eig().getRealD();
		} else if (Name.equalsIgnoreCase("getImagD")) {
			c = a.eig().getImagD();
		} else if (Name.equalsIgnoreCase("getV")) {
			c = a.eig().getV();
		} 
		else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return c;
	}

	public static Object evalFunc(String Name, Matrix a, Matrix b)
			throws Exception {
		Object v = null;

		if (Name.equalsIgnoreCase("solve")) {
			v = a.solve(b);
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;
	}

	public static Object evalFunc(String Name, String fileName, Object obj)
			throws Exception {
		Object v = null;
		if (Name.equalsIgnoreCase("out")) {
			WriteFile w = new WriteFile(fileName, obj);
			w.write();
			v = new Object();
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;
	}

	public static Object evalFunc(String Name, String a) throws Exception {
		Object v = null;

		if (Name.equalsIgnoreCase("plot2d")) {
			AnimationGraph d = new AnimationGraph(a);
			v = a;
		} else if (Name.equalsIgnoreCase("plot3d")) {
			Graph3D d = new Graph3D(a);
			v = a;
		} else if (Name.equalsIgnoreCase("input")) {
			String s = javax.swing.JOptionPane.showInputDialog(null, a);
			s = s.trim();
			s += ";";
			v = MintEngine.compile(s);
		}else if (Name.equalsIgnoreCase("animplot")) {
			AnimationGraph d = new AnimationGraph(a);
			v = a;
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;

	}

	public static Object evalFunc(String Name, String a, String b)
			throws Exception {
		Object v = null;

		if (Name.equalsIgnoreCase("dx")) {
			Derivative d = new Derivative();
			v = d.eval(a, b);
		} else if (Name.equalsIgnoreCase("int")) {
			Integral in = new Integral();
			v = in.eval(a, b);
		} else if (Name.equalsIgnoreCase("paramplot")) {
			ParametricGraph d = new ParametricGraph(a, b);
			v = new String("");
		}
		else if (Name.equalsIgnoreCase("plot3d")) {
			Graph3D d = new Graph3D(a,b);
			v = a;
		}
		else if (Name.equalsIgnoreCase("animplot")) {
			AnimationGraph d = new AnimationGraph(a, b);
			v = new String("");
		}
		else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;
	}

	public static Object evalFunc(String Name, BigInteger a) throws Exception {
		Object c = null;
		if (Name.equalsIgnoreCase("isprime")) {
			Prime p = new Prime();
			c = p.isPrime(a.longValue());
		} else if (Name.equalsIgnoreCase("primes")) {
			Prime p = new Prime();
			c = p.getPrimes(a.intValue());
		} else if (Name.equalsIgnoreCase("sumseries")) {
			c = Series.sumSeries(a);
		}
		else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return c;
	}

	public static Object evalFunc(String Name, Matrix m, Double a, Double b,
			Double c) throws Exception {
		Object v = null;
		if (Name.equalsIgnoreCase("set")) {
			m.set(a.intValue(), b.intValue(), c.doubleValue());
			v = m;
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return v;
	}

	public static Object evalFunc(String Name, Double a, Double b)
			throws Exception {
		Object c = null;
		if (Name.equalsIgnoreCase("min")) {
			c = a.compareTo(b) < 0 ? a : b;
		} else if (Name.equalsIgnoreCase("max")) {
			c = a.compareTo(b) > 0 ? a : b;
		}
		/**
		 * Matrix Functions
		 */

		else if (Name.equalsIgnoreCase("eye")) {
			int l = a.intValue();
			double x = b.doubleValue();
			double[] d = new double[l];
			for (int i = 0; i < l; i++) {
				d[i] = x;
			}
			c = Matrix.diagonal(d);
		} else if (Name.equalsIgnoreCase("random")) {
			int row = a.intValue();
			int col = b.intValue();
			NumberList list = new NumberList();
			for (int i = 0; i < row; i++) {
				Vector v = new Vector();
				for (int j = 0; j < col; j++) {
					v.add(new Double(Math.random()));
				}
				list.add(v);
			}
			c = list;
		} else if (Name.equalsIgnoreCase("comb")) {
			c = Series.comb(a,b);
		} else if (Name.equalsIgnoreCase("perm")) {
			c = Series.perm(a, b);
		} else if (Name.equalsIgnoreCase("complex")) {
			c = new Complex(a.doubleValue(), b.doubleValue());
		} else if (Name.equalsIgnoreCase("polar")) {
			c = Complex.polar(a.doubleValue(), b.doubleValue());
		}
		else if (Name.equalsIgnoreCase("sumseriesn")) {
			c = Series.sumNSeries(a.longValue(),b.longValue());
		}
		else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return c;
	}

	public static Object evalFunc(String Name, NumberList x) throws Exception {
		Object obj = null;
		if (Name.equalsIgnoreCase("matrix")) {
			double[][] mat = vectorToArray(x);
			Matrix m = new Matrix(mat);
			obj = m;
		} else if (Name.equalsIgnoreCase("perm")) {
			Permutation p = new Permutation(x);
			obj = p.compute();
		}
		else if (Name.equalsIgnoreCase("length")) {
			obj = new Double(x.size());
		}
		else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return obj;

	}

	public static Object evalFunc(String Name, NumberList x, Double v)
			throws Exception {
		Object obj = null;
		if (Name.equalsIgnoreCase("comb")) {
			Combination c = new Combination(x, v.intValue());
			obj = c.compute();
		} else {
			throw new Exception(Name + " : Function Not Found ");
		}

		return obj;

	}

	public static Object evalFunction(String name, Object[] args)
			throws Exception {
		Object obj = null;
		Class cx = Functions.class;

		Class[] params = new Class[args.length + 1];
		int len = args.length + 1;

		try {

			params[0] = name.getClass();
			for (int i = 1; i < len; i++) {
				params[i] = ((Object) args[i - 1]).getClass();
			}

			Method m = cx.getMethod("evalFunc", params);

			Object[] args1 = new Object[args.length + 1];
			args1[0] = name;

			for (int i = 1; i < len; i++) {
				args1[i] = args[i - 1];
			}

			obj = m.invoke(null, args1);
			return obj;
		} catch (Exception e) {
			Vector v = new Vector();

			for (int i = 1; i < params.length; i++) {
				v.add(params[i].getName());
			}
			String msg = new String(v.toString());
			msg = name + "(" + msg + ")";
			throw new Exception(msg + " : Function Not Found");
		}
	}

	/** *************** Private Functions *************** */
	public static double[][] vectorToArray(Vector v) {
		Vector v1;
		double[][] temp = new double[v.size()][];
		int count = 0;
		Iterator i = v.iterator();

		while (i.hasNext()) {
			Object o = i.next();
			if (o instanceof Vector) {
				v1 = (Vector) o;
				double[] temp1 = vectorToDoubleArray(v1);

				if (temp1 != null) {
					temp[count++] = temp1;
				}
			}
		}

		return temp;
	}

	private static double[] vectorToDoubleArray(Vector v) {
		double[] temp = null;
		try {

			if (v != null) {
				Iterator i = v.iterator();
				temp = new double[v.size()];
				int count = 0;

				while (i.hasNext()) {
					Object o = i.next();
					temp[count] = ((Double) o).doubleValue();
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	private static double sgn(double x) {
		if (x == 0.0) {
			return 0.0;
		}

		return x < 0.0 ? -1.0 : 1.0;
	}

}