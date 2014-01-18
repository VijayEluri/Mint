package org.mint.number;

import java.math.BigInteger;

import org.mint.util.NumberList;

public class Prime {

	final static BigInteger one = BigInteger.ONE;
	final static BigInteger zero = BigInteger.ZERO;
	final static BigInteger two = new BigInteger("2");

	public BigInteger prime(long n) {
		return BigInteger.ONE;
	}

	public BigInteger sqrt(long num) {
		return sqrt(new BigInteger(String.valueOf(num)));
	}

	public BigInteger sqrt(BigInteger num) {

		BigInteger x = num.divide(two);

		for (int i = 0; i < 50; i++) {
			x = x.add(num.divide(x)).divide(two);
		}
		return x;
	}

	public NumberList getPrimes(int upTo) {
		int size = upTo + 1;
		NumberList primes = new NumberList();
		boolean[] flags = new boolean[size];
		double limit = Math.sqrt(size);

		// Set flags
		int odd = 1, even = 2, i = 0;

		/*
		 * for (i = 2; i < size; i++) { flags[i] = true; }
		 */
		// Cross out multiples of 2
		int j = 2;
		for (i = 2; i < size; i = i + j) {
			flags[i] = true; //crossed out
		}

		// Cross out multiples of odd numbers

		for (j = 3; j <= limit; j = j + 2) {
			if (!flags[j]) {
				for (i = j + j; i < size; i = i + j) {
					flags[i] = true;
				}
			}
		}

		// Build list of primes from what is left

		for (i = 2; i < size; i++) {
			if (!flags[i]) {
				primes.addElement(new Long(i));
			}
		}

		return primes;
	}

	public Boolean isPrime(long n) {
		NumberList v = getPrimes((int) n + 1);
		return new Boolean(v.indexOf(new Long(n)) >= 0);
	}

}