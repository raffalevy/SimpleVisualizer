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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * A configurable grid/graph onto which lines and shapes can be drawn.
 * @author raffa
 */
public class GridVisualization extends Visualization{
	private Visualizer visualizer;
	private JPanel panel;
	
	/**
	 * Creates a new grid given your application's visualizer.
	 * @param v
	 */
	public GridVisualization(Visualizer v) {
		this.visualizer = v;
		panel = new GridPanel();
		panel.setPreferredSize(new Dimension(visualizer.getWindowWidth(),visualizer.getWindowHeight()));
	}
	
	/**
	 * Gets the visualizer.
	 */
	@Override
	protected Visualizer getVisualizer() {
		return visualizer;
	}

	/**
	 * Gets the panel.
	 */
	@Override
	public JPanel getPanel() {
		return panel;
	}
	
	private double scaleFactor = 1;
	/**
	 * Sets how much to zoom into the grid.
	 * @param sf
	 */
	public void setScaleFactor(double sf) {
		scaleFactor=sf;
	}
	
	private int unit = 10;
	/**
	 * sets how wide each grid square should be.
	 * @param unit
	 */
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
			
			g.transform(tr);
			
			for (Rectangle r : gridRectangles) {
				g.fill(AffineTransform.getScaleInstance(unit, unit).createTransformedShape(r));
			}
			for (Line2D l : gridLines) {
				g.draw(AffineTransform.getScaleInstance(unit, unit).createTransformedShape(l));
			}
			
			g.setTransform(save);
		}
	}
	private List<Rectangle> gridRectangles = new ArrayList<>();
	private List<Line2D> gridLines = new ArrayList<>();
	
	/**
	 * clears all shapes from the grid.
	 */
	public void clear() {
		gridRectangles.clear();
		gridLines.clear();
		panel.repaint();
	}
	
	/**
	 * Add a rectangle to be painted to the grid. Uses grid units.
	 * @param rect
	 */
	public void addGridRectangle(Rectangle rect) {
		gridRectangles.add(rect);
		panel.repaint();
	}
	
	/**
	 * Add a line to be painted to the grid. Uses grid units.
	 * @param line
	 */
	public void addGridLine(Line2D line) {
		gridLines.add(line);
		panel.repaint();
	}
	
	private AffineTransform tr = new AffineTransform();
	
	/**
	 * Centers the grid on the specified point.
	 */
	public void centerOnGridPoint(Point2D p) {
		tr.translate(-p.getX()*10, -p.getY()*10);
	}
	
	/**
	 * Sets the grid to normal center.
	 */
	public void reCenter() {
		tr = new AffineTransform();
	}
}