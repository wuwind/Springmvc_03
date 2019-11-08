<%--
  Created by IntelliJ IDEA.
  User: wuhf
  Date: 2019/11/8
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="download?fileName=Readme.pdf">下载</a><br/>

<form enctype="multipart/form-data" action="upload" method="post">
    name:<input type="text" name="name"/><br/>
    file:<input type="file" name="file"/><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
