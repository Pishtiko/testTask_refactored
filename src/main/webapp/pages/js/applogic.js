//GLOBAL CONSTANTS


var admin = "/admin";
var customer = "/main";
var employee = "/service";

$dataTable = $('#data-table');
$tableBody = $('#data-table').find('>tbody');
$tableHead = $('#data-table').find('>thead');

$(document).ready(function(){

    //INIT / HELPERS


    $('.make-order-button').hide();
    $('.delete-button').hide();
    $('#generate-hash').click( function() {
        var t = new Date();
        hash = Sha256.hash($('#message').val());
        $('#hash-time').html(((new Date() - t))+'ms');
        $('#hash').val(hash);
    });
    init();
    $('.make-order-button').click(function(){
        $(this).hide();
    });

    //CHART

    $('.show-chart').hide();
    $('.show-chart').click(function(){
        $('#visit-chart').toggle();
    });
    $('.update-chart').click(function(){
        updateStats();
        $('.show-chart').show();
    });
    $('#visit-chart').hide();

    //BUTTONS
    //  -CUSTOMER
    $('.get-products').click(function(){getTest()});
    $('.search-button').click(function(){
        if ($('#search').val()!==undefined)
            searchProduct($('#search').val());
    });
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

    userRole = "ROLE_CUSTOMER";
    $('.select-role li').click( function(){
        var arg = $(this);
        selectRole(arg);
        console.log(this);
        console.log($(this));
    });
    $('.create-user-btn').click(function(){
        var username = $('#create-user').find($('#username')).val();
        var password = $('#create-user').find($('#password')).val();
        console.log(username, password);
        var user = {
            login: username,
            password: password
        }
        if(user.login!='' && user.password!='' && (userRole==="ROLE_ADMIN")||userRole==="ROLE_CUSTOMER"||userRole==="ROLE_EMPLOYEE") {
            createUser(userRole, user)
        }
        else {
            alert("wrong credentials")
        }

    });
    $('.save-user-btn').click(function(){
        var username = $('#create-user').find($('#username')).val();
        var password = $('#create-user').find($('#password')).val();
        console.log(username, password);
        var user = {
            login: username,
            password: password
        }
        if(user.login!='' && user.password!='' && (userRole==="ROLE_ADMIN")||userRole==="ROLE_CUSTOMER"||userRole==="ROLE_EMPLOYEE") {
           saveUser(userRole, user);
        }
        else {
            alert("wrong credentials");
        }

    });

    $('#sub').on('click', function() {
        // График
        updateChart();
        var buyers = document.getElementById('chartC').getContext('2d');
        var buyerData = {
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
        }
        for(var i = 0; i < 6; i++) {
            buyerData.datasets[0].data[i] = (Math.random()*250)+1|0;
        }
        new Chart(buyers).Line(buyerData);
        // Круговая диаграмма
        for(var j = 0; j < 4; j++) {
            pieData[j].value = (Math.random()*100)+1|0;
        }
        new Chart(countries).Pie(pieData, pieOptions);
        // Гистограмма
        for(var k = 0; k < 6; k++) {
            barData.datasets[0].data[k] = (Math.random()*250)+1|0;
            barData.datasets[1].data[k] = (Math.random()*250)+1|0;
        }
        new Chart(income).Bar(barData);
    });

});

//GLOBAL VARs
var rootURL = "http://localhost:8084/secure";
var ord = "ASC";
var pop = "productName";
var userRole = "ROLE_CUSTOMER";
var visitChart = document.getElementById('visit-chart').getContext('2d');
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
}
var selected = [];
var entityType = "";
var detailsContext = $('#details');
var currentEntity;

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



//  HELPER FUNCs

