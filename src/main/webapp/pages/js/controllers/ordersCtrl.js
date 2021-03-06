app.controller('ordersCtrl', function($scope, ordersFactory, allOrdersFactory){


    var table = $('#orders-table');
    this.currentOrder = {};
    $scope.currentOrder = {};
    ordersFactory.injectScope($scope);
    allOrdersFactory.injectScope($scope);

    this.getMyOrders = function(){
        return ordersFactory.getOrders();
    };
    this.getAllOrders = function () {
        return allOrdersFactory.getAllOrders();
    };
    this.getDetails = function() {
        return $scope.oProducts;
    };
    this.getOrderDetails = function (order) {
        this.currentOrder = order;
        $scope.currentOrder = order;
        console.log(this.currentOrder);
        //this.renderDetails();
        ordersFactory.getOrderDetails(order);
    };


    //Button Handlers
    this.confirmOrder = function(){
        allOrdersFactory.confirmOrder(this.currentOrder);
        this.currentOrder.status = 'CONFIRMED';
    };
    this.cancel = function(){
        allOrdersFactory.cancel(this.currentOrder)
            .success(function () {
                ordersFactory.updateOrders();
            });
        this.currentOrder.status = 'CANCELLED';

    };
    this.backButtonHandler = function(){
        $scope.detailsShown = false;
        this.currentOrder = {};
        //table.html(oldTableContent);
    };

});

app.filter('dateFilter', function(){
    var MONTH = {
        1: "Января",
        2: "Февраля",
        3: "Марта",
        4: "Апреля",
        5: "Мая",
        6: "Июня",
        7: "Июля",
        8: "Августа",
        9: "Сентября",
        10: "Октября",
        11: "Ноября",
        12: "Декабря"
    };
    return function (date) {
        var viewDate = new Date(date);
        return viewDate.getDate()+" "+MONTH[viewDate.getMonth()+1]+" "+ viewDate.getFullYear()+"г.";
    };
});
app.filter('statusFilter', function(){
    return function (status) {
        var DICTSTATUS = {
            UNCONFIRMED:"НЕПОДТВЕРЖДЕН",
            CONFIRMED:"ПОДТВЕРЖДЕН",
            CANCELLED:"ОТМЕНЕН",
            IN_CART:"КОРЗИНА"
        };
        var output = "";
        for(var key in DICTSTATUS){
            if(DICTSTATUS.hasOwnProperty(key))
            if (status == key){
                output = DICTSTATUS[key];
                break;
            }
        }
        return output;
    };
});

app.filter('isStatus', function () {
    return function (items, showWithStatus) {
        var filtered = [];

        if (showWithStatus === "*") {
            return items;
        }
        else {
            filtered = [];
        for(var index = 0; index<items.length; index++){
            if (items[index].status===showWithStatus){
                filtered.push(items[index]);
                }
            }
        }
        return filtered;
    };
});
