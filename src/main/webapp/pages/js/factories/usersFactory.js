app.factory('usersFactory', ['$http', '$rootScope', function ($http) {
    var service ={};
    var URL = {
        GETALL: "admin/getUserList",
        GET: "admin/getUserById/",
        GET_ROLES: "admin/getRoles",
        PUT: "admin/createUser/",
        DELETE: "admin/deleteUser/"
    };
    var users = [];
    var roles = [];
    $http.get(URL.GETALL)
        .success(function(response){
            users = response;
        })
        .error(function(msg){
            console.log(msg);
        });
    $http.get(URL.GET_ROLES)
        .success(function(response){
            roles = response;
        })
        .error(function(msg){
            console.log(msg);
        });
    service.getUsers = function(){
        return users
    };
    service.getUsers = function(){
        return users
    };
    service.getRole = function(login){
        var result = _.filter(roles, {userName:login});
        console.log(JSON.stringify(result));
        return result.roleName;
    };
    service.addUser = function(user){
        $http.put(URL.PUT, user)
            .success(function () {
                users.push(user);
            })
            .error(function(msg){
                console.log(msg);
                alert("Ошибка");
            });
    };
    service.createUser = function (user, userRole) {
        return $http.post(URL.PUT+userRole, user);
    };
    service.push = function (user) {
        users.push(user);
    };
    service.pull = function(user){

    };
    service.removeUser = function(user){
      $http.delete(URL.DELETE+user.login)
          .success(function () {
              _.pull(users, user);
          })
          .error(function (msg) {
              console.log(msg);
              alert("Не удалось удалить учетную запись!");
          });
    };
    return service;
}]);