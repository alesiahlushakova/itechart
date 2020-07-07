package main.java.by.itechart.command;

import main.java.by.itechart.validator.Validator;
import main.java.by.itechart.validator.ValidatorException;
import main.java.by.itechart.validator.ValidatorFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class UploadCommand implements Command{

    private static final String EXTENSION_VALIDATOR="EXTENSION";
    private static final String SIZE_VALIDATOR="SIZE";
    private static final String UNIQUENESS_VALIDATOR="UNIQUENESS";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator extensionValidator = validatorFactory.defineValidator(EXTENSION_VALIDATOR);
        Validator sizeValidator = validatorFactory.defineValidator(SIZE_VALIDATOR);

        if(!ServletFileUpload.isMultipartContent(request)){
                throw new ServletException("Content type is not multipart/form-data");

        }

        response.setContentType("image/gif");

        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<FileItem> fileItemsList = null;
        try {
            fileItemsList = ((ServletFileUpload)request.getAttribute("uploader")).parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        for (FileItem fileItem : fileItemsList) {
                System.out.println("FieldName=" + fileItem.getFieldName());
                System.out.println("FileName=" + fileItem.getName());
                System.out.println("ContentType=" + fileItem.getContentType());
                System.out.println("Size in bytes=" + fileItem.getSize());

                File file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileItem.getName());
                boolean isValidExt = extensionValidator.validate(file);
                boolean isValidSize = sizeValidator.validate(file);

                if ( isValidSize && isValidExt) {
                    System.out.println("Absolute Path at server=" + file.getAbsolutePath());
                    try {
                        fileItem.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    DataInputStream dis = null;
                    try {
                        dis = new DataInputStream(new FileInputStream(file));
                        byte[] barray = new byte[(int) file.length()];

                        try {
                            dis.readFully(barray);           // now the array contains the image
                        } catch (Exception e) {
                            barray = null;
                        } finally {
                            dis.close();
                        }


                        assert barray != null;
                        sos.write(barray);                 // send the byte array to client
                        sos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else if (!isValidExt) {
                    throw new ServletException("Extension is not supported");
                } else if (!isValidSize) {
                    throw new ServletException("The file size is too big");
                }
            }

    }
}
