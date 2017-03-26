<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://pharus.ru/taglibs" prefix="ptags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="logUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="infoUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="listPosts" type="java.util.List" scope="request"/>

<fmt:setLocale value="${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}" />
<fmt:setBundle basename="localization"/>

<jsp:include page="template/header.jsp"/>
   <jsp:include page="template/leftmenu.jsp"/>
    <div id="rightpanel">
        <div id="rightpanelbig">
            <div id="friendsmodule">
                <fmt:message key="subscription"/>
                <ptags:showposts list="${listPosts}" max="10"/>
            </div>
        </div>

        <div id="rightpanelspace"></div>
        <div id="rightpanelsmall"></div>
        ${infoUser.fullName}
    </div>
</div>
</body>
</html>
