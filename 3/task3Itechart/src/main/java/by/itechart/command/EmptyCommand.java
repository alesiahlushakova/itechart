package by.itechart.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EmptyCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

    }
}
