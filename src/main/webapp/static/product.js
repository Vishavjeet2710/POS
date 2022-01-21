function addProduct(){
    console.log("Adding Product");
    var $form = $('#productForm');
    var json = toJson($form);
    $.ajax({
        url: '../api/product',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Product Created");
            getProductList(response);
        },
        error: function(){
            alert("Some error occurred while creating product");
        }
    });
    return false;
}

function getProductList(){
    $.ajax({
        url: '../api/product',
        type: 'GET',
        success: function(response){
            console.log("Product list fetched");
            displayProductList(response);
        },
        error:function(){
            alert("Some error occured while fetching products");
        }
    });
    return false;
}

function updateProduct(){
    console.log("Updating Product");
    var $form = $("#productUpdateForm");
    var json = toJson($form);
    $.ajax({
        url: '../api/product/update',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Product Updated");
            $('#updateModal').modal('toggle');
            getProductList();
        },
        error: handleAjaxError
    });
    return false;
}

// Upload functions
var fileData = [];
var errorData = [];
var processCount = 0;

function processProduct(){
    var file = $('#productFile')[0].files[0];
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
        getProductList();
        return;
    }
    // Process next row
    var row = fileData[processCount];
    processCount++;

    var json = JSON.stringify(row);
    var url = '../api/product';

    // Make ajax call
    $.ajax({
        url: '../api/product',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
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
    document.querySelector("#productFileName").textContent = this.files[0].name;
}

function downloadErrors(){
    writeFileData(errorData);
}

function resetUploadDialog(){
    var $file = $('#productFile');
    $file.val('');
    $('#productFileName').html("Choose File");
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

function editProduct(id){
    var ele = document.getElementById('localID_'+id+'');
    console.log(ele);
    document.getElementById("barcodeUpdateInput").value = ele.cells[1].innerHTML;
    $('#updateModal').modal('toggle');
}

function displayProductList(list){
    console.log("Printing Products");
    console.log("Printing Products");
    var $tbody=$('#productTable').find('tbody');
    $tbody.empty();
    console.log("Printing Products");
    console.log(list.keys());
    for(i in list){
        var p = list[i];
        console.log("YES");
        console.log(p);
        var row ='<tr id="localID_'+p.id+'">'
        +'<th scope="row">'+String(parseInt(i)+1)+'</th>'
        +'<td>'+p.barcode+'</td>'
        +'<td>'+p.brand+'</td>'
        +'<td>'+p.category+'</td>'
        +'<td>'+p.name+'</td>'
        +'<td>'+p.mrp+'</td>'
        +'<td class="d-flex justify-content-end"> <button type="button" class="btn btn-secondary btn-sm float-right" onclick="editProduct(\'' + p.id + '\')" >Edit</button> </td>'
        +'</tr>';
        $tbody.append(row);
    }
}

// Download function

function downloadList(){
    console.log("Downloading");

    url='../api/product/download/';
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
        link.download = "productList.pdf";
        link.click();
    },
    error:function(respone){
        console.log(respone);
    }
    });
}

function init(){
    console.log("initialized");
    $('#addProduct').click(addProduct);
    $('#updateProduct').click(updateProduct);
    $('#uploadProductButton').click(displayUploadData);
    $('#processProduct').click(processProduct);
    $('#downloadButton').click(downloadList);
    $('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);
}

$(document).ready(init)
$(document).ready(getProductList);