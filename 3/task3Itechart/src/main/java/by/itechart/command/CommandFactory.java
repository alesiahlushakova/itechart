package main.java.by.itechart.command;


import javax.servlet.http.HttpServletRequest;

import static main.java.by.itechart.command.Command.COMMAND_PARAMETER;
import static main.java.by.itechart.command.Command.MESSAGE_ATTRIBUTE;


public class CommandFactory {



    public Command defineCommand(HttpServletRequest request) {
        Command currentCommand = new EmptyCommand();

        String action = request.getParameter(COMMAND_PARAMETER);
        if (action == null || action.isEmpty()) {

            return currentCommand;
        }
        try {
            String commandTypeValue = action.toUpperCase();
            CommandType currentType = CommandType.valueOf(commandTypeValue);
            currentCommand = currentType.getCurrentCommand();
        } catch (IllegalArgumentException exception) {

            String message = String.format("%s %s", action, "Command error");
            request.setAttribute(MESSAGE_ATTRIBUTE, message);
        }
        return currentCommand;
    }

}
