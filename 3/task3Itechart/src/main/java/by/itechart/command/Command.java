package by.itechart.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Command {

    /**
     * Parameters.
     */
    String COMMAND_PARAMETER = "command";
    String UPLOAD_PARAMETER = "upload";
    String DISPLAY_PARAMETER = "display";
    /**
     * Attributes.
     */

    String MESSAGE_ATTRIBUTE = "message";



    void execute(HttpServletRequest request, HttpServletResponse response);
}
