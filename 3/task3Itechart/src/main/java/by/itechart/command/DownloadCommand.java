package by.itechart.command;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class DownloadCommand implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");


        try {

            List<FileItem> fileItemsList =   ((ServletFileUpload)request.getAttribute("uploader")).parseRequest(request);
            for (FileItem fileItem : fileItemsList) {
                System.out.println("FieldName=" + fileItem.getFieldName());
                System.out.println("FileName=" + fileItem.getName());
                System.out.println("ContentType=" + fileItem.getContentType());
                System.out.println("Size in bytes=" + fileItem.getSize());

                File file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileItem.getName());
                fileItem.write(file);

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

            }
        }  catch (Exception e) {
            System.out.println("Exception in uploading file.");
        }







    }
}
