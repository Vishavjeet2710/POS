var rowCount;
var update=false;
function addOrderItem(json){
    console.log("Adding Inventory to Backend");
    $.ajax({
        url: '../api/orderitem',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log(response);
        },
        error: handleAjaxError
    });
    return false;
}

//Here I'm just fetching mrp and inventory and not pushing them
function addOrderItemDisplay(){
    console.log("Adding Inventory");
    var $form = $("#barcodeInput");
    if(update==true){
        var $form = $("#barcodeUpdateInput");
    }
    var json = toJson($form);
    $.ajax({
        url: '../api/inventory/getByBarcode',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log(response);
            getOrderItemList(response);
        },
        error: handleAjaxError
    });
    return false;
}

function addOrderItemList(list){
    var table = document.getElementById('orderItemTableAdd');
    if(table.rows.length==1){
        alert("Please Add some products first");
        return false;
    }
    for(var i =1,row;row = table.rows[i];i++){
         var col = row.cells;
         var jsonObj = {
            barcode: col[1].innerHTML,
            quantity: col[2].innerHTML,
            mrp: col[3].innerHTML,
            orderId: list.id
           }
        var json = JSON.stringify(jsonObj);
        console.log(json);
        addOrderItem(json);
    }
}

function checkExisting(){
    var barcodeCheck = $('#barcodeInput').val();
    var existing = document.getElementById('localID_'+barcodeCheck+'');
    if(existing == null){
        return 0;
    }
    return existing.cells.item(2).innerHTML;
}

function checkExistingForUpdate(){
    var barcodeCheck = $('#barcodeUpdateInput').val();
    var existing = document.getElementById('localID_'+barcodeCheck+'');
    if(existing == null){
        return 0;
    }
    return existing.cells.item(2).innerHTML;
}

function setListQuantityZero(){
    var barcodeCheck = $('#barcodeUpdateInput').val();
    var existing = document.getElementById('localID_'+barcodeCheck+'');
    existing.cells.item(2).innerHTML = 0;
}


function editOrderItemListModal(requiredQuantity){
    var barcodeCheck =update ? $('#barcodeUpdateInput').val() : $('#barcodeInput').val();
    var existing = document.getElementById('localID_'+barcodeCheck+'');
    existing.cells.item(2).innerHTML = parseInt(requiredQuantity);
}

function updateOrderItemModal(){
    update=true;
    console.log("Updating");
    addOrderItemDisplay();
}

function getOrderItemList(list){
    if(update==true){
        var existingQuantityForUpdate=checkExistingForUpdate();
        if(existingQuantityForUpdate<=0){
            alert("Product with given barcode is not in the list");
            update=false;
            return false;
        }
        var requiredQuantityForUpdate = parseInt(document.getElementById('quantityUpdateInput').value);
        var inventoryQuantityForUpdate = list.quantity;
        if(requiredQuantityForUpdate>inventoryQuantityForUpdate){
            alert("Not Enough Quantity in Inventory");
            update=false;
            return false;
        }
        setListQuantityZero();
        editOrderItemListModal(requiredQuantityForUpdate);
        update = false;
        return false;
    }

    var existingQuantity = parseInt(checkExisting());
    var requiredQuantity = parseInt(document.getElementById('quantityInput').value);
    requiredQuantity=requiredQuantity + existingQuantity;
    var inventoryQuantity = list.quantity;
    console.log("Existing Quantity" + existingQuantity);
    console.log("New Required Quantity" + requiredQuantity);
    console.log("Quantity in Inventory" + inventoryQuantity);
    if(requiredQuantity>inventoryQuantity){
        alert("Not Enough Quantity in Inventory");
        return false;
    }
    if(existingQuantity>0){
        console.log("There was already some existing Quantity" + existingQuantity);
        editOrderItemListModal(requiredQuantity);
        return false;
    }
    displayOrderItemListModal(list);
}

function resetOrderItemTable(){
    console.log("Resetting Table");
    rowCount=0;
    var $tbody=$('#orderItemTableAdd').find('tbody');
    $tbody.empty();
}


// Compatibility standard works on Google chrome mozilla firefox, not on Internet Explorer
function deleteOrderItemDisplay(barcode){
    document.getElementById("localID_"+barcode).remove();
}

function editOrderItemDisplay(barcode){
    var ele = document.getElementById('localID_'+barcode+'');
    document.getElementById("barcodeUpdateInput").value = barcode;
    $('#collapseOne').collapse('show');
}

