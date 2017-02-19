/*******************************************************************************
 * Copyright (c) 2017 Raffa Levy. All rights reserved.
 *******************************************************************************/
package com.raphaellevy.math.visualizer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Represents a visualization application and contains preferences.
 * 
 * @author raffa
 */
public class Visualizer {
	static final int DEFAULT_WINDOW_WIDTH = 730;
	static final int DEFAULT_WINDOW_HEIGHT = 470;

	public static final boolean Y_UP = true;
	public static final boolean Y_DOWN = false;

	/**
	 * Get a new Visualizer. This should be the first SimpleVisualizer method
	 * called in your application.
	 */
	public static Visualizer open() {
		return new Visualizer();
	}

	private JFrame frame;

	private int windowWidth = DEFAULT_WINDOW_WIDTH;
	private int windowHeight = DEFAULT_WINDOW_HEIGHT;
	private boolean yPreference = Y_UP;
	private boolean centerVisualization = true;

	private Visualizer() {

	}

	/**
	 * Set the starting width of the window.
	 * 
	 * @param width
	 */
	public void setWindowWidth(int width) {
		windowWidth = width;
	}

	/**
	 * @return The current window width.
	 */
	public int getWindowWidth() {
		if (frame != null) {
			if (frame.getContentPane() != null) {
				return frame.getContentPane().getWidth();
			}
		}
		return windowWidth;
	}

	/**
	 * Set the starting height of the window.
	 * 
	 * @param height
	 */
	public void setWindowHeight(int height) {
		windowHeight = height;
	}

	/**
	 * @return The current window height.
	 */
	public int getWindowHeight() {
		if (frame != null) {
			if (frame.getContentPane() != null) {
				return frame.getContentPane().getHeight();
			}
		}
		return windowHeight;
	}

	/**
	 * Either {@link Visualizer#Y_UP} or {@link Visualizer#Y_DOWN}. If Y_UP,
	 * uses math-like behavior with the y axis going up. If Y_DOWN, uses
	 * computer behavior with the y axis going down.
	 * 
	 * @param pref
	 */
	public void setYPreference(boolean pref) {
		yPreference = pref;
	}

	/**
	 * Get current Y preference; see {@link Visualizer#setYPreference
	 * setYPreference()}.
	 * 
	 * @return
	 */
	public boolean getYPreference() {
		return yPreference;
	}

	/**
	 * @return The application's JFrame.
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * If true, places origin at center of window rather than corner.
	 * 
	 * @param b
	 */
	public void setCenterVisualization(boolean b) {
		centerVisualization = b;
	}

	/**
	 * @return whether the visualization is centered; see
	 *         {@link Visualizer#setCenterVisualization(boolean)}
	 */
	public boolean visualizationCentered() {
		return centerVisualization;
	}

	/**
	 * Displays the given visualization in a new frame.
	 * 
	 * @param v
	 */
	public synchronized void displayFrame(Visualization v) {
		if (frame != null) {
			RuntimeException e = new RuntimeException("There already is a frame!");
			throw e;
		}
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					frame = new JFrame();
					frame.setContentPane(v.getPanel());
					frame.pack();
					frame.setVisible(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
