app.controller('productsCtrl', function (productsFactory, cartFactory, $scope) {

    //VARS
    this.searchInput = "";
    //this.selectedProduct = null;
    this.scope = $scope;
    this.orderBy = [
        {
            name: "цене",
            value: "price"
        },
        {
            name: "названию",
            value: "productName"
        }
    ];


    this.products = function(){
        return productsFactory.getProducts();
    };
    this.find = function(){
        productsFactory.find(this.searchInput);
            $scope.searchResult = this.searchInput;
            this.searchInput = "";
    };
    this.delete = function(id){
        productsFactory.delete(id);
    };

    this.addToCart = function(product){
        cartFactory.addToCart(product);
    }

});
app.filter('moneyFilter', function(){
    return function (str) {
        return "$"+str+".00";
    }
});

app.filter('searchKeyUnderline', function () {
    return function (str, searchResult) {
        var regexp = new RegExp(searchResult.toLowerCase());
        if (searchResult != "") {
            return str.toLowerCase()
                .replace(regexp, function (nstr) {
                return "<strong>" + nstr + "</strong>";
            });
        } else return str;
    }
});

app.filter('searchResultFilter', function () {
    return function (searchKey) {
        if (searchKey === "") {
            return "все товары"
        }
        else {
            return "товары по запросу: \"" + searchKey+"\"";
        }
    }
});

app.filter('startsWithA', function () {
    return function (items) {
        return items.filter(function (item) {
            return /a/i.test(item.name.substring(0, 1));
        });
    };
});
app.filter('startsWithLetter', function () {
    return function (items, letter) {
        var filtered = [];
        var letterMatch = new RegExp(letter, 'i');
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            if (letterMatch.test(item.name.substring(0, 1))) {
                filtered.push(item);
            }
        }
        return filtered;
    };
});

//<li ng-repeat="friend in person.friends | startsWithLetter:letter:number:somethingElse:anotherThing">
