var matrix = angular.module('matrix', []);
matrix.controller('matrixController', function($scope, $http){
   $http({
       url:'/matrix/',
       method:'GET'
   }).then(function(resp){
       $scope.distances = resp.data;
   })
});