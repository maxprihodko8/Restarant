
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserGroups Restaurant</title>
</head>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script type="application/javascript" src="//cdnjs.cloudflare.com/ajax/libs/angular-ui/0.4.0/angular-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>
<script>
    var app = angular.module('GroupOrderApp', ['ui.directives', 'ui.filters']);
    app.controller('GroupOrderController', function ($scope, $http) {
        $scope.groups = [];
        $scope.currentUser = "";
        $scope.formEnabled = false;
        $scope.formGroup = "";
        $scope.groupNameForReq = "";
        $scope.groupNames = [];


        $scope.updateGroups = function () {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/admin/listAllUserGroups'
            }).then(function successCallback(response) {
                $scope.groups = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.getOrderPrice = function (group, order) {
            var price = 0;
            var g =  $scope.groups[$scope.groups.indexOf(group)];

            if($scope.groups[g] !== -1){
                var index = g.userOrders.indexOf(order);
                var ggg = g.userOrders[index];
                for(m in ggg.dishesForOrder){
                    var mm = ggg.dishesForOrder[m];
                    price += mm.count * mm.dish.price;
                }
            }
            return price;
        };

        $scope.getPriceByUser = function (group, userName) {
            var price = 0;
            var g =  $scope.groups[$scope.groups.indexOf(group)];
            if($scope.groups[g] !== -1){
                for(i in g.userOrders) {
                    var ii = g.userOrders[i];
                    for (m in ii.dishesForOrder) {
                        var mm = ii.dishesForOrder[m];
                        if (userName === ii.creator) {
                            price += mm.count * mm.dish.price;
                        }
                    }
                }
            }
            return price;
        };

        $scope.getFullPriceForGroup = function (group) {
            var price = 0;
            var g =  $scope.groups[$scope.groups.indexOf(group)];
            if($scope.groups[g] !== -1) {
                for (i in g.users) {
                    var ii = g.users[i];
                    price += $scope.getPriceByUser(group, ii);
                }
            }
            return price;
        };


        function getUserName() {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/currentUser'
            }).then(function successCallback(response) {
                $scope.currentUser = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }
        getUserName();
        $scope.updateGroups();
    });
</script>

<body ng-app="GroupOrderApp" ng-controller="GroupOrderController">
<a href="/admin/mainPage">На главную</a>
<br>
<a href="/">На самую главную</a>
<h3>Список групп :</h3> <input ng-click="updateGroups()" value="Обновить" type="button">
<table border="1">
    <tr>
        <td><b>Id группы</b></td>
        <td><b>Имя группы</b></td>
        <td><b>Создатель</b></td>
    </tr>
    <tbody ng-repeat="group in groups">
    <tr>
        <td>{{group.id}}</td>
        <td>{{group.name}}</td>
        <td>{{group.creator}}</td>
        <td><input type="button" value="Покинуть" ng-click="leftGroup(group.id)"></td>
        <td><input type="button" value="Удалить группу" ng-click="deleteGroup(group.id)"
                   ng-if="group.creator==creator.name"></td>

    </tr>
    <tr>
        <td></td>
        <td><b>Id</b></td>
        <td><b>Пользователь</b></td>
    </tr>
    <tr ng-repeat="order in group.userOrders | orderBy:'-creator'">
        <td></td>
        <td>{{order.id}}</td>
        <td>{{order.creator}}</td>
        <td><table border="1">
            <tr>
                <th>Название</th>
                <th>Цена</th>
                <th>Количество</th>
                <th>Стоимость</th>
            </tr>
            <tr ng-repeat="dsh in order.dishesForOrder">
                <td > {{dsh.dish.name}} </td>
                <td > {{dsh.dish.price}} </td>
                <td > {{dsh.count}} </td>
                <td > {{dsh.count * dsh.dish.price}} </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>{{getOrderPrice(group, order)}}</td>
            </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td>Пользователь</td>
        <td>Сумма</td>
    </tr>
    <tr ng-repeat="user in group.users">
        <td></td>
        <td></td>
        <td>{{user}}</td>
        <td>{{getPriceByUser(group, user)}}</td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td><b>Полная цена</b></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td>{{getFullPriceForGroup(group)}}</td>
    </tr>
    </tbody>
</table>
</body>
</html>
