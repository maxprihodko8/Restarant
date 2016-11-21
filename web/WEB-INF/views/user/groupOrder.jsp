<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Group order</title>
</head>


<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script type="application/javascript" src="//cdnjs.cloudflare.com/ajax/libs/angular-ui/0.4.0/angular-ui.min.js"></script>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script>
    var app = angular.module('GroupOrderApp', ['ui.directives', 'ui.filters']);
    app.controller('GroupOrderController', function ($scope, $http) {
        $scope.groups = [];
        $scope.currentUser = "";
        $scope.formEnabled = false;
        $scope.formGroup = "";
        $scope.groupNameForReq = "";
        $scope.groupNames = [];

        $scope.inputs = [];
        $scope.selectedGroup = "";
        $scope.dishes = [{name : '', count : 0}];



         $scope.refreshDishes = function() {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/dishList'
            }).then(function successCallback(response) {
                $scope.dishes = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.updateGroups = function () {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/listGroupsOfUser'
            }).then(function successCallback(response) {
                $scope.groups = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.leftGroup = function(id){
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/leftGroup/' + id
            }).then(function successCallback(response) {
                $scope.updateGroups();
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.deleteGroup = function (id) {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/deleteGroup/' + id
            }).then(function successCallback(response) {
                $scope.updateGroups();
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.submitGroup = function () {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/addGroup/' + $scope.formGroup
            }).then(function successCallback(response) {
                $scope.updateGroups();
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.addUserToGroup = function (group, name) {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/addUserToGroup/' + group + "/" + name
            }).then(function successCallback(response) {
                $scope.updateGroups();
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.rejectUserFromGroup = function (group, name) {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/rejectUserFromGroup/' + group + "/" + name
            }).then(function successCallback(response) {
                $scope.updateGroups();
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.sendReqForEnterGroup = function () {
            if($scope.groupNameForReq != null){
                $http({
                    method : 'GET',
                    url : 'http://localhost:8080/user/sendReq/' + $scope.groupNameForReq
                }).then(function successCallback(response) {
                    $scope.groupNameForReq = "";
                    getGroupsWhichUserCanEnter();
                }, function errorCallback(response) {
                    console.log(response.statusText);
                });
            }
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

        $scope.removeDishChoise = function () {
            var lastDish = $scope.inputs.length - 1;
            $scope.inputs.splice(lastDish);
        };

        $scope.addNewDishChoise = function () {
            $scope.inputs.push({name : $scope.dishes[0].name, count : 0});
        };

        $scope.saveOrder = function () {
            var method = "POST";
            console.log($scope.selectedGroup);
            var url = "http://localhost:8080/user/saveGroupOrder/" + $scope.selectedGroup;

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

        function getGroupsWhichUserCanEnter() {
            $http({
                method : 'GET',
                url : 'http://localhost:8080/user/getGroupNames'
            }).then(function successCallback(response) {
                $scope.groupNames = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }

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

        function onSuccess() {
            $scope.updateGroups();
        }

        function onError(response) {
            console.log(response.statusText)
        }

        function getMeta(name, content) {
            var content = (content == null) ? 'content' : content;
            return document.querySelector("meta[name='" + name + "']").getAttribute(content);
        }

        getUserName();
        $scope.updateGroups();
        $scope.refreshDishes();
        getGroupsWhichUserCanEnter();
    });
</script>

<body ng-app="GroupOrderApp" ng-controller="GroupOrderController">
<div>
    <table>
        <tbody>
        <tr>
            <td>
                <p><a href="/user/userMainPage"> <span style="color: #800000; background-color: #ffcc00;">Домой</span></a></p>
            </td>
            <td>
                <div style="margin-left: 30px;">
                    <p>Вы зарегистрированы под именем <sec:authentication property="name"/> </p>
                </div>
            </td>
            <td>
                <p><a class="button_logout" href="<c:url value="/logout"/> ">
                    <span style="color: #800000; background-color: #ffcc00;">Выйти</span></a> </p>
            </td>

        </tr>
        </tbody>
    </table>

</div>

    <h3>Список групп, в которых вы состоите :</h3> <input ng-click="updateGroups()" value="Обновить" type="button">
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

<h3>Создать новый групповой заказ</h3>

<form>
    <select ng-model="selectedGroup">
        <option ng-repeat="group in groups" >{{group.name}}</option>
    </select>
    <fieldset ng-repeat="dishInput in inputs">

        <select ng-model="dishInput.name">
            <option ng-repeat="dish in dishes">{{dish.name}}</option>
        </select>

        <input type="number" ng-model="dishInput.count" name="" placeholder="Количество">
        <input type="button" value="Удалить" ng-model="inputs.name" ng-click="removeDishChoise()">
    </fieldset>

    <input type="button" ng-click="addNewDishChoise()" value="Добавить блюдо">
    <input type="button" ng-click="saveOrder()" value="Сохранить новый заказ">
</form>


<h3>Создать новую группу :</h3>
<input type="checkbox" ng-model="formEnabled">
    <input ng-disabled="!formEnabled" type="text" value="Введите название" ng-model="formGroup"
           placeholder="Название">
    <input type="button" value="Сохранить" ng-click="submitGroup()"/>
<br>
<h3>Заявки в группу: </h3>
    <table>
        <tr>
            <td>Имя группы</td>
            <td>Пользователь</td>
            <td>Добавить</td>
            <td>Отказать</td>
        </tr>
        <tr ng-repeat="group in groups" ng-if="group.creator == currentUser.name">
                <td ng-repeat="nm in group.reqs">
                    <ul>
                        <li>{{nm}} - {{group.id}}</li>
                        <li><input type="button" ng-click="addUserToGroup(group.id, nm)" value="Добавить"></li>
                        <li><input type="button" ng-click="rejectUserFromGroup(group.id, nm)" value="Отказать"></li>
                    </ul>
                </td>
        </tr>
    </table>

<br>
<h3>Новая заявка на вступление в группу:</h3>

    <select ng-model="groupNameForReq">
        <option ng-repeat="groupName in groupNames">{{groupName}}</option>
    </select>
    <input type="button" value="Подтвердить" ng-click="sendReqForEnterGroup()">

</body>
</html>
