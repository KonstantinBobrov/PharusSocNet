<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <title></title>
  <link rel="stylesheet" media="screen" href="css/styles.css" >
  <link rel="shortcut icon" href="favicon.ico"/>
</head>
<body bgcolor=#edeef0>

<div id="header">

</div>

<div id="none"></div>


<div id="login" style="margin:0 auto; width:380px; background-color:white" align="center">
  <form class="login_form" action="${pageContext.request.contextPath}/login" method="post" name="login_form">
    <ul>
      <li>
        <input type="email" name="login_email" placeholder="example@email.com" autocomplete="on" required/>
      </li>
      <li>
        <input type="password" name="password" placeholder="Password" required/>
      </li>
      <li>
        <button class="submit" type="submit">Log in</button>
      </li>
    </ul>
  </form>
</div>


<div id="register" style="margin:0 auto; width:380px; background-color:white" align="center">
  <form class="register_form" action="${pageContext.request.contextPath}/registration" method="post" name="register_form">
    <ul>
      <li>
        <h2>Sign up for SocNet!</h2>
        <!--<span class="required_notification"> * Required Field </span>-->
      </li>
      <li>
        <input type="email" name="login_email" placeholder="Your email example@email.com" autocomplete="on" required/>
      </li>
      <li>
        <input type="password" name="password" placeholder="Your password" required/>
      </li>
      <li>
        <input type="text" name="full_name" placeholder="Your first or full name" required/>
      </li>
      <!-- <li>
          <input type="Date" name="full_name"/>
      </li> -->

      <li>
        <button class="submit" type="submit">Sign up</button>
      </li>
    </ul>
  </form>
</div>

</body>
</html>