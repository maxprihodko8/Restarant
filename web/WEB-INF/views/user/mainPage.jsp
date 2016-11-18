<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<html>
<head>
    <head>
        <title>Restarant User Page</title>
    </head>


    <script type="text/javascript">
        var app = angular.module("UserModule",[]);

        app.controller("UserController", function ($scope, $http) {
            $scope.orders = [];
            $scope.dishes = [];
            $scope.submitPrice = 0;
            $scope.inputs = [{name : '', count : 0}];

            $scope.updateOrders = function () {
                refreshOrders();
                //calcOrders();
                getSubmitPrice();
            };


            $scope.addNewDishChoise = function () {
                $scope.inputs.push({name : '', count : 0});
            };

            $scope.removeDishChoise = function () {
                var lastDish = $scope.inputs.length - 1;
                $scope.inputs.splice(lastDish);
            };

            $scope.saveOrder = function () {
                var method = "POST";
                var url = "http://localhost:8080/user/saveOrder";

                $http({
                    method: method,
                    url: url,
                    data: angular.toJson($scope.inputs),
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN' : getMeta('_csrf')
                    }
                }).then(onSuccess, onError);
            };

            $scope.getSubmitPrice = function () {
                $http({
                    method : 'GET',
                    url : 'http://localhost:8080/user/getSubmitPrice'
                }).then(function successCallback(response) {
                    $scope.submitPrice = response.data;
                }, function errorCallback(response) {
                    console.log(response.statusText)
                });
            };

            $scope.deleteOrder = function (id) {
                $http({
                    method : 'GET',
                    url : 'http://localhost:8080/user/deleteOrder/' + id
                }).then(function successCallback(response) {
                    updateOrders();
                }, function errorCallback(response) {
                    updateOrders();
                }).finally(function () {
                    updatePrice();
                    refreshOrders();
                });
            };

            function calcOrders() {
                console.log("TEST");
                for(var index in $scope.orders){
                    console.log("TEST");
                    console.log($scope.orders[index] = " " + $scope.orders[index]);
                }
            }

            function refreshDishes() {
                $http({
                    method : 'GET',
                    url : 'http://localhost:8080/dishList'
                }).then(function successCallback(response) {
                    $scope.dishes = response.data;
                }, function errorCallback(response) {
                    console.log(response.statusText);
                });
            }

            function refreshOrders() {
                $http({
                    method : 'GET',
                    url : 'http://localhost:8080/user/userOrders'
                }).then(function successCallback(response) {
                    $scope.orders = response.data;
                }, function errorCallback(response) {
                    console.log(response.statusText);
                });
            }

            function updatePrice() {
                $scope.getSubmitPrice();
            }

            function getMeta(name, content) {
                var content = (content == null) ? 'content' : content;
                return document.querySelector("meta[name='" + name + "']").getAttribute(content);
            }

            function onSuccess() {
                refreshOrders();
                updatePrice();
            }

            function onError(response) {
                console.log(response.statusText)
            }
            updatePrice();
            refreshDishes();
            refreshOrders();
        });
    </script>

<body ng-app="UserModule" ng-controller="UserController">
<div>
    <table>
        <tbody>
        <tr>
            <td>
                <p><a href="/user/userMainPage"> <span style="color: #800000; background-color: #ffcc00;">Домой</span></a></p>
            </td>
            <td>
                <p><a href="/user/course"> <span style="color: #800000; background-color: #ffcc00;">Сделать заказ</span></a></p>
            </td>
            <td>
                <p><a href="/user/multipleCource"> <span style="color: #800000; background-color: #ffcc00;">Групповой заказ</span></a></p>
            </td>
            <td>
                <div style="margin-left: 30px;">
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
    <h2> Ваши заказы: </h2>
    <input ng-click="updateOrders()" type="button" value="Update">

    <div ng-repeat="order in orders">

            Заказ: {{order.id}} <input ng-click="$watch(deleteOrder(order.id), updateOrders())" type="button" value="Удалить заказ"/>
            <div ng-repeat="(k,v) in order">

                <div ng-repeat="(k1, v1) in v">
                    {{k1}} - {{v1}}
                </div>
            </div>
    </div>
<input ng-click="getSubmitPrice()" type="button" value="Посчитать общую стоимость">
{{submitPrice}}
<div>
    <h2>Новый заказ:</h2>
    <fieldset ng-repeat="dishInput in inputs">
        <select ng-model="dishInput.name">
            <option ng-repeat="dish in dishes">{{dish.name}}</option>
        </select>
        <input type="number" ng-model="dishInput.count" name="" placeholder="Количество">
        <input type="button" value="Удалить" ng-model="inputs.name" ng-click="removeDishChoise()">
    </fieldset>
    <input type="button" ng-click="addNewDishChoise()" value="Добавить блюдо">
    <input type="button" ng-click="saveOrder()" value="Сохранить новый заказ">

</div>

</body>
</html>
