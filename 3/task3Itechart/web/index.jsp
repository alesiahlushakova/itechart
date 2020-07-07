
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07.07.2020
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage = "error.jsp" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  </head>
  <body>

  <script type="text/javascript">
  $(document).ready(function() {

    $("#parm").change(function() {
      var selected = $(this).children(":selected").text();
      switch (selected) {

        case "Display photo":
          $("#myform").attr('action', 'UploadDownloadFileServlet?command=upload');

          break;
        case "Download photo":
          $("#myform").attr('action', 'UploadDownloadFileServlet?command=download');

          break;

        default:
          $("#myform").attr('action', 'UploadDownloadFileServlet?command=upload');
      }
    });
  });
  </script>
  <select id="parm" >
  <option>--- Choose What to do with uploaded photo ---</option>
  <option>Display photo</option>
  <option>Download photo</option>
  </select>


  <form id="myform"  name="myform"  method="post" enctype="multipart/form-data">

    Select File to Upload:<input type="file" name="fileName">
    <br>
       <input type="submit"  id="submit" value="Go!" >
  </form>

  </body>
</html>
