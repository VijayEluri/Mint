package org.mint.util;

import java.io.Serializable;
import java.io.StringReader;

import org.mint.gui.Console;
import org.mint.gui.OutputConsole;
import org.mint.gui.ProgramEditor;
import org.mint.src.MintLexer;
import org.mint.src.MintParser;
import org.mint.src.MintTree;

import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;

public class MintEngine extends Thread implements Serializable {

	public static Object compile(String program, OutputConsole outconsole)
			throws MintException {
		StringReader sin = new StringReader(program);
		MintLexer lex = new MintLexer(sin);
		MintParser parser = new MintParser(lex);

		try {
			parser.program();
		} catch (RecognitionException e) {
			throw new MintException(e.getMessage(), e.getLine(), e.getColumn());
		} catch (TokenStreamException e) {
			e.printStackTrace();
			throw new MintException(e.getMessage(), -1, -1);
		}

		CommonAST tree = (CommonAST) parser.getAST();
		MintTree exec = new MintTree();
		try {
			//outconsole.append(Console.getTimeStamp() + ">
			// ------------------------");
			outconsole.append(Console.getTimeStamp() + ">");
			return exec.program(tree);
		} catch (RecognitionException e1) {
			throw new MintException(e1.getMessage(), e1.getLine(), e1
					.getColumn());
		}
	}

	public static Object compile(String program) throws MintException {
		return compile(program, ProgramEditor.getOutputConsole());
	}
}