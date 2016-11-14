<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fancy
  Date: 11.11.16
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>
</head>
<body>
<div>
    <h2>Список людей зарегистрированных в системе</h2>
    <table>
        <tr>
            <td> ID </td>
            <td> Имя </td>
            <td> Админ </td>
            <td> Онлайн </td>
        </tr>
        <c:forEach items='${userListWithOnlineTag}' var="user">
            <tr>
                <td> <c:out value='${user.key.id}'/> </td>>
                <td> <c:out value='${user.key.name}'/> </td>
                <td> <c:out value='${user.key.admin}'/> </td>
                <td> <c:if test="${user.value == true}">Да</c:if>
                        <c:if test="${user.value == false}">Нет</c:if> </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
