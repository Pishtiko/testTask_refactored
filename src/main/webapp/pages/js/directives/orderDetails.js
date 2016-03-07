app.directive('orderDetails', function( ){
    return {
        templateUrl:"pages/orderDetails.html",
        transclude: true,
        link: function(scope, element, attrs, ctrl, transclude){
            console.log('orderDetails directive', scope, element, attrs, ctrl, transclude);
        }

    }
});