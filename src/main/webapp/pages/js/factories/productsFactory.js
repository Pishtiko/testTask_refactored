app.factory('productsFactory', ['$http', '$rootScope',  function ($http, $rootScope ) {

    var service = {};
    var URL = {
        GET: "main/getProductList/price",
        FIND: function(searchKey){return "main/findProduct/"+searchKey+"/"+$rootScope.ord}
    };
    $http.get(URL.GET).success(function(response){
        products = response;
        isReady = true;
        console.log(products);
    });
    var request = $http.get(URL.GET);
    var searchRequest = function(searchKey){return $http.get(URL.FIND(searchKey));};
    var products = [];
    var isReady = false;


    service.updateProducts = function () {
        request.success(function (response) {
            products = response;
        }).error(function(){console.log(error);});
    };
    service.getProducts = function () {
        return products;
    };
    service.addProduct = function(){};
    service.find = function(searchInput){
        if (searchInput=="")
            service.updateProducts();
        else{
        searchRequest(searchInput)
            .success(function (response) {
                products = response;
            }).error(function(response){
                console.log(response);
        });
        }
        //return products;
    };

    return service;
}]);