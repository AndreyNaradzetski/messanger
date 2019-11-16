package com.bsu.fpm.message.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.bsu.fpm.message.controller.impl.ChatPanelController;
import com.bsu.fpm.message.data.Message;
import com.bsu.fpm.message.utils.DateUtils;
import com.bsu.fpm.message.utils.MessageUtils;
import com.bsu.fpm.message.utils.StringUtils;
import com.bsu.fpm.message.view.IMessageView;
import com.bsu.fpm.message.visitors.Visitors;

public class ChatPanel extends JPanel implements IMessageView<ChatPanel, ChatPanelController> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final CopyOnWriteArrayList<Message> messageList = new CopyOnWriteArrayList<>();
	
	private static final Font USER_NAME_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	
	private static final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

	private static final Font TIME_FONT = new Font(Font.SANS_SERIF, Font.ITALIC, 10);

	//https://colorscheme.ru/ and https://www.color-hex.com/color/ced1d9

	private static final Color BOT_COLOR = new Color(181,217,207);
	
	private static final Color PERSON_COLOR = new Color(206,209,217);
	
	private static final int GAP = 20;

	private ChatPanelController controller;

	private JScrollPane parentScroll;
	
	private JPopupMenu popupMenu;
	
	private JMenuItem clearLogMenu;
	
	public ChatPanel() {
		super();
	}
	
	public ChatPanelController getController() {
		return controller;
	}

	@Override
	public void setController(ChatPanelController controller) {
		this.controller = controller;
	}
	
	public JScrollPane getParentScroll() {
		return parentScroll;
	}

	public void setParentScroll(JScrollPane parentScroll) {
		this.parentScroll = parentScroll;
	}

	@Override
	public void initPanel() {
		setName("ChatPanel");
		addMouseListener( new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		        maybeShowPopup(e);
		    }

		    public void mouseReleased(MouseEvent e) {
		        maybeShowPopup(e);
		    }

		    private void maybeShowPopup(MouseEvent e) {
		        if (e.isPopupTrigger()) {
		            getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
		        }
		    }
		});
	}
	
	public void clearMessages() {
		messageList.clear();
		repaint();
	}
	
	public void addMessage(Message message) {
		addMessages(new Message[] {message});
	}
	
	public void addMessages(Message[] messages) {
		messageList.addAll(Arrays.asList( messages ));
		repaint();
		SwingUtilities.invokeLater(()->scrollDown());
	}
	
	private int getMax(int[] arr) {
		return Arrays.stream(arr).max().getAsInt();
	}
	
	private int paintMessage(Graphics2D graphics2d, Message message, int x, int y) {
		
		Dimension dimention = getRealSize();
		
		graphics2d.setFont(USER_NAME_FONT);
		int heightHeaderLine = graphics2d.getFontMetrics().getHeight();
		int widthHeader = getMax(graphics2d.getFontMetrics().getWidths());

		graphics2d.setFont(TEXT_FONT);
		int heightTextLine = graphics2d.getFontMetrics().getHeight();
		int widthText = getMax(graphics2d.getFontMetrics().getWidths());
		
		int maxSingleLineSize = (dimention.width + 1) / 2;
		int headerLineSize = message.getAuthor().getAuthorName().length() * widthHeader + x;
		int textLineSize = message.getMessage().length() * widthText + x;
		
		int singleLineSize = Math.min(maxSingleLineSize, Math.max(headerLineSize, textLineSize));
		
		String messageText = Visitors.getInstance().visit(message.getMessage());
		String[] lines = StringUtils.splitLines(messageText, singleLineSize, widthText);
		
		int totalHeight = heightHeaderLine + 5 + heightTextLine * (lines.length + 2);
		
		// adjust x position based on message type. 
		if ( MessageUtils.isPersonalMessage(message) ) {
			x = maxSingleLineSize  + ( maxSingleLineSize - singleLineSize ) - widthHeader;
		}

		// draw round border
		graphics2d.setColor(MessageUtils.isPersonalMessage(message) ? PERSON_COLOR : BOT_COLOR);
		graphics2d.fill(new RoundRectangle2D.Double(x, y, singleLineSize, totalHeight, 25, 15));

		graphics2d.setColor(Color.BLACK);
		// draw author name
		graphics2d.setFont(USER_NAME_FONT);
		y = y + widthHeader + 5 ;
		graphics2d.drawString(message.getAuthor().getAuthorName(), x + widthHeader, y);

		// draw delimiter
		y = y + widthText ;
		graphics2d.drawString("", x + widthText, y);

		
		// draw text
		graphics2d.setFont(TEXT_FONT);
		for (String line : lines) {
			y += widthText;
			graphics2d.drawString(line, x + widthText, y);
		}
		
		// draw message time
		graphics2d.setFont(TIME_FONT);
		String timeText = DateUtils.formatTime(message.getDate());
		int timeTextLen = graphics2d.getFontMetrics().stringWidth(timeText);
		int widthTime = getMax(graphics2d.getFontMetrics().getWidths());

		y += widthText;
		graphics2d.drawString(timeText, x + singleLineSize - timeTextLen - widthTime, y);

		return totalHeight ;
	}
	
	private int paintMessages(Graphics graphics) {
		int x = GAP; int y = GAP;
		if ( graphics instanceof Graphics2D) {
			Graphics2D graphics2d = (Graphics2D)graphics;
			for (Message message : messageList) {
				int height = paintMessage(graphics2d, message, x, y);
				y += height + GAP/2;
			}
		}
		return y;
	}
	
	private Dimension getRealSize() {
		return getParent().getSize();
	}
	
	private void scrollDown() {
		getParentScroll().validate();
		JScrollBar vertical = getParentScroll().getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		int height = paintMessages(graphics);
		Dimension dimention = getRealSize();
		Dimension newSize = new Dimension(dimention.width, Math.max(dimention.height, height));
		setPreferredSize(newSize);
		revalidate();
	}

	public JPopupMenu getPopupMenu() {
		if ( popupMenu == null ) {
			popupMenu = new JPopupMenu("Select action");
			popupMenu.add( getClearLogMenu());
		}
		return popupMenu;
	}

	public JMenuItem getClearLogMenu() {
		if ( clearLogMenu == null ) {
			clearLogMenu = new JMenuItem("Clear history");
			clearLogMenu.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					getController().clearHistory();
				}
			});
		}
		return clearLogMenu;
	}
	
	
}
