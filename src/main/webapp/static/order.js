var rowCount;
var update=false;
var upload=false;
var updateBarcode=0;
var exQuantity=0;
function addOrderItem(json){
    console.log("Adding Inventory to Backend");
    $.ajax({
        url: '../api/order/single',
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
    var json;    
    if(update==true){
        var $form = {};
        $form.barcode=updateBarcode;
        json = JSON.stringify($form);
    }else{
        json = toJson($form);
    }
    console.log(json);
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
function checkExistingForUpload(){
    var existing = document.getElementById('localID_'+fileData[processCount-1].barcode+'');
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
    var barcodeCheck =update ? $('#barcodeUpdateInput').val() : upload ? fileData[processCount-1].barcode : $('#barcodeInput').val();
    var existing = document.getElementById('localID_'+barcodeCheck+'');
    existing.cells.item(2).innerHTML = parseInt(requiredQuantity);
}

function editOrderItemListModalForUpdate(requiredQuantity){
    var existing = document.getElementById('localID_'+updateBarcode+'');
    existing.cells.item(2).innerHTML = parseInt(requiredQuantity);
    existing.cells.item(4).innerHTML = '<button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="editOrderItemDisplay(\'' + updateBarcode + '\')">Edit</button> | <button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="deleteOrderItemDisplay(\'' + updateBarcode + '\')">Delete</button>';
}

function updateOrderItemModal(barcode){
    update=true;
    updateBarcode=barcode;
    console.log("Updating");
    addOrderItemDisplay();
}

function getOrderItemList(list){
    if(update==true){
        // var existingQuantityForUpdate=checkExistingForUpdate();
        // if(existingQuantityForUpdate<=0){
        //     alert("Product with given barcode is not in the list");
        //     update=false;
        //     return false;
        // }
        var requiredQuantityForUpdate = parseInt(document.getElementById('quantityUpdateInput').value);
        var inventoryQuantityForUpdate = list.quantity;
        if(requiredQuantityForUpdate>inventoryQuantityForUpdate){
            alert("Not Enough Quantity in Inventory");
            console.log(exQuantity);
            editOrderItemListModalForUpdate(exQuantity);
            update=false;
            return false;
        }
        editOrderItemListModalForUpdate(requiredQuantityForUpdate);
        update = false;
        return false;
    }if(upload==true){
        var existingQuantityForUpload=checkExistingForUpload();
        var requiredQuantityForUpload = parseInt(fileData[processCount-1].quantity)+parseInt(existingQuantityForUpload);
        var inventoryQuantityForUpload = list.quantity;
        if(requiredQuantityForUpload>inventoryQuantityForUpload){
            var row = file[processCount-1];
            row.error = "Not Enough Quantity in Inventory";
            errorData.push(row);
            return false;
        }
        if(existingQuantityForUpload>0){
            editOrderItemListModal(requiredQuantityForUpload);
            return false;
        }
        displayOrderItemListModal(list);
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
    exQuantity = ele.cells.item(2).innerHTML;
    console.log(exQuantity);
    ele.cells.item(2).innerHTML = '<form id="orderItemForm"><div class="form-row"><div class="col-12"><input type="text" class="form-control " name="quantity" placeholder="Quantity" id="quantityUpdateInput"></div></div></form>';
    ele.cells.item(4).innerHTML = '<button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="updateOrderItemModal(\'' + barcode + '\')">Update</button> | <button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="deleteOrderItemDisplay(\'' + barcode + '\')">Delete</button>';
}

function displayOrderItemListModal(list){
    ++rowCount
    console.log("Printing Order");
    var $tbody=$('#orderItemTableAdd').find('tbody');
    var b = list;
    var quantityHTML = upload ? fileData[processCount-1].quantity : $('#quantityInput').val();
    var rowHTML ='<tr id="localID_'+b.barcode+'">'
    +'<th scope="row" name="id">'+rowCount+'</th>'
    +'<td name="barcode">'+b.barcode+'</td>'
    +'<td name="quantity">'+ quantityHTML +'</td>'
    +'<td name="mrp">'+b.mrp+'</td>'
    +'<td><button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="editOrderItemDisplay(\'' + b.barcode + '\')">Edit</button> | <button type="button" class="btn btn-secondary mb-2 btn-sm" onclick="deleteOrderItemDisplay(\'' + b.barcode + '\')">Delete</button></td>'
    +'</tr>';
    console.log(rowHTML);
    console.log(typeof(rowHTML));
    $tbody.append(rowHTML);
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
        console.log(list[e]);
        displayOrderItem(list[e]);
    }
}

function displayOrderItem(list){
    console.log("Printing Order");
    var $tbody=$('#orderTableMainBody');
    console.log(list);
    var innerTable = '<tr><td colspan="4"><div class="row">&nbsp;<div class="col">Order ID :'+list[0].orderId+'</div><button type="button" class="btn btn-secondary float-right btn-sm mb-2" name="action" onclick="downloadInvoice('+list[0].orderId+')">Download</button>&nbsp;&nbsp;&nbsp;</div><table class="table" id="orderTable"><thead class="table-dark"><tr><th scope="col" name="id">Serial No.</th><th scope="col" name="barcode">Barcode</th><th scope="col" name="quantity">Quantity</th><th scope="col" name="mrp">Mrp</th></tr></thead><tbody>';
    for(i in list){
        var b = list[i];
        var row ='<tr>'
        +'<th scope="row">'+ String(parseInt(i)+1)+'</th>'
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

function processOrder(){
    upload=true;
    var file = $('#orderFile')[0].files[0];
    console.log("Processing Data");
    readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
    console.log("CallBack Data");
    fileData = results.data;
    uploadRows();
}

function uploadRows(){
    // Update Progress
    updateUploadDialog();
    console.log("Making an Ajax call");
    console.log(processCount);
    // If every thing processed than return 
    if(processCount==fileData.length){
        upload=false;
        return;
    }
    // Process next row
    var row = fileData[processCount];
    processCount++;
    var data = {barcode : row.barcode};
    var json = JSON.stringify(data);
    $.ajax({
        url: '../api/inventory/getByBarcode',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log(response);
            getOrderItemList(response);
            uploadRows();
        },
        error: function(response){
            var ele=JSON.parse(response.responseText);
            row.error=ele.message;
	   		errorData.push(row);
	   		uploadRows();
        }
    });
}

function updateFileName(){
    document.querySelector("#orderFileName").textContent = this.files[0].name;
}

function downloadErrors(){
    writeFileData(errorData);
}

function resetUploadDialog(){
    var $file = $('#orderFile');
    $file.val('');
    $('#orderFileName').html("Choose File");
    console.log("Reseting");
    // Reset various counts
    processCount=0;
    fileData = [];
    errorData = [];
    // Update Counts
    updateUploadDialog();
}

function updateUploadDialog(){
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount);
    $('#errorCount').html("" + errorData.length);
}

function displayUploadData(){
    resetUploadDialog();
}
function displayUploadData(){
    resetUploadDialog();
}

// Download function

function downloadSaleReport(){
    console.log("Downloading");
    var $form = $("#saleReportForm");
    var json=toJson($form);
    url='../api/order/download/';
    $.ajax(
    {
    url: url,
    type: 'PUT',
    data: json,
    contentType:'application/json; charset=utf-8',
    xhrFields:
    {
        responseType: 'blob'
    },
    success: function(data)
    {
        console.log(data);
        var blob = new Blob([data]);
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = "SaleReport.pdf";
        link.click();
    },
    error:function(respone){
        console.log(respone);
    }
    });
}

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
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = "orderList.pdf";
        link.click();
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
    $('#uploadOrderButton').click(displayUploadData);
    $('#processOrder').click(processOrder);
    $('#createOrder').click(createOrder);
    $('#download-errors').click(downloadErrors);
    $('#orderFile').on('change', updateFileName);
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
  