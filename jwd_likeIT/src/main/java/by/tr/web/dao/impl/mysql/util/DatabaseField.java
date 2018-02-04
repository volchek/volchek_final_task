package by.tr.web.dao.impl.mysql.util;

public final class DatabaseField {

	// table "users"
	public static final String USER_ID = "userId";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String REGISTRATION_DATE = "registrationDate";
	public static final String BIRTHDAY_DATE = "birthdayDate";
	public static final String ACCESS = "accessLevel";
	public static final String STATUS = "status";
	public static final String AVATAR = "avatar";
	public static final String EMAIL = "email";
	public static final String RESERVE_EMAIL = "reserveEmail";
	public static final String IS_BANNED = "isBanned";

	// tables "languages", "users2languages" and "questions2languages"
	public static final String LANGUAGE_ID = "languageId";
	public static final String LANGUAGE_NAME = "language";
	public static final String LANGUAGE_LEVEL = "level";

	// tables "keywords" and "questions2keywords"
	public static final String TAG_ID = "keywordId";
	public static final String TAG_NAME = "keyword";

	// tables "questions" and "questionMarks"
	public static final String QUESTION_ID = "questionId";
	public static final String QUESTION_TITLE = "title";
	public static final String QUESTION_TEXT = "text";
	public static final String QUESTION_AUTHOR = "userId";
	public static final String QUESTION_DATETIME = "creationDatetime";
	public static final String QUESTION_MARK = "mark";
	public static final String QUESTION_MARK_DATETIME = "datetime";

	// table "answers" and "answerMarks"
	public static final String ANSWER_ID = "answerId";
	public static final String ANSWER_TEXT = "answerText";
	public static final String ANSWER_AUTHOR = "userId";
	public static final String ANSWER_DATETIME = "creationDatetime";
	public static final String ANSWER_MARK = "mark";
	public static final String ANSWER_MARK_DATETIME = "datetime";

	private DatabaseField() {

	}

}
