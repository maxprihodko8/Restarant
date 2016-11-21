<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restarant Dishes</title>
</head>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>

<script type="text/javascript">
    var app = angular.module("dishApp",[]);

    app.controller("dishController", function ($scope, $http) {
        $scope.dishes = [];
        $scope.dishForm = {
            id : -1,
            name : "",
            price : 0,
            type : ""
        };

        refreshData();

        function refreshData() {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/dishList'
            }).then(function successCallback(response) {
                $scope.dishes = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }

        $scope.submitDish = function () {
            var method = "";
            var url = "";
            if ($scope.dishForm.id == -1) {
                method = "POST";
                url = "http://localhost:8080/admin/addDish";
            } else {
                method = "POST";
                url = "http://localhost:8080/admin/dishEdit";
            }

            $http({
                method: method,
                url: url,
                data: angular.toJson($scope.dishForm),
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN' : getMeta('_csrf')
                }
            }).then(onSuccess, onError);
        };


        $scope.deleteDish = function (dish) {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/admin/dishDel/' + dish.id
            }).then(onSuccess, onError);
        };

        $scope.editDish = function (dish) {
            $scope.dishForm.id = dish.id;
            $scope.dishForm.name = dish.name;
            $scope.dishForm.price = dish.price;
            $scope.dishForm.type = dish.type;
        };

        function onSuccess() {
            refreshData();
            clearForm();
        }

        function onError(response) {
            console.log(response.statusText)
        }

        function clearForm() {
            $scope.dishForm.id = -1;
            $scope.dishForm.name = "";
            $scope.dishForm.price = 0;
            $scope.dishForm.type = "";
        }

        function getMeta(name, content) {
            var content = (content == null) ? 'content' : content;
            return document.querySelector("meta[name='" + name + "']").getAttribute(content);
        }
    });
</script>


<body ng-app="dishApp" ng-controller="dishController">
<a href="/admin/mainPage">На главную</a>
<br>
<a href="/">На самую главную</a>
<!--<div>
    <h2>В рестаране сегодня вот такие блюда:</h2>
    <table>
    <tr>
        <td> ID </td>
        <td> Название </td>
        <td> Цена </td>
        <td> Тип блюда </td>
    </tr>
    <c:forEach items='${dishList}' var="dish">
        <tr>
            <td> <c:out value='${dish.id}'/> </td>>
            <td> <c:out value='${dish.name}'/> </td>
            <td> <c:out value='${dish.price}'/> </td>
            <td> <c:out value='${dish.type}'/> </td>
            <td> <a href="edit?name=${dish.name}">Редактировать</a> </td>
            <td> <a href="delete?name=${dish.name}">Удалить</a> </td>
        </tr>
    </c:forEach>
        <tr>
            <td colspan="7">
                <a href="admin/AddDish">Добавить блюдо</a></td>
            </td>
        </tr>
    </table>

    <h3>
        ${error}
    </h3>
</div>

-->

<form ng-submit="submitDish()" name="dForm">
    <table>
        <tr>
            <th colspan="2"> <span ng-if="dishForm.id == 1" ng-model="dForm"> Добавить</span> </th>
            <th colspan="2"> <span ng-if="dishForm.id == -1" ng-model="dForm"> Изменить</span> </th>
        </tr>
        <tr>
            <td>Имя</td>
            <td>
                <input type="text" ng-model="dishForm.name" name="dishName" placeholder="Enter name" required ng-minlength="3"/>
                <span ng-show="dForm.$dirty && dForm.dishName.$error.required"> Это поле обязательно для заполнения</span>
                <span ng-show="dForm.$dirty && dForm.dishName.$error.minlength"> Мин длина = 3 </span>
                <span ng-show="dForm.$dirty && dForm.dishName.$invalid"> Это поле инвалидно!</span>
            </td>
        </tr>
        <tr>
            <td>Цена</td>
            <td>
                <input type="text" ng-model="dishForm.price" name="dishPrice" placeholder="Enter price" required ng-minlength="1"/>
                <span ng-show="dForm.$dirty && dForm.dishPrice.$error.required">Это поле обзательно для заполнения</span>
                <span ng-show="dForm.$dirty && dForm.dishPrice.$error.minlength">Это поле должно быть больше 3 символов</span>
                <span ng-show="dForm.$dirty && dForm.dishPrice.$invalid">Это поле не валидно</span>
            </td>
        </tr>
        <tr>
            <td>Тип</td>
            <td>
                <input type="text" ng-model="dishForm.type" name="dishType" placeholder="Enter type" required ng-minlenght="3"/>
                <span ng-show="dForm.$dirty && dForm.dishType.$error.required">Это поле обзательно для заполнения</span>
                <span ng-show="dForm.$dirty && dForm.dishType.$error.minlength">Это поле должно быть больше 3 символов</span>
                <span ng-show="dForm.$dirty && dForm.dishType.$invalid">Это поле не валидно</span>
            </td>

        </tr>
        <tr>
            <td colspan="4"><input type="submit" value="Submit" ng-disabled="dForm.$invalid"/> </td>
        </tr>
    </table>
</form>
<div>
    <h2>В ресторане такие блюда:</h2>
    <table>
        <tr>
            <td> ID </td>
            <td> Название </td>
            <td> Цена </td>
            <td> Тип блюда </td>
        </tr>
        <tr ng-repeat="dish in dishes">
            <td>{{ dish.id }}</td>
            <td>{{ dish.name }}</td>
            <td>{{ dish.price }}</td>
            <td>{{ dish.type }}</td>
            <td>
                <input ng-click="editDish(dish)" type="button" value="Изменить"/> | <input ng-click="deleteDish(dish)" type="button" value="Удалить">
            </td>
        </tr>
        </table>
</div>

</body>
</html>