//JQuery variables initialization on document loaded
function init(){
    window.$dataTable = $('#data-table');
    window.$tableBody = $('#data-table').find('>tbody');
    window.$tableHead = $('#data-table').find('>thead');
    window.detailsContext = $('#details');
    window.rootURL = location.href;
    window.pop = "productName";
    window.selected = [];

}
function proceedSelection(callback){
    console.log(selected);
    //var sel = $('.alert-danger tr');
    $.each(selected, function(index, obj){
        console.log(obj);
        var i = 1;
        if(i===1){
            for(var key in obj){
                callback(obj[key],1);
            }

        }

    })

}
function selectFromTable(context){
    currentEntity = {};
    var kkey = context.attr('id');
    var vval =  context.find('>td:first').html();
    var tdArray = context.find('td');
    var thArray = $tableHead.find('th');


    for (var index = 0; index < tdArray.length; index++){
        //console.log(thArray.eq(index).text());
        currentEntity[thArray.eq(index).text()] = tdArray.eq(index).text();
    }
    //console.log(currentEntity.status);
    //console.log(JSON.stringify(currentEntity));
    index = kkey;
    if (kkey!==undefined)
    {
        context.toggleClass("alert-danger");
        //console.log(index);
    }
    if (context.hasClass('alert-danger')) {
        selected.push({
            key: kkey,
            value: vval
        })
    }
    else {
        selected.push({
            key: ind,
            value: 0
        })
    }
    renderDetails(detailsContext, currentEntity);
}
function selectRole(arg){
    var selected = arg.find('a').attr('id');
    var param = $('.dropdown-toggle');
    var dropdown = arg.parent().siblings(param);
    if (selected === 'admin_role') {
        userRole = "ROLE_ADMIN";
        console.log(dropdown.html());
        dropdown.html("Администратор"+"<span class='caret'></span>");
    } else if (selected === 'customer_role') {
        userRole = "ROLE_CUSTOMER";
        dropdown.html("Клиент"+"<span class='caret'></span>");
    } else if (selected === 'employee_role') {
        userRole = "ROLE_EMPLOYEE";
        dropdown.html("Сотрудник"+"<span class='caret'></span>");
    }
    console.log(selected);
}

//TEST
function getTest(){
    //console.log(self);
    var URL = rootURL+"/main/getProductList/price";
    ajaxGET(URL);

}

//Render Data

function renderTableHeader(item){
    $tableHead.html("");
    $tableHead.append("<tr></tr>");
    var i =1;
    for(var key in item){
        if(i===1){
            if (key === "idd")
                entityType = "OrderEntity";
            else if (key === "orderId")
                entityType = "OrderProduct";

        }
        $('#data-table').find('> thead > tr:last').append("<th>"+key+"</th>");
        if(i===1)
        i--;
    }
}
function renderItemToTable(item){
    renderItem(item, $dataTable, "table");

}
function renderItem(item, container, containerType){
    var prefix;
    var suffix;
    var atr;
    var innerPrefix;
    var innerSuffix;
    if (containerType === "table"){
        prefix = "<tr id="
        suffix = "></tr>";
        atr = '> tbody > tr:last';
        innerPrefix = "<td>";
        innerSuffix = "</td>";
    }
    if (containerType === "editable"){
        prefix = "<li id="
        suffix = "/>";
        atr = ' > li:last';
    //<input type="text" id="search" name="search"/>
        innerPrefix = "<input type=\"text\" id=\"editable\" name=\"";
        innerSuffix = "/\">";
    }
    console.log(item);
    var i = 1;
    for(var key in item) {
        if (i<1){
            break;
        }
        container.append(prefix + key + suffix);
        i--;
    }

    for(key in item){
        if(item[key] instanceof Object ){
            var j =1;
            var obj = item[key];
            for (var k in obj){
                if(j==1)
                container.find(atr).append(innerPrefix+obj[k]+innerSuffix);
                j--;
            }
        }
        else if(key === "createDate" ){
            //if(containerType!=="editable"){
            var obb = 0;
            obb = item[key];
            console.log(obb);
            var ob = new Date(obb);
            obj = ob.getDate()+","+ob.getMonth()+","+ob.getFullYear();
            if(containerType!=="editable")
            container.find(atr).append(innerPrefix+obj+innerSuffix);
            }

        else {
            if(containerType==="editable"){
                container.find(atr).append(innerPrefix+key+innerSuffix+key+"</input>");
                container.find("input:last").val(item[key]);
                container.find(">input:last");
            }
            else {
            container.find(atr).append(innerPrefix+item[key]+innerSuffix);
            }
        }
    }
}
function renderTable(data) {

    $tableBody.html("");
    console.log(window.$tableBody.html());
    renderTableHeader(data[0]);
    for (var index = 0; index < data.length; index++) {
        renderItemToTable(data[index]);
        console.log(data[index]);
    }
    $('#data-table tr').click(function () {
        selectFromTable($(this));
    });
    $('.selection-test').click(function () {
        proceedSelection();
    });
}
function renderDetails(context, currentEntity) {
    console.log(currentEntity);
    for (var key in currentEntity){
        context.html("");
        renderItem(currentEntity,context,"editable");
    }
       /* if(currentEntity.productName === undefined) {
            console.log("reached 2?");
            if (currentEntity.idd!==undefined && currentEntity.orderId ===undefined)
                if(currentEntity.idd!==undefined){
                    console.log("reached>3?");
                    renderItem(data,context,"editable");
                    //renderItem(currentEntity, context, "editable");
                }
        }*/
}

