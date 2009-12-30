package org.mint.graph;

import java.awt.Container;

import javax.swing.JFrame;

import xfunctions.ParametricCurvesPanel;

/**
 */
public class ParametricGraph extends JFrame {
	private static int index;

	public ParametricGraph() {
		this("2*sin(3*t)", "3*cos(5*t)");
	}

	public ParametricGraph(String function, String function1) {
		this("Graph " + ++index, function, function1);
	}

	public ParametricGraph(String title, String func, String func1) {

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setTitle(title);

		try {
			ParametricCurvesPanel p = new ParametricCurvesPanel(func, func1);
			Container c = getContentPane();
			c.add(p);
			pack();
			this.setVisible(true);
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}