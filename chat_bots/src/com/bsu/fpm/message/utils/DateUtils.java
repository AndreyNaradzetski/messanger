package com.bsu.fpm.message.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-YYYY");

	private DateUtils() {
		throw new UnsupportedOperationException();
	}
	
	public static final String formatDate(Date date) {
		return dateFormatter.format(date);
	}

	public static final String formatTime(Date date) {
		return timeFormatter.format(date);
	}
}
