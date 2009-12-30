/*
 * Swing Version
 */

package org.mint.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import org.mint.util.CompileAction;

public class ProgramEditor extends JFrame {
	ProgramConsole program;
	static ErrorConsole error;
	JSplitPane split;
	static ProgramOutputWindow console;
	public static final String title = "Mint - Mathematical Interpreter";
	JFileChooser fc = new JFileChooser(".");

	public ProgramEditor() {
		super(title);

		JPanel p1 = new JPanel(new BorderLayout());
		program = new ProgramConsole();
		program.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		JScrollPane paneScrollPane1 = new JScrollPane(program);
		paneScrollPane1
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p1.add(paneScrollPane1);

		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(new JLabel("Error / Warnings:"), BorderLayout.NORTH);
		error = new ErrorConsole();
		JScrollPane paneScrollPane = new JScrollPane(error);
		paneScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p2.add(paneScrollPane);

		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p1, p2);
		split.setOneTouchExpandable(true);
		split.setResizeWeight(.8);

		JToolBar t = new JToolBar(JToolBar.HORIZONTAL);
		t.setFloatable(false);

		JButton compile;
		t.add(compile = new JButton("Compile / Run"));
		compile.addActionListener(new CompileAction(this));

		JButton clearoutput;
		t.add(clearoutput = new JButton("Clear Output"));
		clearoutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				console.getOutputConsole().setText("");
			}
		});

		JButton exit;
		t.add(exit = new JButton("Exit"));
		exit.addActionListener(new ExitAction());

		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(split);
		contentPane.add(t, BorderLayout.NORTH);
		setContentPane(contentPane);

		addMenuBar();
		console = new ProgramOutputWindow();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		this.setVisible(true);
	}

	public static ErrorConsole getErrorConsole() {
		return error;
	}

	public static OutputConsole getOutputConsole() {
		return console.getOutputConsole();
	}

	public String getSourceCode() {
		return program.getText();
	}

	void addMenuBar() {
		JMenuBar bar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic('F');

		JMenuItem newfile = new JMenuItem("New File"), openfile = new JMenuItem(
				"Open"), save = new JMenuItem("Save"), saveas = new JMenuItem(
				"Save As"), exit = new JMenuItem("Exit");

		newfile.setMnemonic('N');
		newfile.setAccelerator(KeyStroke.getKeyStroke("control N"));
		newfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				program.makeNew();
				ProgramEditor.this.setTitle(title);
			}
		});

		openfile.setMnemonic('O');
		openfile.setAccelerator(KeyStroke.getKeyStroke("control O"));
		openfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}
		});

		exit.setMnemonic('W');
		exit.setAccelerator(KeyStroke.getKeyStroke("control W"));
		exit.addActionListener(new ExitAction());

		save.setMnemonic('S');
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFile(false);
			}

		});

		saveas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFile(true);
			}

		});

		file.add(newfile);
		file.add(openfile);
		file.add(save);
		file.add(saveas);
		file.add(exit);

		bar.add(file);
		this.setJMenuBar(bar);
	}

	class ExitAction implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (!program.isSaved()) {
				saveFile(false);
			}
			int ret = JOptionPane.showConfirmDialog(ProgramEditor.this,
					"Exit?", "Do you want to exit", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);

			if (ret == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		}
	}

	class MintFileFilter extends FileFilter {

		public boolean accept(File f) {
			return f.isDirectory()
					|| f.getName().toLowerCase().endsWith("mint");
		}

		public String getDescription() {
			return "Mint File (*.mint)";
		}

	}

	void saveFile(boolean saveas) {

		fc.setFileFilter(new MintFileFilter());
		int returnVal = -1;
		File f = null;
		if (!program.isSaved() || saveas == true) {
			int ret = JOptionPane.showConfirmDialog(this,
					"Do you want to save this file", "File Save",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (ret == JOptionPane.OK_OPTION) {
				returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					f = fc.getSelectedFile();
					fc.setCurrentDirectory(f);
					program.setFile(f);
					program.setSaved(true);
					this.setTitle(title + "-" + f.getName());
				}
			}
		} else {
			f = program.getFile();
		}

		try {
			if (f == null)
				return;
			FileOutputStream out = new FileOutputStream(f);
			out.write(program.getText().getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Error", "No Such File Found",
					JOptionPane.ERROR);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error", "Error Saving file",
					JOptionPane.ERROR);
		}
	}

	void openFile() {
		fc.setFileFilter(new MintFileFilter());
		int returnVal = -1;
		File f = null;
		returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
			program.setFile(f);
			program.setSaved(true);
			fc.setCurrentDirectory(f);
			this.setTitle(title + "-" + f.getName());
		}

		StringBuffer buf = new StringBuffer();
		try {
			if (f == null)
				return;
			FileInputStream in = new FileInputStream(f);
			int c = -1;
			while ((c = in.read()) != -1) {
				buf.append((char) c);
			}
			in.close();
			program.setText(buf.toString());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Error", "No Such File Found",
					JOptionPane.ERROR);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error", "Error Saving file",
					JOptionPane.ERROR);
		}
	}

}