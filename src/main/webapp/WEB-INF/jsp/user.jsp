<%@ page import="java.util.List" %>
<%@ page import="ru.pharus.socnetwork.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://pharus.ru/taglibs" prefix="ptags" %>

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
    <div id="logout" ><a href="/logout">Петр &nbsp <div id="smallava" style="background-image:url('img/ava/1.jpg');"></div> Logout </a></div>
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
                <div id="bavatar" style="background-image:url('img/ava/1.jpg')"></div>
            </div>
            <div id="rightpanelsmallspace"><!-- blank --></div>
            <div id="addfriend">
                <a href="/user?adduser=">добавить в друзья</a>
            </div>

            <div id="rightpanelsmallspace"><!-- blank --></div>

            <div id="listfriends">
                список друзей
            </div>
        </div>

        <div id="rightpanelspace">
        </div>

        <div id="rightpanelbig">
            <div id="userinfo">
                информация о юзере
            </div>

            <div id="rightpanelbigspace"><!-- blank --></div>
            <div id="usercarsinfo">
                информация о машинах
            </div>

            <div id="rightpanelbigspace"><!-- blank --></div>

            <div id="postinfo">

                посты

            </div>
        </div>
    </div>
</div>

</body>
</html>