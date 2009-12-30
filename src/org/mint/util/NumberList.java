package org.mint.util;
import java.util.Vector;

public class NumberList extends Vector {
	private boolean isNumber;

	public NumberList(Vector v) {
		super(v);
	}

	public NumberList() {
		super();
	}

	public boolean getIsNumber() {
		return isNumber;
	}
}