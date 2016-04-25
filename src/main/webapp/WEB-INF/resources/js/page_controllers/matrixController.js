var matrix = angular.module('matrix', []);
matrix.controller('matrixController', function($scope, $http, $uibModal){
    $http({
       url:'/matrix/',
       method:'GET'
    }).then(function(resp){
       $scope.distances = resp.data;
    });
    $scope.between = function(from, to){
        $uibModal.open({
            animation:'true',
            templateUrl:'pathBetweenTwoCities.html',
            controller:'betweenTwoCitiesController',
            resolve:{
                from:from,
                to:to
            },
            size:'md'
        });
    }
})
.controller('betweenTwoCitiesController', function($scope, $http, $uibModalInstance, from, to){
    $http({
        url:'/matrix/'+from +'&'+to,
        method:'GET'
    }).then(function(resp){
        $scope.from = resp.data[0];
        $scope.to = resp.data[resp.data.length-1];
        var path = [];
        for(var i = 1; i < resp.data.length - 1; i++){
            path[i] = resp.data[i];
        }
        $scope.path = path;
    });
    $scope.close = function(){
        $uibModalInstance.dismiss('cancel');
    };
});