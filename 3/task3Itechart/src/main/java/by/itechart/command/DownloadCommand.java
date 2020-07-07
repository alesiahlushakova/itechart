package main.java.by.itechart.command;

import main.java.by.itechart.validator.Validator;
import main.java.by.itechart.validator.ValidatorException;
import main.java.by.itechart.validator.ValidatorFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class DownloadCommand implements Command {

    private static final String EXTENSION_VALIDATOR = "EXTENSION";
    private static final String SIZE_VALIDATOR = "SIZE";
    private static final String UNIQUENESS_VALIDATOR = "UNIQUENESS";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.setContentType("text/html");
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator extensionValidator = validatorFactory.defineValidator(EXTENSION_VALIDATOR);
        Validator sizeValidator = validatorFactory.defineValidator(SIZE_VALIDATOR);
        Validator uniquenessValidator = validatorFactory.defineValidator(UNIQUENESS_VALIDATOR);


        List<FileItem> fileItemsList = null;
        try {
            fileItemsList = ((ServletFileUpload) request.getAttribute("uploader")).parseRequest(request);
            for (FileItem fileItem : fileItemsList) {

                System.out.println("FieldName=" + fileItem.getFieldName());
                System.out.println("FileName=" + fileItem.getName());
                System.out.println("ContentType=" + fileItem.getContentType());
                System.out.println("Size in bytes=" + fileItem.getSize());

                File file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileItem.getName());
                boolean isValidExt = extensionValidator.validate(file);
                boolean isValidSize = sizeValidator.validate(file);
                boolean isValid = uniquenessValidator.validate(file);
                if (isValid && isValidSize && isValidExt) {
                    try {
                        fileItem.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String fileName = fileItem.getName();
                    System.out.println("File location on server::" + file.getAbsolutePath());
                    ServletContext ctx = request.getServletContext();
                    InputStream fis = null;
                    try {
                        fis = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String mimeType = ctx.getMimeType(file.getAbsolutePath());
                    response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
                    response.setContentLength((int) file.length());
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                    ServletOutputStream os = null;
                    try {
                        os = response.getOutputStream();
                        byte[] bufferData = new byte[1024];
                        int read = 0;
                        while (true) {
                            assert fis != null;
                            if ((read = fis.read(bufferData)) == -1) break;
                            os.write(bufferData, 0, read);
                        }
                        os.flush();
                        os.close();
                        fis.close();
                        System.out.println("File downloaded at client successfully");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (!isValidExt) {
                    throw new ServletException("Extension is not supported");
                } else if (!isValid) {
                    throw new ServletException("File is not unique");
                } else if (!isValidSize) {
                    throw new ServletException("The file size is too big");
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }


    }


}

