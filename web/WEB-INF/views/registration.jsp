<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restarant Registration</title>
</head>
<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>
<body>
<div>
    <table>
        <tbody>
        <tr>
            <td>
                <p><a href="/"> <span style="color: #800000; background-color: #ffcc00;">Домой</span></a></p>
            </td>
            <td>
                <p><a href="/user/course"> <span style="color: #800000; background-color: #ffcc00;">Сделать заказ</span></a></p>
            </td>
            <td>
                <p><a href="/user/multipleCource"> <span style="color: #800000; background-color: #ffcc00;">Групповой заказ</span></a></p>
            </td>
            <td>
                <div style="margin-left: 30px;">
                    <sec:authorize access="!isAuthenticated()">
                        <p><a class="button_login" href="<c:url value="/login"/> " role="button">Войти</a> </p>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <p>Вы зарегистрированы под именем :<sec:authentication property="name"/> </p>
                    </sec:authorize>
                </div>
            </td>
            <td>
                <sec:authorize access="isAuthenticated()">
                    <p><a class="button_logout" href="<c:url value="/logout"/> ">
                        <span style="color: #800000; background-color: #ffcc00;">Выйти</span></a> </p>
                </sec:authorize>
            </td>

        </tr>
        </tbody>
    </table>

</div>
<div>
    <h1>
        Регистрация нового пользователя
    </h1>
    <spring:form method="post"  modelAttribute="userData" action="check-registration">

        Login: <br/><spring:input path="name" id="usrName" maxlength="30" size="30"/>  <br/>
        Password: <br/><spring:input path="password" id="usrPass" size="30" type="password" maxlength="30"/>   <br/>
        <spring:button>Зарегистрироваться</spring:button>

    </spring:form>
    <h2>
        ${messageError}
    </h2>
</div>
</body>
</html>
