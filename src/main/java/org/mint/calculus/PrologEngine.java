/**
 * <p>
 * Title: jCalculus
 * </p>
 * <p>
 * Description: Java Calculus
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003 Maheshwaran.S
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Maheshwaran.S
 * @version 1.0
 */

package org.mint.calculus;

import gnu.prolog.database.PrologTextLoaderError;
import gnu.prolog.io.ParseException;
import gnu.prolog.io.ReadOptions;
import gnu.prolog.io.TermReader;
import gnu.prolog.io.TermWriter;
import gnu.prolog.io.WriteOptions;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.Term;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.PrologCode;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;

public class PrologEngine implements Serializable {
	private HashMap variableResult;
	private StringReader sr;
	private TermReader tr;
	private ReadOptions rd_ops;
	private Environment env;
	private Interpreter interpreter;
	private WriteOptions wr_ops;
	private Term goalTerm;
	private Interpreter.Goal goal;
	private String fileName, goalToExecute;
	private TermWriter out;

	public PrologEngine(String fileName) throws ParseException {

		this.fileName = fileName;
		env = new Environment();
		env.ensureLoaded(AtomTerm.get(fileName));
		interpreter = env.createInterpreter();
		env.runIntialization(interpreter);
		/* show any error occured */
		for (Iterator iter = env.getLoadingErrors().iterator(); iter.hasNext();) {
			PrologTextLoaderError err = (PrologTextLoaderError) iter.next();
			System.err.println(err);
		}
		rd_ops = new ReadOptions();
		rd_ops.operatorSet = env.getOperatorSet();

		wr_ops = new WriteOptions();
		wr_ops.operatorSet = env.getOperatorSet();

		out = new TermWriter(new StringWriter());
	}

	public String getResult(String variable) throws Exception {
		Object obj = variableResult.get(variable);

		if (obj == null) {
			throw new Exception("No Such Variable : " + variable);
		} else {
			Term res = ((Term) obj).dereference();
			String output = TermWriter.toString(res);
			return output;
		}
	}

	public void exec(String goalToExecute) {
		try {
			this.goalToExecute = goalToExecute;
			sr = new StringReader(goalToExecute);
			tr = new TermReader(sr);

			goalTerm = tr.readTermEof(rd_ops);
			goal = interpreter.prepareGoal(goalTerm);

			int flag = interpreter.execute(goal);

			switch (flag) {
				case PrologCode.SUCCESS :
				case PrologCode.SUCCESS_LAST :
					variableResult = (HashMap) rd_ops.variableNames;
					break;
				case PrologCode.FAIL :
					throw new Exception("Cannot Execute for : " + goalToExecute);
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}
}