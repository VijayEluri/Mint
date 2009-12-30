package org.mint.number;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Series {

	final static BigInteger one = BigInteger.ONE;
	final static BigInteger zero = BigInteger.ZERO;

	public static BigInteger fact(long n) {
		if (n <= 0) {
			return one;
		} else {
			BigInteger b = new BigInteger("1");
			BigInteger x = new BigInteger("1");
			for (long i = 1; i <= n; i++) {
				b = b.multiply(x);
				x = x.add(one);
			}

			return b;
		}
	}
	
	public static Double perm(Double n, Double r) {
		Double d = Double.valueOf(fact(n.longValue()).divide(fact(n.longValue()-r.longValue())).toString());
		return d;
	}
	
	public static Double comb(Double n, Double r) {
		long val1 = n.longValue() - r.longValue();
		
		BigInteger b1 = fact(val1);
		BigInteger b2 = fact(r.longValue());
		BigInteger b3 = fact(n.longValue());
		BigInteger b4 = b3.divide(b2.multiply(b1));
		
		return Double.valueOf(b4.toString());
	}
	
	
	
	public static BigInteger sumSeries(long value) {
		String a = String.valueOf(value);

		return sumSeries(new BigInteger(a));
	}

	public static BigInteger sumSeries(BigInteger value) {
		BigInteger iteration = value;
		BigInteger count = zero;
		BigInteger sum = zero;
		BigDecimal d;

		while (true) {
			iteration = iteration.subtract(one);
			count = count.add(one);
			sum = sum.add(count);
			if (iteration.compareTo(zero) == 0)
				break;
		}

		return sum;
	}
	
	public static Double sumNSeries(long value, long n) {
		return Double.valueOf(sumNSeries(BigInteger.valueOf(value),BigInteger.valueOf(n)).longValue()+"");
	}

	public static BigInteger sumNSeries(BigInteger value, BigInteger n) {
		BigInteger iteration = value;
		BigInteger count = zero, count1 = zero;
		BigInteger sum = zero;
		BigDecimal d;

		while (true) {
			iteration = iteration.subtract(one);
			count = count.add(one);
			count1 = count.pow(n.intValue());
			sum = sum.add(count1);
			if (iteration.compareTo(zero) == 0)
				break;
		}

		return sum;
	}

}