package by.tr.web.dao.impl.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class TextCreator {

	private static final String EDIT_INFO = "<p class='date'>[EDIT ?]</p>";
	private static final String PLACEHOLDER = "?";
	private static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	/**
	 * Creates the updated answer text to insert in into the database.
	 *
	 * @param oldText
	 *            the old text
	 * @param newText
	 *            the new text
	 * @return string - the full answer text 
	 */
	public static String createUpdatedText(String oldText, String newText) {

		StringBuilder fullText = new StringBuilder(oldText);

		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		Date date = Calendar.getInstance().getTime();
		String formatDate = dateFormat.format(date);
		fullText.append(EDIT_INFO.replace(PLACEHOLDER, formatDate));

		fullText.append(newText);

		return fullText.toString();

	}

	private TextCreator() {

	}

}
