function addProduct(){
    if(document.getElementById("brandInput").value=="Choose Brand..." || document.getElementById("categoryInput").disabled==true || document.getElementById("categoryInput").value=="Choose Category..." ){
        alert("Please choose brand and category first");
        return false;
    }
    if(document.getElementById("mrpInput").value===""){
        alert("Please enter some Price");
        return false;
    }
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
            resetForm("productForm");
        },
        error: handleAjaxError
    });
    return false;
}

function getBrandOptions(){
    $.ajax({
        url: '../api/brand',
        type: 'GET',
        success: function(response){
            console.log("Brand list fetched");
            displayBrandOptions(response);
        },
        error:function(){
            alert("Some error occured while fetching brand options");
        }
    });
    return false;
}

function displayBrandOptions(list){
    var $select=$('#brandOptions').find('select');;
    $select.empty();
    console.log("Printing Brand options");
    var brand = [];
    for(i in list){
        brand.push(list[i].brand);
    }
    brand = [... new Set(brand)];
    $select.append('<option selected>'+ "Choose Brand..." +'</option>');
    for(i in brand){
        var row ='<option>'+ brand[i] +'</option>';
        $select.append(row);
    }
}

function getCategoryOptions(val){
    if(val=="Choose Brand..."){
        var $select=$('#categoryOptions').find('select');
        $select.empty();
        $select.append('<option selected>'+ "Choose Category..." +'</option>');
        document.getElementById("categoryInput").disabled = true;
        return false;
    }
    var $form = {brand : val};
    console.log($form);
    var json = JSON.stringify($form);
    $.ajax({
        url: '../api/brand/fetch-categories',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Category list fetched");
            displayCategoryOptions(response);
        },
        error:function(){
            alert("Some error occured while fetching brand options");
        }
    });
    return false;
}

function displayCategoryOptions(list){
    document.getElementById("categoryInput").disabled = false;
    var $select=$('#categoryOptions').find('select');
    $select.empty();
    console.log("Printing Brand options");
    $select.append('<option selected>'+ "Choose Category..." +'</option>');
    for(i in list){
        var p = list[i];
        console.log("YES");
        console.log(p);
        var row ='<option>'+ p.category +'</option>';
        $select.append(row);
    }
}

function getBrandUpdateOptions(){
    $.ajax({
        url: '../api/brand',
        type: 'GET',
        success: function(response){
            console.log("Brand list fetched");
            displayBrandUpdateOptions(response);
        },
        error:function(){
            alert("Some error occured while fetching brand options");
        }
    });
    return false;
}

function displayBrandUpdateOptions(list){
    var $select=$('#brandUpdateOptions').find('select');;
    $select.empty();
    console.log("Printing Brand options");
    var brand = [];
    for(i in list){
        brand.push(list[i].brand);
    }
    brand = [... new Set(brand)];
    $select.append('<option selected>'+ "Choose Brand..." +'</option>');
    for(i in brand){
        var row ='<option>'+ brand[i] +'</option>';
        $select.append(row);
    }
}

function getCategoryUpdateOptions(val){
    if(val=="Choose Brand..."){
        var $select=$('#categoryUpdateOptions').find('select');
        $select.empty();
        $select.append('<option selected>'+ "Choose Category..." +'</option>');
        document.getElementById("categoryUpdateInput").disabled = true;
        return false;
    }
    var $form = {brand : val};
    console.log($form);
    var json = JSON.stringify($form);
    $.ajax({
        url: '../api/brand/fetch-categories',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Category list fetched");
            displayCategoryUpdateOptions(response);
        },
        error:function(){
            alert("Some error occured while fetching brand options");
        }
    });
    return false;
}

function displayCategoryUpdateOptions(list){
    document.getElementById("categoryUpdateInput").disabled = false;
    var $select=$('#categoryUpdateOptions').find('select');
    $select.empty();
    console.log("Printing category options");
    $select.append('<option selected>'+ "Choose Category..." +'</option>');
    for(i in list){
        var p = list[i];
        console.log("YES");
        console.log(p);
        var row ='<option>'+ p.category +'</option>';
        $select.append(row);
    }
    if(ResetUpdateCategory==true){
        document.getElementById("categoryUpdateInput").value = updateCategoryVal;
        ResetUpdateCategory=false;
    }
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
    if(document.getElementById("brandUpdateInput").value=="Choose Brand..." || document.getElementById("categoryUpdateInput").disabled==true || document.getElementById("categoryUpdateInput").value=="Choose Category..." ){
        alert("Please choose brand and category first");
        return false;
    }
    if(document.getElementById("mrpUpdateInput").value===""){
        alert("Please enter some Price");
        return false;
    }
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
            resetForm("productUpdateForm");
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

ResetUpdateCategory = false;
updateCategoryVal = "";
function editProduct(id){
    resetForm("productUpdateForm");
    var ele = document.getElementById('localID_'+id+'');
    console.log(ele);
    document.getElementById("barcodeUpdateInput").value = ele.cells[1].innerHTML;
    document.getElementById("brandUpdateInput").value = ele.cells[2].innerHTML;
    document.getElementById("nameUpdateInput").value = ele.cells[4].innerHTML;
    document.getElementById("mrpUpdateInput").value = ele.cells[5].innerHTML;
    ResetUpdateCategory=true;
    updateCategoryVal = ele.cells[3].innerHTML;
    getCategoryUpdateOptions(ele.cells[2].innerHTML);
    
    $('#updateModal').modal('toggle');
}

function displayProductList(list){
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
        +'<td>'+p.mrp.toFixed(2)+'</td>'
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
$(document).ready(getBrandOptions);
$(document).ready(getBrandUpdateOptions);