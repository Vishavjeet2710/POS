var orderId;
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
            var b = response;
            orderId=b.id;
            console.log(orderId);
            getOrderList();
        },
        error: handleAjaxError
    });
    return false;
}

function getOrderList(){
    $.ajax({
        url: '../api/order/'+orderId+'',
        type: 'GET',
        success: function(response){
            console.log("Order Item list fetched");
            displayOrderList(response);
        },
        error: handleAjaxError
    });
    return false;
}

function addBrand(){
    console.log("Adding Brand");
    var $form = $("#brandForm");
    var json = toJson($form);
    $.ajax({
        url: '../api/brand',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Brand Created");
            getBrandList();
        },
        error: handleAjaxError
    });
    return false;
}

function getBrandList(){
    $.ajax({
        url: '../api/brand',
        type: 'GET',
        success: function(response){
            console.log("Brand list fetched");
            displayBrandList(response);
        },
        error: handleAjaxError
    });
    return false;
}

function updateBrand(){
    console.log("Updating Brand");
    var $form = $("#brandUpdateForm");
    var json = toJson($form);
    $.ajax({
        url: '../api/brand/update',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Brand Updated");
            $('#updateModal').modal('toggle');
            getBrandList();
        },
        error: handleAjaxError
    });
    return false;
}

// Upload functions
var fileData = [];
var errorData = [];
var processCount = 0;

function processBrand(){
    var file = $('#brandFile')[0].files[0];
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
        getBrandList();
        return;
    }
    // Process next row
    var row = fileData[processCount];
    processCount++;

    var json = JSON.stringify(row);
    var url = '../api/brand';

    // Make ajax call
    $.ajax({
        url: '../api/brand',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            uploadRows();
        },
        error: function(){
            row.error=response.responseText;
            errorData.push(row);
            uploadRows();
        }
    });
}

function updateFileName(){
    document.querySelector("#brandFileName").textContent = this.files[0].name;
}

function downloadErrors(){
    writeFileData();
}

function resetUploadDialog(){
    var $file = $('#brandFile');
    $file.val('');
    $('#brandFileName').html("Choose File");
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

function displayOrderList(list){
    console.log("Printing Order");
    var $tbody=$('#orderTableAdd').find('tbody');
    $tbody.empty();
    for(i in list){
        var b = list[i];
        var row ='<tr>'
        +'<th scope="row">'+b.id+'</th>'
        +'<td>'+b.barcode+'</td>'
        +'<td>'+b.quantity+'</td>'
        +'<td>'+b.mrp+'</td>'
        +'</tr>';
        $tbody.append(row);
    }
}

// Download function

function downloadList(){
    console.log("Downloading");

    url='../api/brand/download';
    $.ajax(
    {
    dataType: 'native',
    url: url,
    xhrFields:
    {
        responseType: 'blob'
    },
    success: function(blob)
    {
        var filename = "brandList.pdf";
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = "brandList.pdf";
        link.click();
        var file = new File([blob], filename, { type: 'application/force-download' });
        window.open(URL.createObjectURL(file));
    }
    });
}


function init(){
    console.log("initialized");
    $('#addBrand').click(addBrand);
    $('#createOrder').click(createOrder);
    $('#updateBrand').click(updateBrand);
    $('#uploadBrandButton').click(displayUploadData);
    $('#processBrand').click(processBrand);
    $('#downloadButton').click(downloadList);
    $('#brandFile').on('change', updateFileName);
}

$(document).ready(init)
$(document).ready(getBrandList);

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
  