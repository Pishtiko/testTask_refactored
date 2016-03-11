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
    var request = $http.get(URL.GET);

    $http.get(URL.GET).success(function (response) {
        products = response;
        service.scope.cartProducts = response;
        isReady = true;
        console.log(products);
    });

    (service.updateProducts = function () {

        console.log("updating");
        request.success(function (response) {
            for (var i = products.length - 1; i >= 0; i--) {
                products.remove(i);
            }
            response.forEach(function(index, op){
                products.push(op);
            });
            //products = response;
            service.scope.cartProducts = response;
            console.log("updated");
        }).error(function () {
            console.log(error);
        });
    });

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
    };
    service.removeFromCart = function (orderProduct) {
        $http.get(URL.DELETE + orderProduct.orderId)
            .success(function (response) {
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
        service.scope.cartProducts = products;
        return products;
    };
    service.makeOrder = function () {
        $http.get(URL.ORDER)
            .success(function (response) {
                products = [];
            })
            .error(function (msg) {
                console.log(msg);
            });
    };
    service.cleanTheCart = function () {

    };


    return service;
}]);