'use strict';

var App = angular.module('myApp',[]);
App.filter('greet', function () {
    return function (name) {
        return 'Hello, ' + name + '!';
    };

});
