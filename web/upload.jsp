<%--
  Created by IntelliJ IDEA.
  User: dzd-technology01
  Date: 2017/9/8
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>iframe+servlet 文件上传</title>
    <script type="text/javascript">
      function callback(msg)
      {
        alert(msg);
        document.location.href="upload.jsp";
      }
    </script>
  </head>
  <body>

  <form action="FileUploadServlet" method="post" enctype="multipart/form-data" name="form1" target="if1">
    <table>
      <tr>
        <td><label>file:</label></td>
        <td><input type="file" name="file1"></td>
      </tr>
      <tr>
        <td><input type="submit"  value="提交"></td>
      </tr>
    </table>
  </form>

  <iframe name="if1" style="display: none;"></iframe>
  </body>
</html>