package com.bsu.fpm.message.emoji;

public enum Emoji {
	
	// https://unicode.org/emoji/charts/full-emoji-list.html

	GRINNING_FACE {
		@Override
		public String text() {
			return ":)";
		}
		
		@Override
		public int hex() {
			return 0x1F604;
		}
	},
	
	FROWNING_FACE{
		@Override
		public String text() {
			return ":(";
		}
		
		@Override
		public int hex() {
			return 0x1F641;
		}
	};
	
	public String unicode() {
		int hex = hex();
	    char unicode[] = {Character.highSurrogate(hex), Character.lowSurrogate(hex)};
	    return String.valueOf(unicode);
	}
	
	public abstract String text();
	
	protected abstract int hex();
	
}
