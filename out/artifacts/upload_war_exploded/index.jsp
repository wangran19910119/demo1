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
    <title></title>
  </head>
  <body>
  <!-- enctype 默认是 application/x-www-form-urlencoded -->
  <form action="FileUploadServlet" enctype="multipart/form-data" method="post" target="hidden_frame">
    上传文件：<input type="file" name="file1"><br/>
  <%--  上传文件： <input type="file" name="file2"><br/>--%>
    <input type="submit" value="提交"/>
    <iframe name='hidden_frame'  style='display:none'></iframe>
  </form>
  </body>
  <script type="text/javascript">
  function callback(msg)
  {
     alert(msg);
  }
  </script>
</html>
