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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A configurable grid/graph onto which lines and shapes can be drawn.
 * @author raffa
 */
public final class GridVisualization extends Visualization{
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
	protected synchronized Visualizer getVisualizer() {
		return visualizer;
	}

	/**
	 * Gets the panel.
	 */
	@Override
	public synchronized JPanel getPanel() {
		return panel;
	}
	
	private double scaleFactor = 1;
	
	/**
	 * Sets how much to zoom into the grid.
	 * @param sf
	 */
	public synchronized void setScaleFactor(double sf) {
		scaleFactor=sf;
		panel.repaint();
	}
	
	/**
	 * Gets the current scaling factor for the grid
	 */
	public synchronized double getScaleFactor() {
		return scaleFactor;
	}
	
	/**
	 * Multiplies the current scaling factor by a given amount.
	 * @param factor
	 */
	public synchronized void zoom(double factor) {
		scaleFactor = scaleFactor * factor;
		panel.repaint();
	}
	
	private int unit = 10;
	/**
	 * sets how wide in pixels (before scaling) each grid square should be.
	 * @param unit
	 */
	public synchronized void setUnit(int unit) {
		this.unit = unit;
		panel.repaint();
	}
	
	/**
	 * Gets the current grid unit, in pixels (before scaling)
	 * @return
	 */
	public synchronized int getUnit() {
		return unit;
	}
	
	private Stack<Color> colorStack = new Stack<>();
	
	private class GridPanel extends JPanel{
		@Override
		public synchronized void paint(Graphics g1) {
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
			
			drawAxes(g);
			for (Rectangle r : gridRectangles.keySet()) {
				colorStack.push(g.getColor());
				g.setColor(gridRectangles.get(r));
				g.fill(AffineTransform.getScaleInstance(unit, unit).createTransformedShape(r));
				g.setColor(colorStack.pop());
			}
			for (Line2D l : gridLines.keySet()) {
				colorStack.push(g.getColor());
				g.setColor(gridLines.get(l));
				g.draw(AffineTransform.getScaleInstance(unit, unit).createTransformedShape(l));
				g.setColor(colorStack.pop());
			}
			
			g.setTransform(save);
		}
		@Override
		public void repaint() {
			JPanel p = this;
			if (SwingUtilities.isEventDispatchThread()) {
				super.repaint();
			} else {
				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							p.repaint();
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private Map<Rectangle,Color> gridRectangles = new HashMap<>();
	private Map<Line2D,Color> gridLines = new HashMap<>();
	
	private Color defaultObjectColor = Color.DARK_GRAY;
	
	/**
	 * Sets the default color for objects added to the grid.
	 * @param c
	 */
	public synchronized void setDefaultObjectColor(Color c) {
		defaultObjectColor = c;
	}
	
	/**
	 * Gets the default color for objects added to the grid.
	 */
	public synchronized Color getDefaultObjectColor() {
		return defaultObjectColor;
	}
	
	/**
	 * clears all shapes from the grid.
	 */
	public synchronized void clear() {
		gridRectangles.clear();
		gridLines.clear();
		panel.repaint();
	}
	
	/**
	 * Add a rectangle to be painted to the grid. Uses grid units.
	 * @param rect
	 */
	public synchronized  void addGridRectangle(Rectangle rect) {
		gridRectangles.put(rect, defaultObjectColor);
		panel.repaint();
	}
	
	/**
	 * Add a rectangle to be painted to the grid. Uses grid units.
	 * @param rect
	 * @param c The color of the rectangle
	 */
	public synchronized void addGridRectangle(Rectangle rect, Color c) {
		gridRectangles.put(rect, c);
		panel.repaint();
	}
	
	/**
	 * Add a line to be painted to the grid. Uses grid units.
	 * @param line
	 */
	public synchronized void addGridLine(Line2D line) {
		gridLines.put(line, defaultObjectColor);
		panel.repaint();
	}
	
	/**
	 * Add a line to be painted to the grid. Uses grid units.
	 * @param line
	 * @param c The color of the line
	 */
	public synchronized void addGridLine(Line2D line, Color c) {
		gridLines.put(line, c);
		panel.repaint();
	}
	
	private AffineTransform tr = new AffineTransform();
	
	/**
	 * Centers the grid on the specified point.
	 */
	public synchronized void centerOnGridPoint(Point2D p) {
		tr.translate(-p.getX()*10, -p.getY()*10);
		panel.repaint();
	}
	
	/**
	 * Sets the grid to normal center.
	 */
	public synchronized void reCenter() {
		tr = new AffineTransform();
	}
	
	private boolean axesShown;
	private Color axesColor = Color.black;
	
	/**
	 * Sets whether to show x and y axes.
	 * @param b
	 */
	public synchronized void showAxes(boolean b) {
		axesShown = b;
		panel.repaint();
	}
	
	/**
	 * Set the color of the axes
	 * @param color
	 */
	public synchronized void setAxesColor(Color color) {
		axesColor = color;
		if (axesShown) {
			panel.repaint();
		}
	}
	
	/**
	 * Set the color of the axes
	 * @param red 0-255
	 * @param green 0-255
	 * @param blue 0-255
	 */
	public synchronized void setAxesColor(int red, int green, int blue) {
		setAxesColor(new Color(red, green, blue));
	}
	
	private void drawAxes(Graphics2D g) {
		if (axesShown) {
			colorStack.push(g.getColor());
			g.setColor(axesColor);
			int wid = panel.getGraphicsConfiguration().getBounds().width;
			int hei = panel.getGraphicsConfiguration().getBounds().height;
			g.drawLine(-wid, 0, wid, 0);
			g.drawLine(0, -hei, 0, hei);
			g.setColor(colorStack.pop());
		}
	}
}