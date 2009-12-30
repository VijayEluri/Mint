/*
 * Created on Feb 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.mint.gui;

import java.io.File;

import javax.swing.text.DefaultStyledDocument;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProgramConsole extends Console{

	boolean saved = false;
	File filename;

	public boolean isSaved() {
		return saved == true;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public File getFile() {
		return filename;
	}

	public void setFile(File filename) {
		this.filename = filename;
	}
	
	public void makeNew() {
		this.setDocument(new DefaultStyledDocument());
		filename = null;
		setSaved(false);
	}
}