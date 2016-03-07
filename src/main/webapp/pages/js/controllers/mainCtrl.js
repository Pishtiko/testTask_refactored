app.controller('mainCtrl', function($scope){
    if($scope.ord===undefined){
        $scope.ord = "productName";
        console.log($scope.ord)
    }
    if($scope.showWithStatus===undefined){
        $scope.showWithStatus = "UNCONFIRMED";
        console.log($scope.showWithStatus);
    }
    this.setShownStatus = function(status){
        $scope.showWithStatus = status;
        console.log($scope.showWithStatus);
    };
    this.setPage = function(page){
        console.log(page);
        $scope.pagePart = page;
    };
});
