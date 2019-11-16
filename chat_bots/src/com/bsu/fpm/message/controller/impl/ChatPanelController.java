package com.bsu.fpm.message.controller.impl;

import java.io.IOException;

import com.bsu.fpm.message.controller.AbstractController;
import com.bsu.fpm.message.controller.MainController;
import com.bsu.fpm.message.data.Message;
import com.bsu.fpm.message.model.MessageModel;
import com.bsu.fpm.message.view.impl.ChatPanel;

public class ChatPanelController extends AbstractController<ChatPanel> {

	private ChatPanel component;

	public ChatPanelController(MainController parent) {
		super(parent);
	}
	
	public void addMessage(Message message) {
		getComponent().addMessage(message);
	}
	
	public void addMessages(Message[] messages) {
		getComponent().addMessages(messages);
	}
	
	public void clearHistory() {
		try {
			MessageModel.getInstance().clearHistory();
			getComponent().clearMessages();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ChatPanel getComponent() {
		if (component == null) {
			component = new ChatPanel();
			component.setController(this);
			component.initPanel();
		}
		return component;
	}

}
