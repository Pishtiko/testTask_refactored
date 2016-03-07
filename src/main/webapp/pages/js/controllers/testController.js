app.controller('testController', function (testFactory) {

    console.log("controller ok");
    this.products = testFactory.getCards();

    });
