package com.bsu.fpm.message.view.workers;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import com.bsu.fpm.message.data.Message;
import com.bsu.fpm.message.data.Pair;
import com.bsu.fpm.message.utils.MessageUtils;
import com.bsu.fpm.message.view.border.RoundBorder;
import com.bsu.fpm.message.view.impl.ChatPanel;

public class AddMessageWorker extends SwingWorker<Void, Pair<JLabel, GridBagConstraints>> {
	
	//https://colorscheme.ru/ and https://www.color-hex.com/color/ced1d9
	private static final Color BOT_COLOR = new Color(181,217,207);
	
	private static final Color PERSON_COLOR = new Color(206,209,217);

	private final Message[] messages;
	private final ChatPanel chatPanel;
	private final AtomicInteger counter;

	public AddMessageWorker(Message[] messages, ChatPanel chatPanel, AtomicInteger counter) {
		super();
		this.messages = messages;
		this.chatPanel = chatPanel;
		this.counter = counter;
	}

	private GridBagConstraints createMessageConstraints(Message message) {
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = counter.getAndIncrement();
		gbc.weighty = 1;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		gbc.gridwidth = 1;
		gbc.anchor = MessageUtils.isPersonalMessage(message) ? GridBagConstraints.EAST : GridBagConstraints.WEST;

		return gbc;
	}
	
	private JLabel createFormattedMessage(Message message) {
		JLabel label = new JLabel();
		label.setBorder(new RoundBorder(Color.BLACK, 15, 5));
		label.setOpaque(true);
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		label.setBackground(MessageUtils.isPersonalMessage(message) ? PERSON_COLOR: BOT_COLOR);
		label.setText(message.getMessage());
		return label;
	} 

	@Override
	protected Void doInBackground() throws Exception {
		for (Message message : messages) {
			if (!isCancelled()) {
				JLabel label = createFormattedMessage(message);
				GridBagConstraints gbc = createMessageConstraints(message);
				publish(new Pair<JLabel, GridBagConstraints>(label, gbc));
			}
		}
		return null;
	}

	@Override
	protected void process(List<Pair<JLabel, GridBagConstraints>> chunks) {
		for (Pair<JLabel, GridBagConstraints> pair : chunks) {
			chatPanel.add(pair.getKey(), pair.getValue());
		}
	}
	
	@Override
	protected void done() {
		chatPanel.revalidate();
		super.done();
	}
}
