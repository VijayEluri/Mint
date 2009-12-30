/*
 * Created on Feb 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.mint.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

import javax.swing.JTextPane;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Console extends JTextPane {

	public Console() {
		super();
		this.setFont(new Font("Lucida Console", Font.PLAIN, 12));
	}

	public void setError(String msg) {
		this.setForeground(Color.RED);
		append(msg);
	}

	public void setMessage(String msg) {
		append(msg);
	}

	public void append(String msg) {
		String txt = this.getText() + msg + "\n";
		this.setText(txt);
	}

	public static Date getTimeStamp() {
		return new Date(System.currentTimeMillis());
	}

}