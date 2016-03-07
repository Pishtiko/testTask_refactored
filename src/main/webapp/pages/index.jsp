<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>

<html lang="ru" ng-app="app">
<head>
    <title>Тестовое задание Akvelon</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--<meta name="description" content="">--%>
    <meta name="author" content="">

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/pages/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- Custom styles  -->
    <link href="<c:url value="/pages/css/jumbotron-narrow.css" />" rel="stylesheet">
    <link href="<c:url value="/pages/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/pages/css/easyTableFilter.css" />" rel="stylesheet">
    <%--libraries--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/libs/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/libs/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/libs/lodash/lodash.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/libs/angular.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/libs/Chart.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/libs/easyTableFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/app-logic-ng.js"></script>
    <%--controllers--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/controllers/mainCtrl.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/controllers/testController.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/controllers/productCtrl.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/controllers/productsCtrl.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/controllers/ordersCtrl.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/controllers/cartCtrl.js"></script>


    <%--factories--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/factories/testFactory.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/factories/productFactory.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/factories/productsFactory.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/factories/cartFactory.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/factories/ordersFactory.js"></script>


    <%--derictives--%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/directives/productsDirective.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/directives/cartDirective.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/pages/js/directives/ordersDirective.js"></script>


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>

    <![endif]-->
</head>
<body ng-cloak
      ng-controller="mainCtrl as mainCtrl"
>
<%--top panel 'nav'--%>

<sec:authorize access="!isAuthenticated()">
    <div>
        <a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a>
        <h1>Akvelon Test Task</h1>
        <p>&copy; Akvelon 2016</p>
    </div>

</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <div id="top-panel">
        <a class="btn btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a>
            <%--<sec:authorize access="hasRole('ROLE_CUSTOMER')">--%>
        <div class="btn-group">
            <button type="button" class="btn btn-success" ng-click="mainCtrl.setPage('catalogue')">Каталог</button>
            <button type="button" class="btn btn-success" ng-click="mainCtrl.setPage('cart')">Корзина</button>
            <button type="button" class="btn btn-success" ng-click="mainCtrl.setPage('myOrders')">Мои заказы</button>
        </div>
        <a style="margin-right: auto"><h1>Ваш логин: <sec:authentication property="principal.username"/></h1></a>
    </div>
    <%--</sec:authorize>--%>

    <div class="container"
         id="root-container"
         style="background-color: #F7D358; margin-top: 20px;">

        <div ng-if="pagePart==='catalogue'">
            <div ng-controller="productsCtrl as productsCtrl" products-directive></div>
        </div>
        <div ng-if="pagePart==='cart'">
            <div cart-directive></div>
        </div>
        <div ng-if="pagePart==='myOrders'">
            <div ng-controller="ordersCtrl as ordersCtrl" orders-directive></div>
        </div>

    </div>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <div class="row chart">
        <section class="content">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p>
                        <a class="btn glyphicon glyphicon-signal " id="show-chart" role="button"
                           onclick="toggleChartButton()">скрыть
                            график</a>
                        <a class="btn glyphicon glyphicon-signal " id="update-chart" role="button"
                           onclick="updateStats()">показать
                            график посещений</a>
                        </p>
                        <canvas id="visit-chart" style=" display: inline-block; width: 100%"></canvas>
                    </div>
                </div>
            </div>
        </section>
    </div>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
</sec:authorize>
</body>
</html>
