<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>User page $title</title>
    <link rel="stylesheet" media="screen" href="${pageContext.request.contextPath}css/styles.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}favicon.ico"/>
</head>
<body bgcolor=#edeef0 align="center">

<div id="header">
    <div id="gohome"><a href="/"><div id="smallogo" style="background-image:url('img/logo.png');"></div></a></div>
    <div id="searchinhead"><input type="text" autocomplete="off" placeholder="Поиск"><div id="smallava"></div></div>
    <div id="logout" ><a href="/logout">Петр &nbsp <div id="smallava" style="background-image:url('img/ava/${logUser.avatar}');"></div> Logout </a></div>
</div>

<div id="none"></div>

<div id="contentconteiner">
