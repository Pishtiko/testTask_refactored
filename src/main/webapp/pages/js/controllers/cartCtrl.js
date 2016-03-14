app.controller('cartCtrl', function($scope, cartFactory){

    cartFactory.injectScope($scope);
    cartFactory.updateProducts();
    this.getMyCartContent = function(){
        return cartFactory.getProducts();
    };
    $scope.cartProducts = this.getMyCartContent();

    this.makeOrder = function(){
        cartFactory.makeOrder();
    };
    this.removeFromCart = function (product) {
        cartFactory.removeFromCart(product);
    };
    this.saveCart = function (){
        cartFactory.saveCart();
        cartFactory.updateProducts();
    };
    this.updateProducts = function () {
        return cartFactory.updateProducts();
        $scope.$apply();
    }
});