function displayOrderItemListModal(list){
    ++rowCount
    console.log("Printing Order");
    var $tbody=$('#orderItemTableAdd').find('tbody');
    var b = list;
    var row ='<tr id="localID_'+b.barcode+'">'
    +'<th scope="row" name="id">'+rowCount+'</th>'
    +'<td name="barcode">'+b.barcode+'</td>'
    +'<td name="quantity">'+$('#quantityInput').val()+'</td>'
    +'<td name="mrp">'+b.mrp+'</td>'
    +'<td><button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="editOrderItemDisplay(\'' + b.barcode + '\')">Edit</button> | <button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="deleteOrderItemDisplay(\'' + b.barcode + '\')">Delete</button></td>'
    +'</tr>';
    console.log(row);
    $tbody.append(row);
}

function createOrder(){
    console.log("Creating Order");
    var $form = $("#addOrder");
    var json = toJson($form);
    $.ajax({
        url: '../api/order',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Order Created");
            $('#createOrderModal').modal('toggle');
            addOrderItemList(response);
            getOrderList();
        },
        error: handleAjaxError
    });
    return false;
}

function resetOrderTableMain(){
    var $tbody=$('#orderTableMain').find('tbody');
    $tbody.empty();
}

function getOrderList(){
    resetOrderTableMain();
    $.ajax({
        url: '../api/order',
        type: 'GET',
        success: function(response){
            console.log("Order Item list fetched");
            getOrder(response);
        },
        error: handleAjaxError
    });
    return false;
}

function getOrder(list){
    for(e in list){
        console.log(list[e].id);
        getOrderId(list[e].id);
    }
}

function getOrderId(id){
    $.ajax({
        url: '../api/order/'+id+'',
        type: 'GET',
        success: function(response){
            console.log("Order Item id fetched");
            displayOrderItem(response);
        },
        error: handleAjaxError
    });
    return false;
}

function displayOrderItem(list){
    console.log("Printing Order");
    var $tbody=$('#orderTableMainBody');
    var innerTable = '<tr><td colspan="4"><div class="row">&nbsp;<div class="col">Order ID :'+list[0].orderId+'</div><button type="button" class="btn btn-secondary float-right btn-sm mb-2" name="action" onclick="downloadInvoice('+list[0].orderId+')">Download</button>&nbsp;&nbsp;&nbsp;</div><table class="table" id="orderTable"><thead class="table-dark"><tr><th scope="col" name="id">#</th><th scope="col" name="barcode">Barcode</th><th scope="col" name="quantity">Quantity</th><th scope="col" name="mrp">Mrp</th></tr></thead><tbody>';
    for(i in list){
        var b = list[i];
        var row ='<tr>'
        +'<th scope="row">'+i+'</th>'
        +'<td>'+b.barcode+'</td>'
        +'<td>'+b.quantity+'</td>'
        +'<td>'+b.mrp+'</td>'
        +'</tr>';
        innerTable=innerTable+row;
    }
    innerTable = innerTable+'</tbody></table></td></tr>';
    $tbody.append(innerTable);
}


// Upload functions
var fileData = [];
var errorData = [];
var processCount = 0;

function readFileDataCallback(results){
    console.log("CallBack Data");
    fileData = results.data;
    uploadRows();
}


function downloadErrors(){
    writeFileData();
}

function updateUploadDialog(){
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount);
    $('#errorCount').html("" + errorData.length);
}

function displayUploadData(){
    resetUploadDialog();
}

// Download function

function downloadInvoice(orderId){
    console.log("Downloading");

    url='../api/order/download/'+orderId+'';
    $.ajax(
    {
    url: url,
    xhrFields:
    {
        responseType: 'blob'
    },
    success: function(data)
    {
        console.log(data);
        var blob = new Blob([data]);
        var filename = "orderList.pdf";
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = "orderList.pdf";
        link.click();
        var file = new File([blob], filename, { type: 'application/force-download' });
        window.open(URL.createObjectURL(file));
    },
    error:function(respone){
        console.log(respone);
    }
    });
}


function init(){
    console.log("initialized");
    $('#resetOrderItemTable').click(resetOrderItemTable);
    $('#addOrderItem').click(addOrderItemDisplay);
    $('#updateOrderItemModal').click(updateOrderItemModal);
    // $('#downloadButton').click(downloadList);
    $('#createOrder').click(createOrder);
}

$(document).ready(init)
$(document).ready(getOrderList)
$('#myModal').on('hidden.bs.modal', function() {
    $('.collapse').collapse('hide');
  });
  $('#myModal').on('shown.bs.modal', function() {
    $('#collapseOne').collapse('show');
  });
  
  
  
  $('#accordion').find('.panel-default:has(".in")').addClass('panel-primary');
  
  $('#accordion').on('show.bs.collapse', function(e) {
    $(e.target).closest('.panel-default').addClass(' panel-primary');
    $('.collapse').collapse('hide');
  }).on('hide.bs.collapse', function(e) {
    $(e.target).closest('.panel-default').removeClass(' panel-primary');
  })
  