<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}" />
<fmt:setBundle basename="localization"/>
<div id="leftpanel">
    <!--Левая панель-->
    <div id="menu">
        <ol class="listmenu">
            <li>
                <a href="/user" class="left_row">
                    <span class="left_label inl_bl"><fmt:message key="menu.myprofile"/> </span>
                    </span>
                </a>
            </li>
            <li>
                <a href="/feed" class="left_row">
                    <span class="left_label inl_bl"><fmt:message key="menu.news"/></span>
                    </span>
                </a>
            </li>
            <li>
                <a href="/im" class="left_row">
                    <span class="left_label inl_bl"><fmt:message key="menu.messages"/></span>
                    </span>
                </a>
            </li>
            <li>
                <a href="/friends" class="left_row">
                    <span class="left_label inl_bl"><fmt:message key="menu.friends"/></span>
                    </span>
                </a>
            </li>
        </ol>
    </div>
</div>