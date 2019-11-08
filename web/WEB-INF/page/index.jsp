<%--
  Created by IntelliJ IDEA.
  User: wuhf
  Date: 2018/12/24
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>index</title>
    <script type="text/javascript" src="../../js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function () {
            $.post("menu/all", function (data) {
                var result ="";
                for(var i=0;i<data.length;i++){
                    result+="<dl>";
                    result+="<dt style='cursor:pointer'>"+data[i].name+"</dt>";
                    for(var j=0;j<data[i].children.length;j++){
                        result+="<dd>"+data[i].children[j].name+"</dd>";
                    }
                    result+="</dl>";
                }
                $("body").html(result);
            });

            $("dt").live("click", function () {
                $(this).siblings().slideToggle(100)
            });
        })
    </script>
</head>
<body>

</body>
</html>
