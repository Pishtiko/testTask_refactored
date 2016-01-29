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

    <!-- Custom styles for this template -->
    <link href="<c:url value="/pages/css/jumbotron-narrow.css" />" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <script src="js/applogic.js"></script>
    <script src="js/jquery-2.2.0.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">

    <div class="jumbotron" style="margin-top: 20px;">
        <h1>Akvelon Test Task</h1>
        <p class="lead">AKVELON</p>
        <sec:authorize access="!isAuthenticated()">
            <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>Ваш логин: <sec:authentication property="principal.username"/> </p>
            <p>Ваш логин: <sec:authentication property="principal.password"/> </p>
            <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>
            <p><a class="btn btn-lg btn-danger2" href="<c:url value="/service/getProductListJSON/PRICE" />" role="button">Выйти</a></p>
            <input type="text" id="address" name="address"/>
            <table>
                <thead>
                <tr>
                    <th>Purcus</th>
                    <th>Hantis</th>
                    <th>Mastron</th>
                    <th>Jevicon</th>
                    <th>Lang</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Gitsome</td>
                    <td>Some one</td>
                    <td>Take mose</td>
                    <td>Likbes</td>
                    <td>Racounter</td>
                </tr>
                <tr>
                    <td>Linkage</td>
                    <td>Fordor</td>
                    <td>Miad ron me</td>
                    <td>Diablo core</td>
                    <td>Tidbade</td>
                </tr>
                <tr>
                    <td>Hicura</td>
                    <td>Warecom</td>
                    <td>Xamicon</td>
                    <td>Yamama</td>
                    <td>Udoricano</td>
                </tr>
                <tr>
                    <td>Lavistaro</td>
                    <td>Micanorta</td>
                    <td>Ebloconte ma</td>
                    <td>Quad ri port</td>
                    <td>Timesquer</td>
                </tr>
                </tbody>
            </table>

        </sec:authorize>
    </div>

    <div class="footer">
        <p>&copy; Akvelon 2015</p>
    </div>

</div>
</body>
</html>
