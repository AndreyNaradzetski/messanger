package com.bsu.fpm.message.visitors.impl;

import java.util.Arrays;

import com.bsu.fpm.message.emoji.Emoji;
import com.bsu.fpm.message.visitors.IMessageVisitor;

public class EmojiVisitor implements IMessageVisitor {

	public EmojiVisitor() {
		super();
	}

	@Override
	public String visit(final String message) {
		Emoji[] emojiArray = Arrays.stream(Emoji.values())
			.filter((e)-> message.contains(e.text()))
			.toArray((size)-> new Emoji[size]);
		String text = new String(message);
		for (Emoji emoji : emojiArray) {
			text = text.replace(emoji.text(), emoji.unicode());
		}
		return text;
	}

}
