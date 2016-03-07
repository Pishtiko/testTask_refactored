<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">


    <title>Test Task UI Page</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet">

    <!-- Custom styles  -->
    <link href="<c:url value="/pages/css/jumbotron-narrow.css" />" rel="stylesheet">
    <link href="<c:url value="/pages/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/pages/css/easyTableFilter.css" />" rel="stylesheet">

    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/Chart.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/easyTableFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/applogic.js"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>

    <![endif]-->
</head>

<body>

<div class="container">

    <div class="nav" style="background-color: 	#F7D358; margin-top: 20px;">


        <sec:authorize access="!isAuthenticated()">
            <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a>
            <p>&copy; Akvelon 2016</p></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">

            <a>Ваш логин: <sec:authentication property="principal.username"/> </a>
            <a><a class="btn btn-inverse" href="<c:url value="/logout" />" role="button">Выйти</a></a>
            <input type="text" id="search" name="search"/>
            <a><a class="btn btn-inverse search-button" role="button">Поиск по товарам</a></a>


        </sec:authorize>
    </div>

    <%--CHART--%>
<sec:authorize access="isAuthenticated()">

    <div class="user-panelka">
        <canvas id="visit-chart" width="600" height="400"></canvas>

        <a class="chart-buttons">
            <a class="btn glyphicon glyphicon-signal show-chart" role="button">спрятать/показать</a>
            <a class="btn glyphicon glyphicon-signal update-chart" role="button">статистика посещений</a>
        </a>
    </div>
</sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">

        <%--CREATE USER--%>


        <p><h1>Зарегистрировать Пользователя</h1></p>
        <div id="create-user">
            <input type="text" id="username" name="username"/>
            <input type="password" id="password" name="password"/>
            <div class="btn-group-vertical">
                <!-- Кнопка с выпадающим меню -->
                <div class="btn-group select-role">
                    <button type="button" data-toggle="dropdown" class="btn btn-info dropdown-toggle">
                        Укажите роль
                        <span class="caret"></span>
                    </button>
                    <!-- Выпадающее меню -->
                    <ul class="dropdown-menu">
                        <!-- Пункты меню -->
                        <li><a id="admin_role">Администратор</a></li>
                        <li><a id="customer_role">Клиент</a></li>
                        <li><a id="employee_role">Сотрудник</a></li>
                    </ul>
                </div>
            </div>
            <p>
                <a class="btn btn-success create-user-btn"  role="button">Создать</a>
                <a class="btn btn-success save-user-btn"  role="button">Сохранить</a>
            </p>

        </div>

        <div class="user-data">
            <a><a class="btn btn-danger get-my-orders-button" role="button">Мои Заказы</a></a>
            <a><a class="btn btn-danger get-my-cart" role="button">Моя Корзина</a></a>
            <a><a class="btn btn-danger get-products" role="button">Товары</a></a>
            <a><a class="btn btn-danger selection-test" role="button" onclick="proceedSelection(addToCart)">Добавить в корзину</a></a>
            <a><a class="btn btn-danger get-orders" role="button">Заказы</a></a>
        </div>
        <div class="user-details"> </div>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
    <h1>Akvelon Test Task</h1>
    <div id="spacer-side">
            <%--<a><a class="btn btn-danger save-details-button" role="button" onclick="updateItem()">Сохранить</a></a>--%>
            <%--<a><a class="btn btn-danger delete-details-button" role="button">Удалить</a></a>--%>
            <%--<a><a class="btn btn-danger new-details-button" role="button">Новый</a></a>--%>
        <div id="details">
            <div id="buttons">
                   <a><a class="btn btn-danger make-order-button" role="button">Заказать</a>
                    <%--<a><a class="btn btn-danger delete-button" role="button" onclick="proceedSelection(removeFromCart)">Удалить из корзины</a></a>--%>
                    <%--<a><a class="btn btn-danger update-button" role="button">Сохранить</a></a></div>--%>
                <table id="details-table" class="table table-hover">
                </table>
            </div>
        </div>
    <div id="some_table">
        <table id="data-table" class="table table-hover">
            <thead>
            </thead>
            <tbody>
            </tbody>
        </table>

    </div>

    <div class="footer">
        <p>&copy; Akvelon 2016</p>
    </div>

    </sec:authorize>
</div>
    </div>
</body>
</html>
