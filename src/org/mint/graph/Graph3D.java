package org.mint.graph;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import xfunctions.Graph3DPanel;

/**
 */
public class Graph3D extends JFrame {

	private static int index;

	public Graph3D() {
		this("x^y");
	}

	public Graph3D(String function) {
		this("Graph " + ++index, function);
	}

	public Graph3D(String title, String function) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setTitle(title);

		try {
			Graph3DPanel g = new Graph3DPanel(function, new double[]{-2D, 2D,
					-2D, 2D, -4D, 4D, 20});

			Container c = getContentPane();
			c.setLayout(new BorderLayout(2, 2));
			c.add(g);
			pack();
			this.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}