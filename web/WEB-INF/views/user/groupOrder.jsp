<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Group order</title>
</head>


<link rel="stylesheet" type="text/css" href="/resources/css/someStyles.css"/>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script>
    var app = angular.module('GroupOrderApp', []);
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
        getUserName();
        $scope.updateGroups();
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

    Список групп, в которых вы состоите : <input ng-click="updateGroups()" value="Обновить" type="button">
    <table>
        <tr>
            <td>Id группы</td>
            <td>Имя группы</td>
            <td>Создатель</td>
        </tr>
        <tr ng-repeat="group in groups">
            <td>{{group.id}}</td>
            <td>{{group.name}}</td>
            <td>{{group.creator}}</td>
            <td><input type="button" value="Покинуть" ng-click="leftGroup(group.id)"></td>
            <td><input type="button" value="Удалить группу" ng-click="deleteGroup(group.id)" ng-if="group.creator==userName.name"></td>
        </tr>
        <tr ng-repeat="group in groups">
            <td>

            </td>
        </tr>
    </table>

Создать новую группу :
<input type="checkbox" ng-model="formEnabled">
    <input ng-disabled="!formEnabled" type="text" value="Введите название" ng-model="formGroup"
           placeholder="Название">
    <input type="button" value="Сохранить" ng-click="submitGroup()"/>
<br>
Заявки в группу:
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
Новая заявка на вступление в группу:

    <select ng-model="groupNameForReq">
        <option ng-repeat="groupName in groupNames">{{groupName}}</option>
    </select>
    <input type="button" value="Подтвердить" ng-click="sendReqForEnterGroup()">

</body>
</html>
