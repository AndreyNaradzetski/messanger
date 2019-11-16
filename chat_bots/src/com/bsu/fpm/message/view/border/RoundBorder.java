package com.bsu.fpm.message.view.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class RoundBorder extends AbstractBorder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Color color;
	
	private final double arcw;
	
	private final double arch;
	
	public RoundBorder(Color color, double arcw, double arch) {
		super();
		this.color = color;
		this.arcw = arcw;
		this.arch = arch;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		super.paintBorder(c, g, x, y, width, height);
		if ( g instanceof Graphics2D) {
			Graphics2D graphics2d = (Graphics2D)g;
			graphics2d.setColor(color);
			graphics2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, arcw, arch));
		}
	}
}
