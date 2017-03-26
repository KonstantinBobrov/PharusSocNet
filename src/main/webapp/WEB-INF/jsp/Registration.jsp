<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Register User</title>
    <link rel="stylesheet" media="screen" href="../../css/styles.css" >
</head>
<body>


<div>
    <%--suppress JspAbsolutePathInspection --%>
<form class="register_form" action="/registration" method="get" name="register_form">
    <ul>
        <li>
            <h2>Please enter registration form</h2>
            <span class="required_notification"> * Denotes Required Field </span>
        </li>
        <li>
            <!-- <label for="login_email">Email login:</label> -->
            <input type="text" name="login_email" placeholder="example@email.com2" autocomplete="on" required/>
        </li>
        <li>
            <!-- <label for="password">Password:</label> -->
            <input type="password" name="password" placeholder="password..." required/>
        </li>
        <li>
            <!-- <label for="full_name">Name:</label> -->
            <input type="text" name="full_name" placeholder="Peter Ivanov" required/>
        </li>
        <li>
            <!-- <label for="birth_date">Date:</label> -->
            <input type="text" name="full_name" placeholder="Peter Ivanov" required/>
        </li>

        <li>
            <button class="submit" type="submit">Submit Form</button>
        </li>
    </ul>
</form>
</div>

</body>
</html>
