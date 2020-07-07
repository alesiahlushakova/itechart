package by.itechart.command;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

public class UploadCommand implements Command{


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if(!ServletFileUpload.isMultipartContent(request)){
            try {
                throw new ServletException("Content type is not multipart/form-data");
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }

        response.setContentType("image/gif");
        try {
            ServletOutputStream sos = response.getOutputStream();
            List<FileItem> fileItemsList = ((ServletFileUpload)request.getAttribute("uploader")).parseRequest(request);
            for (FileItem fileItem : fileItemsList) {
                System.out.println("FieldName=" + fileItem.getFieldName());
                System.out.println("FileName=" + fileItem.getName());
                System.out.println("ContentType=" + fileItem.getContentType());
                System.out.println("Size in bytes=" + fileItem.getSize());

                File file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileItem.getName());
                System.out.println("Absolute Path at server=" + file.getAbsolutePath());
                fileItem.write(file);


                DataInputStream dis = new DataInputStream(new FileInputStream(file));
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
            }
        } catch (FileUploadException e) {
            //    out.write("Exception in uploading file.");
        } catch (Exception e) {
            //  out.write("Exception in uploading file.");
        }
    }
}
