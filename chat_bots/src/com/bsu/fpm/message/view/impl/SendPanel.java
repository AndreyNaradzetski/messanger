package com.bsu.fpm.message.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.bsu.fpm.message.controller.impl.SendPanelController;
import com.bsu.fpm.message.emoji.Emoji;
import com.bsu.fpm.message.view.IMessageView;

public class SendPanel extends JPanel implements IMessageView<SendPanel, SendPanelController> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SendPanelController controller;

	private JTextArea textPane;

	private JButton sendButton;
	
	private JButton smileButton;
	
	private JPanel smilePanel;
	
	private JFrame smileFrame;
	
	private final AtomicBoolean smileVisible = new AtomicBoolean(true);

	public SendPanel() {
		super();
	}

	@Override
	public void setController(SendPanelController controller) {
		this.controller = controller;
	}

	@Override
	public void initPanel() {
		setName("SendPanel");
		setLayout(new BorderLayout());
		add(getTextPane(), BorderLayout.CENTER);
		
		{
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.CENTER;
			panel.add(getSmileButton(), gbc);
			add(panel, BorderLayout.WEST);
		}
		{
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.CENTER;
			panel.add(getSendButton(), gbc);
			add(panel, BorderLayout.EAST);
		}
	}

	public JTextArea getTextPane() {
		if (textPane == null) {
			textPane = new JTextArea();
			textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			textPane.setLineWrap(true);
			textPane.setWrapStyleWord(true);
			textPane.setEditable(true);
			textPane.setMargin(new Insets(10, 10, 10, 10));

			textPane.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(1, 3, 1, 1)));
			textPane.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					getTextPane().requestFocusInWindow();
				}
			});

			int condition = WHEN_FOCUSED;
			InputMap inputMap = textPane.getInputMap(condition);
			ActionMap actionMap = textPane.getActionMap();
			KeyStroke enterStroke = KeyStroke.getKeyStroke("ENTER");
			
			inputMap.put(enterStroke, enterStroke.toString());
			actionMap.put(enterStroke.toString(), new AbstractAction() {

				/**
				* 
				*/
				private static final long serialVersionUID = 1L;
				

				@Override
				public void actionPerformed(ActionEvent action) {
					getSendButton().doClick();
				}
			});
			KeyStroke shiftEnterStroke = KeyStroke.getKeyStroke("shift ENTER");
			inputMap.put(shiftEnterStroke, shiftEnterStroke.toString());
			actionMap.put(shiftEnterStroke.toString(), new AbstractAction() {
				
				/**
				* 
				*/
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					getTextPane().append(System.lineSeparator());
				}
			});
		}
		return textPane;
	}

	public JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton("Send");
			sendButton.setPreferredSize(new Dimension(100, 30));
			sendButton.setMinimumSize(new Dimension(100, 30));
			sendButton.setMargin(new Insets(10, 10, 10, 10));
			sendButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String text = getTextPane().getText();
					getTextPane().setText("");
					sendMessage(text);
				}
			});
		}
		return sendButton;
	}
	
	private boolean changeVisible() {
		return smileVisible.getAndSet(!smileVisible.get());
	}
	
	public JButton getSmileButton() {
		if ( smileButton == null ) {
			smileButton = new JButton(Emoji.GRINNING_FACE.unicode());
			smileButton.setPreferredSize(new Dimension(50, 30));
			smileButton.setMinimumSize(new Dimension(50, 30));
			smileButton.setMargin(new Insets(10, 10, 10, 10));
			smileButton.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getSmileFrame().pack();
					getSmileFrame().setVisible(changeVisible());
				}
			});
		}
		return smileButton;
	}
	
	
	
	public JFrame getSmileFrame() {
		if ( smileFrame == null ) {
			smileFrame = new JFrame("Smile panel");
			smileFrame.getContentPane().add( getSmilePanel() );
			smileFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		}
		return smileFrame;
	}

	public JPanel getSmilePanel() {
		if ( smilePanel == null ) {
			smilePanel = new JPanel();
			smilePanel.setPreferredSize(new Dimension(300, 300));
			smilePanel.setMinimumSize(new Dimension(300, 300));
			smilePanel.setLayout( new FlowLayout(FlowLayout.LEADING, 5, 5) );
			Emoji[] emojiArray = Emoji.values();
			for (Emoji emoji : emojiArray) {
				smilePanel.add(createLable(emoji.unicode()));
			}
		}
		return smilePanel;
	}
	
	public JLabel createLable(final String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		label.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(()->controller.sendMessage(text));
				SwingUtilities.invokeLater(()->getSmileFrame().setVisible(changeVisible()));
			}
		});
		return label;
	}

	public void sendMessage(String message) {
		SwingUtilities.invokeLater(()->controller.sendMessage(message));
	}
}
