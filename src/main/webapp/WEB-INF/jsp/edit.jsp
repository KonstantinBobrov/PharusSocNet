<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://pharus.ru/taglibs" prefix="ptags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="user" type="ru.pharus.socnetwork.entity.User" scope="request"/>

<fmt:setLocale value="${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}"/>
<fmt:setBundle basename="localization"/>

<jsp:include page="template/header.jsp"/>

<jsp:include page="template/leftmenu.jsp"/>
<div id="rightpanel">
    <div id="rightpanelbig">
        <div id="friendsmodule">
            <fmt:message key="subscription" var="friendslist"/>

            <form class="register_form" action="${pageContext.request.contextPath}/edit" method="post"
                  name="register_form">
                <ul>
                    <li>
                        <h2><fmt:message key="editprofile"/></h2>
                    </li>
                    <li>
                        <div class="diverror">${sessionScope['errEditProfile']}</div>
                        <input type="email" name="regLoginEmail" value="${user.login}"
                               placeholder="<fmt:message key="remailplaceholder"/>" autocomplete="on" readonly/>
                    </li>
                    <li>
                        <input type="password" name="regPassword"
                               placeholder="<fmt:message key="rpasswordplaceholder"/>" required/>
                    </li>
                    <li>
                        <input type="text" name="regFullName" value="${user.fullName}"
                               placeholder="<fmt:message key="rnameplaceholder"/>" required/>
                    </li>

                    <li>
                        <input type="date" name="birthDate" value="${user.birthDate}"
                               placeholder="<fmt:message key="profile.birthDate"/>" />
                    </li>

                    <li>
                        <button class="submit" type="submit"><fmt:message key="save"/></button>
                    </li>
                </ul>
            </form>

        </div>
    </div>

    <div id="rightpanelspace"></div>
    <div id="rightpanelsmall"></div>
</div>
<jsp:include page="template/footer.jsp"/>