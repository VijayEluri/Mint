package org.mint.graph;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import xfunctions.Graph3DPanel;

public class Graph2D extends JFrame {
	public Graph2D(String function) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);

		try {
			Graph3DPanel g = new Graph3DPanel(function, new double[]{-4D, 4D,
					-4D, 4D, -4D, 4D, 20});
			Container c = getContentPane();
			c.setLayout(new BorderLayout(2, 2));
			c.add(g);
			pack();
			this.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Graph2D() {
		this("x^y");
	}
}