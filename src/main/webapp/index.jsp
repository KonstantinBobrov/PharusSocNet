<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}" />
<fmt:setBundle basename="localization"/>

<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="welcome"/></title>
  <link rel="stylesheet" media="screen" href="${pageContext.request.contextPath}css/styles.css" >
  <link rel="shortcut icon" href="${pageContext.request.contextPath}favicon.ico"/>
</head>
<body bgcolor=#edeef0>

<div id="header">
    <div id="gohome"><a href="/"><div id="smallogo" style="background-image:url('img/logo.png');"></div></a></div>
    <div id="searchinhead"></div>
    <div id="logout" ><div id="smallava"></div><a href="${pageContext.request.contextPath}/?lang=${'en' eq sessionScope['lang'] ? 'ru' : 'en'}"><fmt:message key="button_switсhto"/></a></div>
</div>

<div id="none"></div>




<div id="login" style="margin:0 auto; width:380px; background-color:white" align="center">
  <form class="login_form" action="${pageContext.request.contextPath}/login" method="post" name="login_form">
    <ul>
      <li>
        <input type="email" name="login_email" placeholder="<fmt:message key="emailplaceholder"/>" autocomplete="on" required/>
      </li>
      <li>
        <input type="password" name="password" placeholder="<fmt:message key="passwordplaceholder"/>" required/>
      </li>
      <li>
        <button class="submit" type="submit"><fmt:message key="button_login"/></button>
      </li>
    </ul>
  </form>
</div>


<div id="register" style="margin:0 auto; width:380px; background-color:white" align="center">
  <form class="register_form" action="${pageContext.request.contextPath}/registration" method="post" name="register_form">
    <ul>
      <li>
        <h2><fmt:message key="title_singup"/></h2>
        <!--<span class="required_notification"> * Required Field </span>-->
      </li>
      <li>
        <input type="email" name="login_email" placeholder="<fmt:message key="remailplaceholder"/>" autocomplete="on" required/>
      </li>
      <li>
        <input type="password" name="password" placeholder="<fmt:message key="rpasswordplaceholder"/>" required/>
      </li>
      <li>
        <input type="text" name="full_name" placeholder="<fmt:message key="rnameplaceholder"/>" required/>
      </li>
      <!-- <li>
          <input type="Date" name="full_name"/>
      </li> -->

      <li>
        <button class="submit" type="submit"><fmt:message key="button_singup"/></button>
      </li>
    </ul>
  </form>
</div>

</body>
</html>