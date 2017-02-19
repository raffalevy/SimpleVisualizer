/*******************************************************************************
 * Copyright (c) 2017 Raffa Levy. All rights reserved.
 *******************************************************************************/
package com.raphaellevy.math.visualizer;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Represents a visualization to be rendered in a JFrame.
 * @author raffa
 */
public abstract class Visualization {protected Visualizer visualizer;
	protected abstract Visualizer getVisualizer();
	
	/**
	 * @return The panel in which to render.
	 */
	public abstract JPanel getPanel();
}
