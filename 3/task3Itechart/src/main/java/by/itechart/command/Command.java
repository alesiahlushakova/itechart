package main.java.by.itechart.command;

import main.java.by.itechart.validator.ValidatorException;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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



    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
