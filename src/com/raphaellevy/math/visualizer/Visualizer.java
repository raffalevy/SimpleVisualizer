/*******************************************************************************
 * Copyright (c) 2017 Raffa Levy. All rights reserved.
 *******************************************************************************/
package com.raphaellevy.math.visualizer;

import javax.swing.JFrame;

public class Visualizer {
	static final int DEFAULT_WINDOW_WIDTH = 730;
	static final int DEFAULT_WINDOW_HEIGHT = 470;
	
	public static final boolean Y_UP = true;
	public static final boolean Y_DOWN = true;
	
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
	
	public void setWindowWidth(int width) {
		windowWidth = width;
	}
	public int getWindowWidth() {
		if (frame != null) {
			if (frame.getContentPane() != null) {
				return frame.getContentPane().getWidth();
			}
		}
		return windowWidth;
	}
	public void setWindowHeight(int height) {
		windowHeight = height;
	}
	public int getWindowHeight() {
		if (frame != null) {
			if (frame.getContentPane() != null) {
				return frame.getContentPane().getHeight();
			}
		}
		return windowHeight;
	}
	public void setYPreference(boolean pref) {
		yPreference = pref;
	}
	public boolean getYPreference() {
		return yPreference;
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setCenterVisualization(boolean b) {
		centerVisualization = b;
	}
	public boolean visualizationCentered() {
		return centerVisualization;
	}
	
	public void displayFrame(Visualization v) {
		frame = new JFrame();
		frame.setContentPane(v.getPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
