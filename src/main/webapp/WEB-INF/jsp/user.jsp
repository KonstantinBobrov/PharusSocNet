<%@ page import="java.util.List" %>
<%@ page import="ru.pharus.socnetwork.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>User page</title>
    <link rel="stylesheet" media="screen" href="../../css/styles.css">
    <link rel="shortcut icon" href="../../favicon.ico"/>
</head>
<body bgcolor=#edeef0 align=center>

<div id="header">
</div>

<div id="none"></div>

<div>
    Страница пользователя
</div>

<div>
    <% List<Post> postList = (List<Post>) request.getAttribute("Posts"); %>
        <%
            for(Post post:postList){
                %><table><tr><td><%=post.getTitle()%><tr><td><%=post.getText()%></table><%
            }
        %>
</div>


</body>
</html>