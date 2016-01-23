var rootURL = "http://localhost:8084/secure/service/";
var currentEntity;

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