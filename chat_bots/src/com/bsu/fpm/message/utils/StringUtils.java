package com.bsu.fpm.message.utils;

import java.util.ArrayList;

public class StringUtils {

	private StringUtils() {
		throw new UnsupportedOperationException();
	}

	public static final boolean isEmpty(String str) {
		return ObjectUtils.isNull(str) || str.trim().length() == 0;
	}
	
	public static final String append(String first, String second) {
		if (ObjectUtils.isNull( first)) {
			return null;
		}
		if (isEmpty( second )) {
			return first;
		}
		return new StringBuffer(first).append(second).toString();
	}
	
	public static final String appendIfMissed(String str, String toAppend) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty( toAppend)) {
			return str;
		}
		if (!str.endsWith(toAppend)) {
			str += toAppend;
		}
		return str;
	}
	
	public static int calcAvailable(String str, int maxWidth, int widthText) {
		int lineWidth = str.length() * widthText;
		return maxWidth - lineWidth;
	} 
	
	public static String[] splitLines(String text, int maxWidth, int widthText) {
		String[] words = text.split("((?<= )|(?= ))");
		ArrayList<String> lines = new ArrayList<>();
		String line = "";
		for (String word : words){
			boolean flag = true;
			do {
				int available = calcAvailable(line + word, maxWidth, widthText);
				if (available < 0) {
					if (StringUtils.isEmpty(line)) {
						int endIndex = Math.max(1, word.length() + available / widthText );
						String subString = word.substring(0, endIndex);
						lines.add(subString);
						word = word.substring(endIndex);
					} else {
						lines.add(line);
						line = "";
						flag = true;
					}
				} else {
					line += word; 
					flag = false;
				}
				
			} while (flag);

		}
		if (!StringUtils.isEmpty( line )) {
			lines.add( line );
		}
		return lines.toArray( new String[0]);
	}
	
	public static void main(String[] args) {
		try {
			
		    String bear = "\ud83d\udc3b";

		    int bearCodepoint = bear.codePointAt(bear.offsetByCodePoints(0, 0));
		    int mysteryAnimalCodepoint = bearCodepoint + 1;

		    char mysteryAnimal[] = {Character.highSurrogate(mysteryAnimalCodepoint),
		                            Character.lowSurrogate(mysteryAnimalCodepoint)};
		    System.out.println("The Coderland Zoo's latest attraction: "
		                       + String.valueOf(mysteryAnimal));

			
			String[] words = StringUtils.splitLines("hope this can heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeelp", 25 , 5);
			for (String string : words) {
				System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
