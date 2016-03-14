app.controller('cartCtrl', function($scope, cartFactory, ordersFactory){

    cartFactory.injectScope($scope);
    cartFactory.updateProducts();
    this.getMyCartContent = function(){
        return cartFactory.getProducts();
    };
    $scope.cartProducts = this.getMyCartContent();

    this.makeOrder = function(){
        cartFactory.makeOrder()
            .success(function (response) {
                console.log(response);
                cartFactory.updateProducts();
            });
    };
    this.removeFromCart = function (product) {
        cartFactory.removeFromCart(product);
    };
    this.saveCart = function (){
        cartFactory.saveCart();
        cartFactory.updateProducts();
        ordersFactory.updateOrders();
    };
    this.updateProducts = function () {
        return cartFactory.updateProducts();
        $scope.$apply();
    }
    this.cleanTheCart = function () {
        cartFactory.cleanTheCart();
    }
});
