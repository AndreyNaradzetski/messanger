package com.bsu.fpm.message.view.impl;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.bsu.fpm.message.controller.impl.BotPanelController;
import com.bsu.fpm.message.view.IMessageView;

public class BotPanel extends JPanel implements IMessageView<BotPanel, BotPanelController> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);

	private final ButtonGroup radioButtonGroup = new ButtonGroup();

	private Map<String, JRadioButton> buttons = new TreeMap<String, JRadioButton>();
	
	private BotPanelController controller;
	
	public BotPanel() {
		super();
	}
	
	@Override
	public void setController(BotPanelController controller) {
		this.controller = controller;
	}

	@Override
	public void initPanel() {
		if ( buttons.isEmpty() ) {
			setName("BotPanel");
			setLayout(new GridBagLayout());
			Border border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Available bots");
			setBorder(border);
			
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 0;
	        gbc.weighty = 0;
	        gbc.weightx = 1;
	        gbc.gridwidth = 1;
	        gbc.gridwidth = 1;
	        gbc.anchor = GridBagConstraints.NORTH; 
	        
			String[] botNames = controller.botNames();
			for (String botName : botNames) {
				add(createBotName(radioButtonGroup, botName), gbc);
				gbc.gridy++;
			}			
		}
	}
	
	public void selectBot(String botName) {
		radioButtonGroup.clearSelection();
		buttons.get(botName).setSelected(true);
	}
	
	private JPanel createBotName(ButtonGroup radioButtonGroup, String botName ) {
		JPanel oneBot = new JPanel();
		oneBot.setLayout(new GridBagLayout());
		
		JRadioButton oneButton = createButton(botName);
		radioButtonGroup.add(oneButton);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTH; 
        oneBot.add(oneButton, gbc);
		
		return oneBot;
	}
	
	private JRadioButton createButton(String name) {
		JRadioButton button = new JRadioButton(name);
		button.setFont(FONT);
		button.setActionCommand(name);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.botSelected(e.getActionCommand());
			}
		});
		buttons.put(name, button);
		return button;
	}
}
