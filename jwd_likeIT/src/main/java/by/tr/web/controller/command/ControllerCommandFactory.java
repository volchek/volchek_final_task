package by.tr.web.controller.command;

import java.util.Map;
import java.util.HashMap;

import by.tr.web.controller.command.impl.user.BanUser;
import by.tr.web.controller.command.impl.user.EditUser;
import by.tr.web.controller.command.impl.user.FindUserByLogin;
import by.tr.web.controller.command.impl.user.Logout;
import by.tr.web.controller.command.impl.user.Register;
import by.tr.web.controller.command.impl.user.SignIn;
import by.tr.web.controller.command.impl.language.ChangeLanguage;
import by.tr.web.controller.command.impl.text.AddQuestion;
import by.tr.web.controller.command.impl.text.DeleteAnswer;
import by.tr.web.controller.command.impl.text.EditAnswer;
import by.tr.web.controller.command.impl.text.EditQuestion;
import by.tr.web.controller.command.impl.text.EvaluateAnswer;
import by.tr.web.controller.command.impl.text.EvaluateQuestion;
import by.tr.web.controller.command.impl.text.FindQuestionByLanguage;
import by.tr.web.controller.command.impl.text.FindQuestionByTag;
import by.tr.web.controller.command.impl.text.FinishEditAnswer;
import by.tr.web.controller.command.impl.text.FinishEditQuestion;
import by.tr.web.controller.command.impl.text.ShowLastQuestions;
import by.tr.web.controller.command.impl.text.FindQuestionById;
import by.tr.web.controller.command.impl.text.ShowUserAnswer;
import by.tr.web.controller.command.impl.text.ShowUserQuestion;
import by.tr.web.controller.command.impl.text.AddAnswer;
import by.tr.web.controller.command.util.CommandType;

public class ControllerCommandFactory {

	private static final ControllerCommandFactory instance = new ControllerCommandFactory();

	private final Map<String, ControllerCommand> commands = new HashMap<String, ControllerCommand>(){{
		put(CommandType.CHANGE_LANG.name(), new ChangeLanguage());
		put(CommandType.FIND_USER_BY_LOGIN.name(), new FindUserByLogin());
		put(CommandType.REGISTER.name(), new Register());
		put(CommandType.SIGN_IN.name(), new SignIn());
		put(CommandType.LOGOUT.name(), new Logout());
		put(CommandType.EDIT_USER.name(), new EditUser());
		put(CommandType.BAN_USER.name(), new BanUser());
		put(CommandType.ADD_QUESTION.name(), new AddQuestion());
		put(CommandType.GET_ANSWER.name(), new AddAnswer());
		put(CommandType.EDIT_QUESTION.name(), new EditQuestion());
		put(CommandType.FINISH_TO_EDIT_QUESTION.name(), new FinishEditQuestion());
		put(CommandType.EDIT_ANSWER.name(), new EditAnswer());
		put(CommandType.FINISH_TO_EDIT_ANSWER.name(), new FinishEditAnswer());
		put(CommandType.DELETE_ANSWER.name(), new DeleteAnswer());
		put(CommandType.EVALUATE_QUESTION.name(), new EvaluateQuestion());
		put(CommandType.EVALUATE_ANSWER.name(), new EvaluateAnswer());
		put(CommandType.SHOW_USER_QUESTIONS.name(), new ShowUserQuestion());
		put(CommandType.SHOW_USER_ANSWERS.name(), new ShowUserAnswer());
		put(CommandType.SHOW_LAST_QUESTIONS.name(), new ShowLastQuestions());
		put(CommandType.FIND_QUESTION_BY_LANGUAGE.name(), new FindQuestionByLanguage());
		put(CommandType.FIND_QUESTION_BY_TAG.name(), new FindQuestionByTag());
		put(CommandType.FIND_QUESTION_BY_ID.name(), new FindQuestionById());
	}};
	
	public static ControllerCommandFactory getInstance() {
		return instance;
	}

	public Map<String, ControllerCommand> getCommands() {
		return commands;
	}

	public ControllerCommand getCommand(String commandName) {
		return commands.get(commandName);
	}

	private ControllerCommandFactory() {
	}
}
