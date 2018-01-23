package by.tr.web.controller.command;

import java.util.Map;
import java.util.HashMap;

import by.tr.web.controller.command.impl.user.EditUser;
import by.tr.web.controller.command.impl.user.FindUserByLogin;
import by.tr.web.controller.command.impl.user.Logout;
import by.tr.web.controller.command.impl.user.Register;
import by.tr.web.controller.command.impl.user.SignIn;
import by.tr.web.controller.command.impl.language.ChangeLanguage;
import by.tr.web.controller.command.impl.text.AddQuestion;
import by.tr.web.controller.command.impl.text.EvaluateAnswer;
import by.tr.web.controller.command.impl.text.EvaluateQuestion;
import by.tr.web.controller.command.impl.text.FindQuestionByLanguage;
import by.tr.web.controller.command.impl.text.FindQuestionByTag;
import by.tr.web.controller.command.impl.text.ShowLastQuestions;
import by.tr.web.controller.command.impl.text.FindQuestionById;
import by.tr.web.controller.command.impl.text.ShowUserAnswer;
import by.tr.web.controller.command.impl.text.ShowUserQuestion;
import by.tr.web.controller.command.impl.text.AddAnswer;
import by.tr.web.controller.command.util.CommandType;

public class ControllerCommandFactory {

	private static final ControllerCommandFactory instance = new ControllerCommandFactory();

	private final Map<String, ControllerCommand> commands = new HashMap<>();
	{
		commands.put(CommandType.CHANGE_LANG.name(), new ChangeLanguage());
		commands.put(CommandType.FIND_USER_BY_LOGIN.name(), new FindUserByLogin());
		commands.put(CommandType.REGISTER.name(), new Register());
		commands.put(CommandType.SIGN_IN.name(), new SignIn());
		commands.put(CommandType.LOGOUT.name(), new Logout());
		commands.put(CommandType.EDIT_USER.name(), new EditUser());
		commands.put(CommandType.ADD_QUESTION.name(), new AddQuestion());
		commands.put(CommandType.GET_ANSWER.name(), new AddAnswer());
		commands.put(CommandType.EVALUATE_QUESTION.name(), new EvaluateQuestion());
		commands.put(CommandType.EVALUATE_ANSWER.name(), new EvaluateAnswer());
		commands.put(CommandType.SHOW_USER_QUESTIONS.name(), new ShowUserQuestion());
		commands.put(CommandType.SHOW_USER_ANSWERS.name(), new ShowUserAnswer());
		commands.put(CommandType.SHOW_LAST_QUESTIONS.name(), new ShowLastQuestions());
		commands.put(CommandType.FIND_QUESTION_BY_LANGUAGE.name(), new FindQuestionByLanguage());
		commands.put(CommandType.FIND_QUESTION_BY_TAG.name(), new FindQuestionByTag());
		commands.put(CommandType.FIND_QUESTION_BY_ID.name(), new FindQuestionById());
	}
	
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
