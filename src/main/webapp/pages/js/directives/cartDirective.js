app.directive('cartDirective', function( ){
    return {
        templateUrl:"pages/cart.html",
        transclude: true,
        link: function(scope){
            scope.$watch(
                function thingToWatch(){ return scope.cartProducts; },
                function whatToDo(cartProducts){
                    console.log(cartProducts);}
            );
            console.log('cart directive');
        }

    }
});