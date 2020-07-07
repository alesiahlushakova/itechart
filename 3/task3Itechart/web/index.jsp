
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07.07.2020
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="UploadDownloadFileServlet?command=upload" method="post" enctype="multipart/form-data">

    Select File to Upload and Display:<input type="file" name="fileName">
    <br>
       <input type="submit" value="Display">
  </form>
  <form action="UploadDownloadFileServlet?command=download" method="post" enctype="multipart/form-data">

    Select File to Upload and Download:<input type="file" name="fileName">
    <br>
    <input type="submit" value="Download">
  </form>
  </body>
</html>