function ajaxGET(URL){
    //console.log(self);
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: function(data){renderTable(data);}
    });
}                   //TODO: CHECK FOR ISSUES
function ajaxGET2(URL, callback){
    //console.log(self);
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: function(data){callback(data);}
    });
}
function ajaxGET3(URL){
    //console.log(self);
    var returnable = [];
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: function(data){returnable = data;}
    });
    return returnable;
}
function ajaxPOST(URL){
    console.log(self);
    $.ajax({
        type: 'POST',
        url: URL,
        dataType: "json",
        success: function(){
            alert("усё ok ");
        }
    });
}                  //TODO: CHECK FOR ISSUES
function ajaxPUT(URL, object){
    $.ajax({
        type: 'POST',
        url: URL,
        //dataType: "JSON",
        success: console.log("ok"),
            //renderData(data, textstatus),
        data: JSON.stringify(object),
        contentType: 'application/json'
    });
}             //TODO: CHECK FOR ISSUES
function ajaxDELETE(URL){
    console.log(self);
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: renderData(data, textstatus)
    });
}               //TODO: CHECK FOR ISSUES

//ADMIN LOGIC

function getUserList(){
    var URL = rootURL+admin+"/getUserList";
    ajaxGET(URL);
}
function createUser(userRole, user){
    var URL = rootURL+admin+"/createUser/"+userRole;
    ajaxPUT(URL, user);
}
function saveUser(userRole, user){
    var URL = rootURL+admin+"/updateUser/"+userRole;
    ajaxPUT(URL, user);
}
function deleteUser(userName){
    var URL = rootURL+admin+"/deleteUser/"+userName;
    ajaxDELETE(URL);
}

//  USER PAGE LOGIC


function getProductList(){
    var URL = rootURL+customer+"/getProductList/"+pop;
    ajaxGET(URL);
}
function searchProduct(searchKey){
    var URL = rootURL+customer+"/findProduct/"+searchKey+"/"+ord;
    ajaxGET(URL);
}
function getMyOrders(){
    var URL = rootURL+customer+"/getMyOrders";
    ajaxGET(URL);
}
function previewCart(){
    var URL = rootURL+customer+"/getCartContent";
    ajaxGET(URL);
    $('.make-order-button').show();
    $('.delete-button').show();
}
function makeOrder(){
    var URL = rootURL+customer+"/makeOrder";
    ajaxGET(URL);
}
function cleanTheCart(){
    var URL = rootURL+customer+"/cleanTheCart";
    ajaxPOST(URL);
}
function addToCart(productId, count){
    var URL = rootURL+customer+"/addToCart/"+productId+"/"+count;
    ajaxPOST(URL);
}
function removeFromCart(productId){
    var URL = rootURL+customer+"/removeFromCart/"+"/"+productId
}

//  EMPLOYEE LOGIC

function getProductList(){
    var URL = rootURL+customer+"/getProductList/"+pop;
    ajaxGET(URL);
}

