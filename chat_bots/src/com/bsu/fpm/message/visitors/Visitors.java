package com.bsu.fpm.message.visitors;

import com.bsu.fpm.message.visitors.impl.EmojiVisitor;

public class Visitors {

	private static Visitors instance = null;
	
	public static Visitors getInstance() {
		if ( instance == null ) {
			synchronized (Visitors.class) {
				if ( instance == null) {
					instance = new Visitors();
				}
			}
		}
		return instance;
	};
	
	private final IMessageVisitor[] visitors = new IMessageVisitor[] { new EmojiVisitor()};
	
	private Visitors() {
		super();
	}
	
	public String visit(String message) {
		for (IMessageVisitor visitor : visitors) {
			message = visitor.visit(message);
		}
		return message;
	}
}
