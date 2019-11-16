package com.bsu.fpm.message.controller;

import javax.swing.JComponent;

public abstract class AbstractController<V extends JComponent> {
	
	private final MainController parent;
	
	public AbstractController(MainController parent) {
		super();
		this.parent = parent;
	}
	
	protected MainController getParent() {
		return parent;
	}
	
	public String getName() {
		return getClass().getName();
	};
	
	public abstract V getComponent();
}
