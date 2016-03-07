app.controller('mainCtrl', function($scope){
    if($scope.ord===undefined){
        $scope.ord = "productName";
    }
    if($scope.showWithStatus===undefined){
        $scope.showWithStatus = "UNCONFIRMED";
    }
    if($scope.ord===undefined){
        $scope.sortReverse = false;
    }
    this.setShownStatus = function(status){
        $scope.showWithStatus = status;
    };
    this.setPage = function(page){
        $scope.pagePart = page;
    };
    this.setSortReverse = function(sortReverse){
        $scope.sortReverse = sortReverse;
    };
    this.setSortType = function(sortType){
        if($scope.ord===sortType){
            $scope.sortReverse = !$scope.sortReverse;
        }
        else {
            $scope.sortReverse = false;
            $scope.ord = sortType;
        }
    };
});
