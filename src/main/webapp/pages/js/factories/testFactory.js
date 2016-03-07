app.factory('testFactory', ['$http', function ($http, testController) {

    var service = {};
    var URL = {
        GET: "http://localhost:8084/secure/main/getProductList/price"
    };
    var cards = $http.get(URL.GET);
    var isReady = false;
    var filteredCards = [];

        service.getCards = function () {
            cards.success(function(response){
            cards = response;
                isReady = true;
                for (var i =0; i<cards.length;i++){
                    filteredCards.push(cards[i])
                }
        });
            return filteredCards;
        };


        return service;
    }]);