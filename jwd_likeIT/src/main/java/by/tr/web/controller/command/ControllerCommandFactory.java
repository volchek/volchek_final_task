package by.tr.web.controller.command;

import java.util.Map;
import java.util.HashMap;

import by.tr.web.controller.command.impl.SignIn;
import by.tr.web.controller.command.impl.FindByLogin;
import by.tr.web.controller.command.impl.Registrate;
import by.tr.web.controller.command.util.CommandType;


public class ControllerCommandFactory {

	
	private static final ControllerCommandFactory instance = new ControllerCommandFactory();

	private final Map<String, ControllerCommand> commands = new HashMap<>();
	{
		commands.put(CommandType.SIGN_IN.name(), new SignIn());
		commands.put(CommandType.REGISTRATE.name(), new Registrate());
		commands.put(CommandType.FIND_BY_LOGIN.name(), new FindByLogin());
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
	
	private ControllerCommandFactory() { }
}
