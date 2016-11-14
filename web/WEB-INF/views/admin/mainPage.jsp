<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>
</head>
<body>
<div>
<h2>
    Вы теперь админ, <sec:authentication property="name"/>
    <a href="/admin/userList">Вы можете посмотреть список зарегистрированных людей: </a>
    <br>
    <a href="/admin/dishList">Вы можете посмотреть список блюд: </a>

</h2>
</div>
</body>
</html>
