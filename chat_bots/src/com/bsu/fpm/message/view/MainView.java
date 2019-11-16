package com.bsu.fpm.message.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.bsu.fpm.message.controller.MainController;
import com.bsu.fpm.message.view.workers.ReadMessagesWorker;

public class MainView extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Dimension DEFAULT_DIM = new Dimension(1024, 780);

	private static final String TITLE = "Messenger";

	private JPanel botsPanel;

	private JPanel sendPanel;
	
	private JSplitPane botChatSplit;
	
	private JScrollPane chatScroll;
	
	private JPanel chatSplit;
	
	private final MainController controller;

	public MainView(MainController controller) {
		super();
		this.controller = controller;
	}
	
	public MainController getController() {
		return controller;
	}

	public void initFrame() {
		setTitle(TITLE);
		setName("MainView");
		setSize(DEFAULT_DIM);
		getContentPane().add(getBotChatSplit());
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				getController().getSendPanel().getTextPane().requestFocusInWindow();
			}
		});
		new ReadMessagesWorker(this).execute();
	}
	
	public JSplitPane getBotChatSplit() {
		if ( botChatSplit == null ) {
			botChatSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getBotsPanel(), getChatSplit());
			botChatSplit.setOneTouchExpandable(true);
		}
		return botChatSplit;
	}
	
	public JPanel getChatSplit() {
		if ( chatSplit == null ) {
			chatSplit = new JPanel();
			chatSplit.setLayout(new BorderLayout());
			
			chatSplit.add(getChatScroll(), BorderLayout.CENTER);
			chatSplit.add(getSendPanel(), BorderLayout.SOUTH);
		}
		return chatSplit;
	}
	
	private JScrollPane wrapIntoScrollPane(JComponent component, int vsbPolicy, int hsbPolicy ) {
		JScrollPane scroll = new JScrollPane(component, vsbPolicy, hsbPolicy);
		return scroll;
	}
	
	public JScrollPane getChatScroll() {
		if ( chatScroll == null ) {
			chatScroll = wrapIntoScrollPane(getController().getChatPanel(), 
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			chatScroll.getVerticalScrollBar().setUnitIncrement(50);
			getController().getChatPanel().setParentScroll(chatScroll);
		}
		return chatScroll;
	}

	public JPanel getBotsPanel() {
		if (botsPanel == null) {
			botsPanel = new JPanel();
			botsPanel.setLayout(new BorderLayout());
			botsPanel.setMinimumSize(new Dimension(200, 100));
			
			JComponent component = getController().getBotPanel();
			botsPanel.add(component, BorderLayout.NORTH);
		}
		return botsPanel;
	}

	public JPanel getSendPanel() {
		if (sendPanel == null) {
			sendPanel = new JPanel();
			sendPanel.setLayout(new BorderLayout());
			JComponent component = getController().getSendPanel();
			sendPanel.add(component, BorderLayout.CENTER);
		}
		return sendPanel;
	}

}
