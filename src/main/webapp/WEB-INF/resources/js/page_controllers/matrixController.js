var matrix = angular.module('matrix', []);
matrix.controller('matrixController', function($scope, $http){
   $http({
       url:'/distances/',
       method:'GET'
   }).then(function(resp){
       $scope.distances = resp.data;
   })
});