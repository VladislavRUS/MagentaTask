var crowflight = angular.module('crowflight', []);
crowflight.controller('crowflightController', function($scope, $http){
    $http({
        url:'/crowflight/',
        method:'GET'
    }).then(function(resp){
        $scope.distances = resp.data;
    })
});