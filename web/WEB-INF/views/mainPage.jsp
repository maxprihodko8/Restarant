<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restarant</title>
</head>

<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>

<body>
<div>
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
        <sec:authorize access="isAuthenticated()">
            <p><a class="button_logout" href="<c:url value="/logout"/> ">

        <h3 style="text-align: center;"><span style="color: #000000;">Привет, <sec:authentication property="name"/> !</span></h3>
        </sec:authorize>
        <h1 style="text-align: center;"><span style="color: #800000;"><strong>Добро пожаловать в наш ресторан!</strong></span></h1>
        <p>Сегодня у нас такие блюда:</p>
    </div>
    <div>
        <h2>Первые блюда</h2>
        <table>
            <tr>
                <td> ID </td>
                <td> Название </td>
                <td> Цена </td>
            </tr>
            <c:forEach items='${dishList}' var="dish">
                <c:if test="${dish.type eq 'Первое'}">
                <tr>
                    <td> <c:out value='${dish.id}'/> </td>>
                    <td> <c:out value='${dish.name}'/> </td>
                    <td> <c:out value='${dish.price}'/> </td>
                    <td> <c:out value='${dish.type}'/> </td>
                </tr>
                </c:if>

            </c:forEach>

        </table>
        <h2>Вторые блюда</h2>
        <table>
            <tr>
                <td> ID </td>
                <td> Название </td>
                <td> Цена </td>
            </tr>
            <c:forEach items='${dishList}' var="dish">
                <c:if test="${dish.type eq 'Второе'}">
                    <tr>
                        <td> <c:out value='${dish.id}'/> </td>>
                        <td> <c:out value='${dish.name}'/> </td>
                        <td> <c:out value='${dish.price}'/> </td>
                        <td> <c:out value='${dish.type}'/> </td>
                    </tr>
                </c:if>

            </c:forEach>

        </table>
        <h2>Третьи блюда</h2>
        <table>
            <tr>
                <td> ID </td>
                <td> Название </td>
                <td> Цена </td>
            </tr>
            <c:forEach items='${dishList}' var="dish">
                <c:if test="${dish.type eq 'Третье'}">
                    <tr>
                        <td> <c:out value='${dish.id}'/> </td>>
                        <td> <c:out value='${dish.name}'/> </td>
                        <td> <c:out value='${dish.price}'/> </td>
                        <td> <c:out value='${dish.type}'/> </td>
                    </tr>
                </c:if>

            </c:forEach>

        </table>
        <h2>Остальные блюда</h2>
        <table>
            <tr>
                <td> ID </td>
                <td> Название </td>
                <td> Цена </td>
            </tr>
            <c:forEach items='${dishList}' var="dish">
                <c:if test="${dish.type ne 'Первое' && dish.type ne 'Второе' && dish.type ne 'Третье'}">
                    <tr>
                        <td> <c:out value='${dish.id}'/> </td>>
                        <td> <c:out value='${dish.name}'/> </td>
                        <td> <c:out value='${dish.price}'/> </td>
                        <td> <c:out value='${dish.type}'/> </td>
                    </tr>
                </c:if>
            </c:forEach>

        </table>
    </div>

</div>

</body>

</html>
