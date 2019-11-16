package com.bsu.fpm.message.view;

import javax.swing.JComponent;

import com.bsu.fpm.message.controller.AbstractController;

public interface IMessageView<V extends JComponent, T extends AbstractController<V>> {

	public void setController(T controller);
	
	public void initPanel();
}
