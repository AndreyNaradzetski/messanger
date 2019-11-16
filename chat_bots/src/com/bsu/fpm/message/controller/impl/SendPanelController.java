package com.bsu.fpm.message.controller.impl;

import java.io.IOException;

import com.bsu.fpm.message.controller.AbstractController;
import com.bsu.fpm.message.controller.MainController;
import com.bsu.fpm.message.data.Author;
import com.bsu.fpm.message.data.AuthorType;
import com.bsu.fpm.message.data.Message;
import com.bsu.fpm.message.model.MessageModel;
import com.bsu.fpm.message.view.impl.SendPanel;

public class SendPanelController extends AbstractController<SendPanel> {

	private SendPanel component;

	public SendPanelController(MainController parent) {
		super(parent);
	}
	
	public void sendMessage(String text) {
		Author author = new Author(AuthorType.PERSON, "me");
		Message message = new Message(author, text);
		try {
			MessageModel.getInstance().save(message);
			getParent().newMessage(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public SendPanel getComponent() {
		if (component == null) {
			component = new SendPanel();
			component.setController(this);
			component.initPanel();
		}
		return component;
	}
}
