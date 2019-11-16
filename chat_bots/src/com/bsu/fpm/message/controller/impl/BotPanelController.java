package com.bsu.fpm.message.controller.impl;

import com.bsu.fpm.message.bot.BotFactory;
import com.bsu.fpm.message.controller.AbstractController;
import com.bsu.fpm.message.controller.MainController;
import com.bsu.fpm.message.view.impl.BotPanel;

public class BotPanelController extends AbstractController<BotPanel> {

	private BotPanel component;
	
	public BotPanelController(MainController parent) {
		super(parent);
	}
	
	public String[] botNames() {
		return BotFactory.getInstance().getBotNames();
	}
	
	public void botSelected(String newBotName) {
		BotFactory.getInstance().setCurrentBot(newBotName);
	}
	
	@Override
	public BotPanel getComponent() {
		if ( component == null ) {
			component = new BotPanel();
			component.setController(this);
			component.initPanel();
			component.selectBot(BotFactory.getInstance().getCurrentBot().getBotName());
		}
		return component;
	}
}
