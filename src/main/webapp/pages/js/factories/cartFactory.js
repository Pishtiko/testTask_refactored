app.factory('cartFactory', ['$http', '$rootScope', function ($http) {

    var cartId = {};

    var isReady = false;
    var service = {};
    service.scope = {};
    service.injectScope = function (scope) {
        service.scope = scope;
    };

    var products = [];

    var URL = {
        CARTID: "main/getMyCart",
        GET: "main/getCartContent",
        CLEAN: "main/cleanTheCart",
        ORDER: "main/makeOrder",
        PUT: "main/addToCart/",
        DELETE: "main/removeFromCart/",
        SAVE: "main/updateCart"
    };
    $http.get(URL.CARTID)
        .success(function (response) {
            cartId = response;
            console.log(cartId);
        })
        .error(function (msg) {
            console.log(msg);
        });

    $http.get(URL.GET).success(function (response) {
        products = response;
        //service.scope.cartProducts = response;
        isReady = true;
        console.log(products);
    });

    service.updateProducts = function () {

        console.log("updating");
        $http.get(URL.GET).success(function (response) {
            for (var i = products.length - 1; i >= 0; i--) {
                products.splice(i);
            }
            products =[];
            //products = response;
            for (var i =0; i < response.length; i++) {
                products.push(response[i]);
            }
            products = response;
            service.scope.cartProducts = response;
            console.log("updated");
        }).error(function () {
            console.log(error);
        });
    };

    (service.updateProducts());


    service.saveCart = function () {
        $http.post(URL.SAVE, products)
            .success(function (response) {
                console.log(response);
                service.updateProducts();
                alert("Сохранено");
            })
            .error(function (msg) {
                console.log(msg);
            });
    };
    service.addToCart = function (product) {
        if (_.filter(products, {productId: product}).length===0){
            $http.post(URL.PUT + product.productId + "/" + 1, null)
                .success(function (response) {
                    console.log(response);
                    alert("Добавлено в корзину");
                    products.push({
                        count: 1,
                        idd: cartId,
                        orderId: response,
                        productId: product
                    });
                })
                .error(function (msg) {
                    alert("ошибка");
                    console.log(msg);
                });
        }else {
            alert("Товар уже в корзине");
        }

    };
    service.removeFromCart = function (orderProduct) {
        $http.get(URL.DELETE + orderProduct.orderId)
            .success(function (response) {
                console.log(response);
                service.scope.cartProducts = products;
                alert("удалено");
                _.pull(products, orderProduct);
            })
            .error(function (msg) {
                console.log(msg);
            });
    };

    service.getProducts = function () {
        //service.updateProducts();
        //products = service.scope.cartProducts;
        return products;
    };
    service.makeOrder = function () {
        return $http.post(URL.ORDER, null);
    };
    service.cleanTheCart = function () {
        $http.post(URL.CLEAN, null)
            .success(function () {
                alert("Товары удалены из корзины");
            })
            .error(function(msg){
                console.log(msg);
            })
    };


    return service;
}]);