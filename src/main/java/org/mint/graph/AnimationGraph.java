package org.mint.graph;

import java.awt.Container;

import javax.swing.JFrame;

import xfunctions.AnimatorPanel;

/**
 */
public class AnimationGraph extends JFrame {

	private static int index;

	public AnimationGraph() {
		this("x^y");
	}

	public AnimationGraph(String function) {
		this("Graph " + ++index, function);
	}

	public AnimationGraph(String title, String func) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setVisible(true);
		this.setTitle(title);

		try {
			AnimatorPanel p = new AnimatorPanel(func);

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