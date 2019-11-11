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
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function () {
            var name;
            var password;
            var passwordSure;
            $(":text:eq(0)").blur(function () {
                if ($(this).val().trim() == "") {
                    $(this).next().css("color", "red").html("x");
                    name = false;
                } else {
                    $(this).next().css("color", "green").html("√");
                    name = true;
                }

            });
            $(":password:eq(0)").blur(function () {
                if ($(this).val().match(/^\w{6,12}$/)) {
                    $(this).next().css("color", "green").html("√");
                    password = true;
                } else {
                    $(this).next().css("color", "red").html("x");
                    password = false;
                }
            });
            $(":password:eq(1)").blur(function () {
                if ($(this).val().trim() == "" || $(this).val().trim() != $(":password:eq(0)").val().trim()) {
                    $(this).next().css("color", "red").html("x");
                    passwordSure = false;
                } else {
                    $(this).next().css("color", "green").html("√");
                    passwordSure = true;
                }
            });
            $(":submit").click(function () {
                if (name == false || password == false || passwordSure == false || $(":file").val() == "") {
                    alert("请填写完整");
                    return false;
                }
            });
        });

    </script>
</head>
<body>
<a href="download?fileName=Readme.pdf">下载</a><br/>

<form enctype="multipart/form-data" action="register" method="post">
    name:<input type="text" name="name"/><span></span><br/>
    photo:<input type="file" name="file"/><br/>
    password:<input type="password" name="password"/><span></span><br/>
    password2:<input type="password" name="password2"/><span></span><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
