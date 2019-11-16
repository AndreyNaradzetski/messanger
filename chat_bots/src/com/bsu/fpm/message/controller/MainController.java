package com.bsu.fpm.message.controller;

import java.io.IOException;

import javax.swing.JComponent;

import com.bsu.fpm.message.bot.AbstractBot;
import com.bsu.fpm.message.bot.BotFactory;
import com.bsu.fpm.message.controller.impl.BotPanelController;
import com.bsu.fpm.message.controller.impl.ChatPanelController;
import com.bsu.fpm.message.controller.impl.SendPanelController;
import com.bsu.fpm.message.data.Author;
import com.bsu.fpm.message.data.AuthorType;
import com.bsu.fpm.message.data.Message;
import com.bsu.fpm.message.model.MessageModel;
import com.bsu.fpm.message.view.MainView;
import com.bsu.fpm.message.view.impl.BotPanel;
import com.bsu.fpm.message.view.impl.ChatPanel;
import com.bsu.fpm.message.view.impl.SendPanel;

public class MainController {

	private MainView frame;
	
	private final SendPanelController sendController;
	
	private final ChatPanelController chatsController;
	
	private final BotPanelController botsController;
	
	public MainController() {
		super();
		this.sendController = new SendPanelController(this);
		this.chatsController = new ChatPanelController(this);
		this.botsController = new BotPanelController(this);
	}

	public MainView getFrame() {
		if ( frame == null ) {
			frame = new MainView(this);
			frame.initFrame();
		}
		return frame;
	}
	
	private SendPanelController getSendController() {
		return sendController;
	}

	private ChatPanelController getChatsController() {
		return chatsController;
	}

	private BotPanelController getBotsController() {
		return botsController;
	}

	public SendPanel getSendPanel() {
		return getComponent(getSendController());
	}
	
	public ChatPanel getChatPanel() {
		return getComponent(getChatsController());
	}

	public BotPanel getBotPanel() {
		return getComponent(getBotsController());
	}

	private <V extends JComponent> V getComponent(AbstractController<V> controller) {
		return controller.getComponent();
	}
	
	public Message[] read() {
		try {
			return MessageModel.getInstance().read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Message createBotMessage(String text) {
		AbstractBot bot = BotFactory.getInstance().getCurrentBot();
		String botText = bot.message(text); 

		Author author = new Author(AuthorType.BOT, bot.getBotName());
		Message botMessage = new Message(author, botText);
		return botMessage;
	}
	
	public void newMessage(Message message) {
		Message botMessage = createBotMessage(message.getMessage());
		try {
			MessageModel.getInstance().save(botMessage);
			Message[] toAdd = new Message[] {message, botMessage};
			getChatsController().addMessages(toAdd);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
