/*******************************************************************************
 * Copyright (c) 2017 Raffa Levy. All rights reserved.
 *******************************************************************************/
package com.raphaellevy.math.visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GridVisualization extends Visualization{
	private Visualizer visualizer;
	private JPanel panel;
	public GridVisualization(Visualizer v) {
		this.visualizer = v;
		panel = new GridPanel();
		panel.setPreferredSize(new Dimension(visualizer.getWindowWidth(),visualizer.getWindowHeight()));
	}
	
	@Override
	protected Visualizer getVisualizer() {
		return visualizer;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}
	
	private int scaleFactor = 1;
	public void setScaleFactor(int sf) {
		scaleFactor=sf;
	}
	private int unit = 10;
	public void setUnit(int unit) {
		this.unit = unit;
	}
	
	private class GridPanel extends JPanel{
		@Override
		public void paint(Graphics g1) {
			super.paint(g1);
			Graphics2D g = (Graphics2D) g1;
			AffineTransform save = new AffineTransform(g.getTransform());
			AffineTransform t = g.getTransform();getBackground();
			if (visualizer.visualizationCentered()) {
				t.translate(getWidth()/2, getHeight()/2);
			}
			if (visualizer.getYPreference() == Visualizer.Y_UP) {
				t.concatenate(new AffineTransform(new float[] {1, 0, 0, -1}));
			}
			t.scale(scaleFactor, scaleFactor);
			g.transform(t);
			
			for (Rectangle r : gridRectangles) {
				g.fill(AffineTransform.getScaleInstance(unit, unit).createTransformedShape(r));
			}
			for (Line2D l : gridLines) {
				g.draw(AffineTransform.getScaleInstance(unit, unit).createTransformedShape(l));
			}
		}
	}
	List<Rectangle> gridRectangles = new ArrayList<>();
	List<Line2D> gridLines = new ArrayList<>();
	
	public void clear() {
		gridRectangles.clear();
		gridLines.clear();
		panel.repaint();
	}
	
	public void addGridRectangle(Rectangle rect) {
		gridRectangles.add(rect);
		panel.repaint();
	}
	public void addGridLine(Line2D line) {
		gridLines.add(line);
		panel.repaint();
	}
}