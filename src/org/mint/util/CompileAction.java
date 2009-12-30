/*
 * Created on Feb 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.mint.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.mint.gui.Console;
import org.mint.gui.ProgramEditor;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CompileAction implements ActionListener {
	ProgramEditor win;

	public CompileAction(ProgramEditor win) {
		this.win = win;
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			MintEngine.compile(win.getSourceCode());
			ProgramEditor.getErrorConsole().setMessage(
					Console.getTimeStamp() + "> Sucessfully Compiled");
		} catch (MintException e) {
			//ProgramEditor.getErrorConsole().setError("==========================");
			ProgramEditor.getErrorConsole().setError(
					Console.getTimeStamp() + ">" + e.toString());
			//ProgramEditor.getErrorConsole().setError("==========================");
		}
	}

}