function getOrderList(){
    var URL = rootURL+employee+"/getOrderList";
    ajaxGET(URL);
}
function getOrderById(orderId, callback){
    console.log("reached ajax");
    var URL = rootURL+employee+"/getOrderById/"+orderId;
    ajaxGET3(URL);
    return ajaxGET3(URL);
}
function getOrdersOfCustomer(id){
    var URL = rootURL+employee+"/getOrdersOfCustomer/"+id;
    ajaxGET(URL);
}
function updateOrder(orderId, object){
    var URL = rootURL+employee+"/updateOrder/"+orderId;
    ajaxPUT(URL, object);
}
function cancelOrder(orderId){
    var URL = rootURL+employee+"/cancelOrder/"+orderId;
    ajaxGET(URL);
}
function confirmOrder(orderId){
    var URL = rootURL+employee+"/confirmOrder/"+orderId;
    ajaxPOST(URL);
}




function findAll() {

    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: "http://localhost:8083/TestTaskWS/service/getProductListJSON/PRICE",
        dataType: "json", // data type of response
        success: renderList
    });
}

function renderList(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    $('#hotelList li').remove();
    $.each(data, function(index, object) {
        $('#hotelList').append('<li><a href="#" data-identity="' + object.product.productId +'">' + object.product.productName + '</a></li>');
    });
}

function findByName(searchKey) {
    console.log('findByName: ' + searchKey);
    $.ajax({
        type: 'GET',
        url: rootURL + '/search/findByNameContaining?name=' + searchKey,
        dataType: "json",
        success: renderList
    });
}



function addHotel() {
    console.log(formToJSON());
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR) {
            alert('Hotel created successfully');
            $('#btnDelete').show();
            //$('#hotelId').val(data.id);
            findAll();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status);
            // Fix a bug in JQuery: 201 means the insert succeeded!
            if (jqXHR.status === 201) {
                alert('Hotel created successfully');
                $('#btnDelete').show();
                //$('#hotelId').val(data.id);
                findAll();
            }
            else {
                alert('addHotel error: ' + textStatus);
            }
        }
    });
}


function updateItem(URL) {
    console.log('updateHotel');
    $.ajax({
        type: 'PATCH',
        contentType: 'application/json',
        url: URL + detailsContext.find('>input:first').val(),
        dataType: "html",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR) {
            alert('Updated successfully');
            //findAll();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('update error: ' + textStatus);
        }
    });

}

function deleteHotel() {
    console.log('deleteHotel');
    $.ajax({
        type: 'DELETE',
        url: rootURL + '/' + $('#hotelId').val(),
        success: function(data, textStatus, jqXHR) {
            alert('Hotel deleted successfully');
            findAll();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('deleteHotel error');
        }
    });
}



// Helper function to serialize all the form fields into a JSON string
function formToJSON(formDivPointer) {
    var output = new Object;
    var arr = formDivPointer.find($('li'));
    for (var key in arr){
        output[arr[key].attr('id').val()] = arr[key].html();
    }

    return JSON.stringify(output);
}

// TRASH
//function renderProductList(data){
//
//function findById(self) {
//    console.log(self);
//    $.ajax({
//        type: 'GET',
//        url: rootURL + "getProductById/"+self,
//        dataType: "json",
//        success: function(data, textstatus) {
//            $('#btnDelete').show();
//            console.log('findById success: ' + data.product.productName);
//
//            currentEntity = data.product;
//            renderDetails(currentEntity);
//        }
//    });
//}
//    $.each(data, function(index, object) {
//        $('#data-table').find('>tbody').append('<tr id ="'+object.productId+'"></tr>');
//        var output = "";
//        output+="<td>";
//        output+=object.productId;
//        output+="</td>";
//        output+="<td>";
//        output+=object.productName;
//        output+="</td>";
//
//        $('#data-table').find('> tbody > tr:last').append(output);
//    });
//
//}
//var id = currentEntity.ProductId;
//var idx = id.lastIndexOf("/");
//id = id.substring(idx+1);
//console.log(item[key]);
//console.log(key);
//console.log($tableBody);
//console.log($tableBody.val());
//console.log($tableBody.html());