package by.tr.web.controller.command.util;

public enum CommandType {

	CHANGE_LANG, 
	ADD_LANGUAGE,
	ADD_TAG,
	UPDATE_LANGUAGE_LIST,
	UPDATE_TAG_LIST,
	FIND_USER_BY_LOGIN, 
	REGISTER, 
	SIGN_IN, 
	LOGOUT, 
	EDIT_USER, 
	BAN_USER,
	ADD_QUESTION,
	GET_ANSWER,
	EDIT_QUESTION,
	FINISH_TO_EDIT_QUESTION,
	DELETE_QUESTION,
	EDIT_ANSWER,
	FINISH_TO_EDIT_ANSWER,
	DELETE_ANSWER,
	EVALUATE_ANSWER,
	EVALUATE_QUESTION,
	SHOW_USER_QUESTIONS,
	SHOW_USER_ANSWERS,
	SHOW_LAST_QUESTIONS,
	FIND_QUESTION_BY_ID,
	FIND_QUESTION_BY_TAG,
	FIND_QUESTION_BY_LANGUAGE;
}
