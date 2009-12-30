/*
 * Swing Version
 */

package org.mint.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProgramOutputWindow extends JFrame {
	OutputConsole console;

	public ProgramOutputWindow() {
		super("Console");

		JPanel p1 = new JPanel(new BorderLayout());
		console = new OutputConsole();
		console.setEditable(false);
		JScrollPane paneScrollPane1 = new JScrollPane(console);
		paneScrollPane1
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p1.add(paneScrollPane1);

		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(new JScrollPane(console));
		setContentPane(contentPane);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(600, 400);
		this.setVisible(true);
	}

	public OutputConsole getOutputConsole() {
		return console;
	}

}