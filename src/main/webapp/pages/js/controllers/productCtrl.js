app.controller('productCtrl', function (productFactory, cartFactory) {

    this.addToCart = function(product){
        cartFactory.addToCart(product);
    };

});