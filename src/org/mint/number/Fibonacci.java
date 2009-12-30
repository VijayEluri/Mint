package org.mint.number;

import java.math.BigInteger;

import org.mint.util.NumberList;

public class Fibonacci {

	boolean list = false;
	NumberList fibList = new NumberList();

	public Fibonacci() {
		fibList.add(BigInteger.ZERO);
		fibList.add(BigInteger.ONE);
	}

	public void setList(boolean value) {
		list = value;
	}

	public BigInteger fib(int n) {
		return fib(n - 1, 0, 1);
	}

	public BigInteger fib(int n, int a, int b) throws ArithmeticException {

		if (n < 0) {
			throw (new ArithmeticException("negative values are not defined."));
		}

		BigInteger F[] = new BigInteger[2];

		F[0] = new BigInteger(Integer.toString(a));

		if (n == 0) {
			return F[0];
		}
		F[1] = new BigInteger(Integer.toString(b));

		if (n == 1) {
			return F[1];
		}
		int i = 1;
		int e = n + 1;

		while (i++ < e) {
			F[i % 2] = F[0].add(F[1]);
			if (list) {
				fibList.add(F[i % 2]);
			}
		}
		return F[i % 2];
	}

	public NumberList getList() {
		fibList.remove(fibList.lastElement());
		return fibList;
	}

}