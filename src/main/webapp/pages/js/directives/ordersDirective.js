app.directive('ordersDirective', function(){
    return {
        templateUrl:"pages/orders.html",
        link: function(){
            console.log('orders directive');
        }

    }
});