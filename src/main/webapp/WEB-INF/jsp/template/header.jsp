<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}" />
<fmt:setBundle basename="localization"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="welcome"/></title>
    <link rel="stylesheet" media="screen" href="${pageContext.request.contextPath}css/styles.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}favicon.ico"/>
</head>
<body bgcolor=#edeef0 align="center">

<div id="header">
    <div id="gohome"><a href="/"><div id="smallogo" style="background-image:url('img/logo.png');"></div></a></div>
    <div id="searchinhead"><form action="/friends" method="get"><input type="text" name="search" autocomplete="off" placeholder="Поиск"></form><div id="smallava"></div></div>
    <div id="logout" ><a href="/logout">&nbsp&nbsp&nbsp&nbsp&nbsp <div id="smallava" style="background-image:url('img/ava/${logUser.avatar}');"></div> <fmt:message key="logout"/> </a></div>
</div>

<div id="none"></div>

<div id="contentconteiner">
