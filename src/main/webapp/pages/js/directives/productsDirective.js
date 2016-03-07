app.directive('productsDirective', function(){
    return {
        templateUrl:"pages/products.html",
        link: function(scope, element, attrs){
            console.log('products directive', scope, element, attrs);
        }

    }
});