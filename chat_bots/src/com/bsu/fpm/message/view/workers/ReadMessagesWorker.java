package com.bsu.fpm.message.view.workers;

import java.util.List;

import javax.swing.SwingWorker;

import com.bsu.fpm.message.data.Message;
import com.bsu.fpm.message.view.MainView;

public class ReadMessagesWorker extends SwingWorker<Void, Message> {
	
	private final MainView mainView;

	public ReadMessagesWorker(MainView mainView) {
		super();
		this.mainView = mainView;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Message[] messages = mainView.getController().read();
		for (Message message : messages) {
			if (!isCancelled()) {
				publish(message);
			}
		}
		return null;
	}

	@Override
	protected void process(List<Message> chunks) {
		Message[] messages = chunks.toArray( new Message[0]);
		mainView.getController().getChatPanel().addMessages(messages);
	}
}
