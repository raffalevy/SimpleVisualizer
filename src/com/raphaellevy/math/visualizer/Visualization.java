package com.raphaellevy.math.visualizer;

import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class Visualization {protected Visualizer visualizer;
	protected abstract Visualizer getVisualizer();
	public abstract JPanel getPanel();
}
