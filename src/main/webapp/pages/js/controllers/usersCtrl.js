app.controller('usersCtrl', function ($scope, usersFactory) {

    this.ROLES = [
        CUSTOMER = {
            name: "Клиент",
            value: "ROLE_CUSTOMER"
        },
        EMPLOYEE = {
            name: "Сотрудник",
            value: "ROLE_EMPLOYEE"
        },
        ADMIN = {
            name: "Администратор",
            value: "ROLE_ADMIN"
        }
    ];

    console.log(this.ROLES);

    this.newUser = {
        login: "",
        password:""
    };
    this.confirmPassword = "";
    this.newUsersRole = "";

    this.getUsers = function () {
        return usersFactory.getUsers();
    };
    this.getRole = function (user) {
        return usersFactory.getRole(user);
    };
    this.createUser = function () {
        console.log(this.newUser);
        if(this.newUser.password == this.confirmPassword){
            usersFactory.createUser(this.newUser, this.newUsersRole)
                .success(function () {
                    usersFactory.push(this.newUser);
                    this.newUser = {};
                    this.newUsersRole = "";
                })
                .error(function (msg) {
                    console.log(msg);
                    alert("Ошибка");
                });
        }else {
            alert("пароли не совпадают");
        }
    };
    this.removeUser = function (user) {
        usersFactory.removeUser(user);
    };
});
