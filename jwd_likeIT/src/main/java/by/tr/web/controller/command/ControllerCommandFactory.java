package by.tr.web.controller.command;

import java.util.Map;
import java.util.HashMap;

import by.tr.web.controller.command.impl.SignIn;
import by.tr.web.controller.command.impl.AddQuestion;
import by.tr.web.controller.command.impl.ChangeLanguage;
import by.tr.web.controller.command.impl.EditUser;
import by.tr.web.controller.command.impl.FindByLogin;
import by.tr.web.controller.command.impl.GetAnswer;
import by.tr.web.controller.command.impl.Logout;
import by.tr.web.controller.command.impl.Register;
import by.tr.web.controller.command.util.CommandType;

public class ControllerCommandFactory {

	private static final ControllerCommandFactory instance = new ControllerCommandFactory();

	private final Map<String, ControllerCommand> commands = new HashMap<>();
	{
		commands.put(CommandType.CHANGE_LANG.name(), new ChangeLanguage());
		commands.put(CommandType.FIND_BY_LOGIN.name(), new FindByLogin());
		commands.put(CommandType.REGISTER.name(), new Register());
		commands.put(CommandType.SIGN_IN.name(), new SignIn());
		commands.put(CommandType.LOGOUT.name(), new Logout());
		commands.put(CommandType.EDIT_USER.name(), new EditUser());
		commands.put(CommandType.ADD_QUESTION.name(), new AddQuestion());
		commands.put(CommandType.GET_ANSWER.name(), new GetAnswer());
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
