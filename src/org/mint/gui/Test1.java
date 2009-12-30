/*
 * Created on Feb 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.mint.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Test1 {

	public static void main(String[] args) {

		String[] pattern = {"abs", "print"};
		String text = "print();print(); abs();";

		for (int i = 0; i < pattern.length; i++) {
			Pattern p = Pattern.compile(pattern[i]);
			Matcher match = p.matcher(text);
			System.out.println(pattern[i] + ":" + i + ":" + text);

			int start = -1, end = -1;

			while (match.find(end + 1)) {
				for (int j = 0; j <= match.groupCount(); j++) {
					if (match.group(j) == null)
						continue;
					start = match.start(j);
					end = match.end(j);
					System.out.println("Start:" + start + " end:" + end);
				}
			}
		}
	}

}

