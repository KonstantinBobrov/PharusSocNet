<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://pharus.ru/taglibs" prefix="ptags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="logUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="infoUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="listPosts" type="java.util.List" scope="request"/>
<jsp:useBean id="listFriends" type="java.util.List" scope="request"/>
<jsp:useBean id="listCars" type="java.util.List" scope="request"/>

<fmt:setLocale value="${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}"/>
<fmt:setBundle basename="localization"/>

<jsp:include page="template/header.jsp" flush="true"/>
<jsp:include page="template/leftmenu.jsp"/>


<div id="rightpanel">
    <div id="rightpanelsmall">
        <div id="avatarcontainter">
            <div id="bavatar" style="background-image:url('img/ava/${infoUser.avatar}')"></div>
        </div>
        <div id="rightpanelsmallspace"><!-- blank --></div>
        <div id="addfriend">
            <c:choose>
                <c:when test="${logUser.id == infoUser.id}">
                    <fmt:message key="editprofile" var="editprofile"/>
                    <ptags:Button caption="${editprofile}" url="/edit"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="subscribe" var="subscribe"/>
                    <fmt:message key="unsubscribe" var="unsubscribe"/>
                    <ptags:SubscribeButton caption="${subscribe}" uncaption="${unsubscribe}"/>
                    <br>
                    <fmt:message key="writeMessage" var="writemessage"/>
                    <ptags:MessageButton caption="${writemessage}"/>
                </c:otherwise>
            </c:choose>
        </div>

        <div id="rightpanelsmallspace"><!-- blank --></div>

        <div id="listfriends">
            <fmt:message key="subscription" var="friendslist"/>
            <ptags:showfriends list="${listFriends}" title="${friendslist}" cols="3" max="6"/>
        </div>
    </div>

    <div id="rightpanelspace">
    </div>

    <div id="rightpanelbig">
        <div id="userinfo">
            <ul>
                <li>${infoUser.fullName}</li>
                <li>дата рождения: ${infoUser.birthDate}</li>
                <li>дата регистрации:
                    <c:set var="cleanedDateTime" value="${fn:replace(infoUser.registerDate, 'T', ' ')}"/>
                    <fmt:parseDate value="${ cleanedDateTime }" pattern="yyyy-MM-dd" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/></li>
                <li>статус: ${'ADMIN' eq infoUser.role ? 'Администратор' : 'Пользователь'}</li>
            </ul>


        </div>

        <div id="rightpanelbigspace"><!-- blank --></div>
        <div id="usercarsinfo">
            Гараж - всего машин (${listCars.size()})
            <ptags:showcars list="${listCars}" max="2"/>
        </div>

        <div id="rightpanelbigspace"><!-- blank --></div>

        <div id="postinfo">
            Бортжурнал - всего записей (${listPosts.size()})
            <ptags:showposts list="${listPosts}" max="3"/>
        </div>
    </div>
</div>

<jsp:include page="template/footer.jsp"/>