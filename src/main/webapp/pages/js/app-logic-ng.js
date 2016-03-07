var app = angular.module('app', []);
//GLOBAL CONSTANTS
var admin = "/admin";
var customer = "/main";
var employee = "/service";
//JQuery
$dataTable = $('#data-table');
$tableBody = $dataTable.find('>tbody');
$tableHead = $dataTable.find('>thead');


//GLOBAL VARs
var rootURL = "http://localhost:8084/secure";
var ord = "ASC";
var pop = "productName";
var userRole = "ROLE_CUSTOMER";
var visitChart;
var chartData = {
    labels : ["Январь","Февраль","Март","Апрель","Май","Июнь"],
    datasets : [
        {
            fillColor : "rgba(172,194,132,0.4)",
            strokeColor : "#ACC26D",
            pointColor : "#fff",
            pointStrokeColor : "#9DB86D",
            data : [200,155,100,250,305,250]
        }
    ]
};
var selected = [];
var entityType = "";
var detailsContext = $('#details');
var currentEntity;



$(document).ready(function(){

    //INIT / HELPERS

    init();
    //$('.edit-buttons').hide();


    //CHART

    $('#show-chart').hide();
    $('#visit-chart').hide();

    //BUTTONS

    //CUSTOMER
    $('.get-my-orders-button').click(function(){
        getMyOrders();
    });
    $('.make-order-button').click(function(){
        makeOrder();
    });
    $('#data-table tr').click(function(){
        $(this).toggleClass("alert-danger");
        //var selected = $(this).closest('tr').attr('id');
    });
    $('.get-orders').click(function(){
        getOrderList();
    });
    $('.get-my-cart').click(function(){
        previewCart();
    });

    //ADMIN

    $('.create-user-btn').click(function(){
        var username = $('#create-user').find($('#username')).val();
        var password = $('#create-user').find($('#password')).val();
        var user = {
            login: username,
            password: password
        };
        if(user.login!='' && user.password!='' && (userRole==="ROLE_ADMIN")||userRole==="ROLE_CUSTOMER"||userRole==="ROLE_EMPLOYEE") {
            createUser(userRole, user)
        }
        else {
            alert("wrong credentials")
        }

    });
    //$('.select-role li').click( function(){
    //    var arg = $(this);
    //    selectRole(arg);
    //});
    $('.save-user-btn').click(function(){
        var username = $('#create-user').find($('#username')).val();
        var password = $('#create-user').find($('#password')).val();
        var user = {
            login: username,
            password: password
        };
        if(user.login!='' && user.password!='' && (userRole==="ROLE_ADMIN")||userRole==="ROLE_CUSTOMER"||userRole==="ROLE_EMPLOYEE") {
            saveUser(userRole, user);
        }
        else {
            alert("wrong credentials");
        }
    });

});

//INIT
function init(){
    window.$dataTable = $('#data-table');
    window.$tableBody = window.$dataTable.find('>tbody');
    window.$tableHead = window.$dataTable.find('>thead');
    window.detailsContext = $('#details');
    window.rootURL = location.href;
    window.pop = "productName";
    window.selected = [];
    window.userRole = "ROLE_CUSTOMER";
}

// CHART
function initChart(){
    visitChart = document.getElementById('visit-chart').getContext('2d');
    chartData = {
        labels : [],
        datasets : [
            {
                fillColor : "rgba(172,194,132,0.4)",
                strokeColor : "#ACC26D",
                pointColor : "#fff",
                pointStrokeColor : "#9DB86D",
                data : []
            },
            {
                fillColor : "rgba(172,194,132,0.4)",
                strokeColor : "#088A08",
                pointColor : "#088A08",
                pointStrokeColor : "#9DB86D",
                data : []
            }
        ]
    }
}
function updateStats(){
    var URL = rootURL+"/stat/getStat";
    var URL2 = rootURL+"/stat/getStatUnique";
    initChart();
    ajaxGET2(URL, renderFirstLine);
    ajaxGET2(URL2, renderSecondLine);
    toggleChartButton();
}
function renderFirstLine(data) {
    renderStats(data, 0);
}
function renderSecondLine(data) {
    renderStats(data, 1);
}
function renderStats(data, datasetNumber) {
    updateChart(data, datasetNumber);
    $('#visit-chart').show();
    new Chart(visitChart).Line(chartData);
}
function updateChart(data,datasetNumber){
    $.each(data, function(index, object){
        if(datasetNumber===0)
            chartData.labels.push(object[0]);
        chartData.datasets[datasetNumber].data.push(object[1]);
    })

}
function toggleChartButton(){
    $('#visit-chart').hide();
    $('#update-chart').toggle();
    $('#show-chart').toggle();
}

// HELPERS
function ajaxGET(URL) {
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: function (data) {
            renderTable(data);
        }
    });
};
function ajaxGET2(URL, callback) {
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: function (data) {
            callback(data);
        }
    });

};
