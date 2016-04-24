var cities = angular.module('cities', []);
cities.controller('citiesController', function($scope, $http){
    $http({
        url:'/cities/',
        method:'GET'
    }).then(function(resp){
        $scope.cities = resp.data;
    });
});