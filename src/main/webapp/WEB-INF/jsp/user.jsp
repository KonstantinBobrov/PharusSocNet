<%@ page import="java.util.List" %>
<%@ page import="ru.pharus.socnetwork.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://pharus.ru/taglibs" prefix="ptags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="logUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="showUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="listPosts" type="java.util.List" scope="request"/>
<jsp:useBean id="listFriends" type="java.util.List" scope="request"/>
<jsp:useBean id="listCars" type="java.util.List" scope="request"/>

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
    <div id="leftpanel">
        <!--Левая панель-->
        <div id="menu">
            <ol class="listmenu">
                <li>
                    <a href="/user" class="left_row">
                        <span class="left_label inl_bl">Моя Страница</span>
                        </span>
                    </a>
                </li>

                <li>
                    <a href="/feed" class="left_row">
                        <span class="left_label inl_bl">Новости</span>
                        </span>
                    </a>
                </li>

                <li>
                    <a href="/im" class="left_row">
                        <span class="left_label inl_bl">Сообщения</span>
                        </span>
                    </a>
                </li>

                <li>
                    <a href="/friends" class="left_row">
                        <span class="left_label inl_bl">Друзья</span>
                        </span>
                    </a>
                </li>
            </ol>
        </div>

    </div>


    <div id="rightpanel">
        <div id="rightpanelsmall">
            <div id="avatarcontainter">
                <div id="bavatar" style="background-image:url('img/ava/${showUser.avatar}')"></div>
            </div>
            <div id="rightpanelsmallspace"><!-- blank --></div>
            <div id="addfriend">
                <a href="/user?adduser=">добавить в друзья</a>
            </div>

            <div id="rightpanelsmallspace"><!-- blank --></div>

            <div id="listfriends">
                Подписок (${listFriends.size()})
               <ptags:showfriends/>
            </div>
        </div>

        <div id="rightpanelspace">
        </div>

        <div id="rightpanelbig">
            <div id="userinfo">
                ${showUser.id}<br>
                ${showUser.fullName}<br>
                ${showUser.birthDate}<br>
                ${showUser.registerDate}<br>
            </div>

            <div id="rightpanelbigspace"><!-- blank --></div>
            <div id="usercarsinfo">
                Ваш гараж (${listCars.size()})
            </div>

            <div id="rightpanelbigspace"><!-- blank --></div>

            <div id="postinfo">
                Ваши посты (${listPosts.size()})

            </div>
        </div>
    </div>
</div>

</body>
</html>