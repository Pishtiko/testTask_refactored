app.factory('ordersFactory', function ($http, $rootScope) {

    var URL = {
        GET: "main/getMyOrders",
        GET_DETAILS: "main/orderDetails/",
        CONFIRM:"service/confirmOrder/",
        CANCEL:"service/cancelOrder/"
    };
    var scope = {};
    var oProducts = [];
    var service = {};
    var orders = [];
    var request = $http.get(URL.GET);


    service.injectScope = function (iScope) {
        scope = iScope;
    };
    (service.updateOrders = function () {
        request.success(function (response) {
            for (var i = orders.length - 1; i >= 0; i--) {
                delete orders[i];
            }
            orders = [];
            for (var i =0; i < response.length; i++) {
                orders.push(response[i]);
            }
            console.log(orders);
        });
    });
    scope.oProducts = [];

    service.updateOrders();
    service.confirmOrder = function (order) {
        $http.post(URL.CONFIRM+order.idd, null)
            .success(function () {
                alert("Заказ подтаержден");
            });
    };
    service.cancel = function (order) {
        return $http.post(URL.CANCEL+order.idd, null);
    };
    service.getOrders = function () {
        return orders;
    };
    service.getOrderDetails = function (order) {
        console.log("updating details");
        $http.get(URL.GET_DETAILS + order.idd)
            .success(function (response) {
                if (scope.oProducts !== undefined) {
                    for (var i = scope.oProducts.length - 1; i >= 0; i--) {
                        delete scope.oProducts[i];
                        //delete oProducts[i];
                    }
                    scope.oProducts = [];
                } else {
                    scope.oProducts = [];
                }
                for (var ii = 0; ii < response.length; ii++) {
                    scope.oProducts.push(response[ii]);
                }
                scope.detailsShown = true;
            })
            .error(function () {
                console.log(error);
            });
    };
    service.getDetails = function () {
        return oProducts;
    };

    return service;
});