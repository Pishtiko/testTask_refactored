app.factory('allOrdersFactory', function ($http) {

    var URL = {
        GET_ALL: "service/getOrderList",
        GET_DETAILS: "main/orderDetails/",
        CONFIRM:"service/confirmOrder/",
        CANCEL:"service/cancelOrder/"
    };
    var scope = {};
    var oProducts = [];
    var service = {};
    var allOrders = [];

    $http.get(URL.GET_ALL).success(function (response) {
            console.log(response);
            allOrders = response;
        })
        .error(function (msg) {
            console.log(msg)
        });

    service.injectScope = function (iScope) {
        scope = iScope;
    };

    service.updateAll =function(){
        $http.get(URL.GET_ALL).success(function (response) {
                for (var i = allOrders.length - 1; i >= 0; i--) {
                    allOrders.splice(i);
                }
                allOrders = [];
                for (var i =0; i < response.length; i++) {
                    allOrders.push(response[i]);
                }
                allOrders = response;
            })
            .error(function(msg){
                console.log(msg);
            });
    };
    scope.oProducts = [];

    service.updateAll();

    service.confirmOrder = function (order) {
        $http.post(URL.CONFIRM+order.idd, null)
            .success(function () {
                alert("Заказ подтаержден");
            });
    };
    service.cancel = function (order) {
        return $http.post(URL.CANCEL+order.idd, null);
    };

    service.getAllOrders = function(){
        return allOrders;
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