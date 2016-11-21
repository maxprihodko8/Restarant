<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>
</head>
<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>
<body>
<div>
<h2>
    Вы администратор, <sec:authentication property="name"/>
    <br>
    <a href="/admin/userList">Вы можете посмотреть список зарегистрированных людей: </a>
    <br>
    <a href="/admin/dishList">Вы можете посмотреть список блюд: </a>
    <br>
    <a href="/admin/userGroups">Вы можете посмотреть список групп пользователей: </a>
</h2>
</div>
</body>
</html>
