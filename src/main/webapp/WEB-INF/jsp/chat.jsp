<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://pharus.ru/taglibs" prefix="ptags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="logUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="infoUser" type="ru.pharus.socnetwork.entity.User" scope="request"/>
<jsp:useBean id="listMessages" type="java.util.List" scope="request"/>

<fmt:setLocale value="${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}" />
<fmt:setBundle basename="localization"/>

<jsp:include page="template/header.jsp"/>
   <jsp:include page="template/leftmenu.jsp"/>
    <div id="rightpanel">
        <div id="rightpanelbig">
            <div id="friendsmodule">
                <fmt:message key="lastmessages"/> ${infoUser.fullName} (${listMessages.size()})<br>
                <ptags:ChatMessages list="${listMessages}"/>

                <p>
                <form action='/messages' method='get'>
                    <input hidden name="id" value="${infoUser.id}"/>
                    <textarea name='send' rows=3 cols=50 minlength=1 maxlength=1000 style='resize: vertical;' required></textarea>
                    <input type='submit' size=25>
                </form>

            </div>
        </div>

        <div id="rightpanelspace"></div>
        <div id="rightpanelsmall"></div>
        ${infoUser.fullName}
    </div>
<jsp:include page="template/footer.jsp"/>
