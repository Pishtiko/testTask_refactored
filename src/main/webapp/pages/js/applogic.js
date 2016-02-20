var rootURL = "http://localhost:8084/secure/";
var admin = "/admin";
var customer = "/main";
var employee = "/service"
var currentEntity;
$dataTable = $('#data-table table');

$(document).ready(function(){
    $('#data-table tr').click(function(){

            $(this).toggleClass("alert-danger");

    });
});

//  HELPER METHODS

function renderTable(data, textstatus){
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $dataTable.remove();
    $.each(data, function(index, object) {
        $dataTable.append('<tr><a href="#" data-identity="' + object.product.productId +'">' + object.product.productName + '</a></tr>');
    });
    console.log('getting data succeed: ' + data);
    //dataTable.append()
    currentEntity = data.product;
    renderDetails(currentEntity);
}   //TODO: CHECK FOR ISSUES
function renderData(data, textstatus){
    $('#btnDelete').show();
    console.log('findById success: ' + data.product.productName);

    currentEntity = data.product;
    renderDetails(currentEntity);
}   //TODO: CHECK FOR ISSUES
function ajaxGET(URL){
    console.log(self);
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: renderData(data, textstatus)
    });
}                   //TODO: CHECK FOR ISSUES
function ajaxPOST(URL){
    console.log(self);
    $.ajax({
        type: 'GET',
        url: URL,
        dataType: "json",
        success: renderData(data, textstatus)
    });
}                  //TODO: CHECK FOR ISSUES
function ajaxPUT(URL, object){
    console.log(self);
    $.ajax({
        type: 'PUT',
        url: URL,
        dataType: "json",
        //success: renderData(data, textstatus),
        data: object,
        contentType: Array
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
function createUser(userRole){
    var URL = rootURL+admin+"/createUser/"+userRole;
    ajaxPOST(URL);
}
function deleteUser(userName){
    var URL = rootURL+admin+"/deleteUser/"+userName;
    ajaxDELETE(URL);
}

//  USER PAGE LOGIC

function findAllp(pop){
var URL = rootURL+customer+"/getProductList/"+pop;
    ajaxGET(URL);
}
function getProductList(pop){
    var URL = rootURL+customer+"/getProductList/"+pop;
    ajaxGET(URL);
}
function searchProduct(searchKey, pop){
    var URL = rootURL+customer+"/findProduct/"+searchKey+"/"+pop;
    ajaxGET(URL);
}
function getMyOrders(){
    var URL = rootURL+customer+"/getMyOrders";
    ajaxGET(URL);
}
function previewCart(){
    var URL = rootURL+customer+"/getCartContent";
    ajaxGET(URL);
}
function makeOrder(){
    var URL = rootURL+customer+"/makeOrder";
    ajaxPOST(URL);
}
function cleanTheCart(){
    var URL = rootURL+customer+"/cleanTheCart";
    ajaxPOST(URL);
}
function addToCart(productId, count){
    var URL = rootURL+customer+"/adToCart/"+productId+"/"+count;
    ajaxPOST(URL);
}
function removeFromCart(productId){
    var URL = rootURL+customer+"/removeFromCart/"+"/"+productId
}

//  EMPLOYEE LOGIC

function getProductList(){
    var URL = rootURL+customer+"/getProductList";
    ajaxGET(URL);
}
function getOrderList(){
    var URL = rootURL+employee+"/getOrderList";
    ajaxGET(URL);
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


//TODO:

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

function findById(self) {
    console.log(self);
    $.ajax({
        type: 'GET',
        url: rootURL + "getProductById/"+self,
        dataType: "json",
        success: function(data, textstatus) {
            $('#btnDelete').show();
            console.log('findById success: ' + data.product.productName);

            currentEntity = data.product;
            renderDetails(currentEntity);
        }
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

function updateHotel() {
    console.log('updateHotel');
    $.ajax({
        type: 'PATCH',
        contentType: 'application/json',
        url: rootURL + '/' + $('#hotelId').val(),
        dataType: "html",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR) {
            alert('Hotel updated successfully');
            findAll();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('updateHotel error: ' + textStatus);
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

function renderDetails(currentEntity) {
    if(currentEntity.ProductName === undefined) {
        $('#hotelId').val(currentEntity.ProductId);
    } else {
        var id = currentEntity.ProductId;
        var idx = id.lastIndexOf("/");
        id = id.substring(idx+1);
        $('#hotelId').val(id);
    }
    $('#name').val(currentEntity.productName);
    console.log("!undefined "+currentEntity.productName);
    $('#address').val(currentEntity.address);
    $('#city').val(currentEntity.city);
    $('#zip').val(currentEntity.zip);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    return JSON.stringify({
        "address": $('#address').val(),
        "city": $('#city').val(),
        "name": $('#name').val(),
        "zip": $('#zip').val(),
    });
}