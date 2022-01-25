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
            resetForm("brandForm");
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
            resetForm("brandUpdateForm");
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
        error: function(response){
            var ele=JSON.parse(response.responseText);
            row.error=ele.message;
            errorData.push(row);
            uploadRows();
        }
    });
}

function updateFileName(){
    document.querySelector("#brandFileName").textContent = this.files[0].name;
}

function downloadErrors(){
    writeFileData(errorData);
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

function editBrand(id){
    resetForm("brandUpdateForm");
    var ele = document.getElementById('localID_'+id+'');
    console.log(ele);
    document.getElementById("exBrandInput").value = ele.cells[1].innerHTML;
    document.getElementById("exCategoryInput").value = ele.cells[2].innerHTML;
    document.getElementById("brandUpdateInput").value = ele.cells[1].innerHTML;
    document.getElementById("categoryUpdateInput").value = ele.cells[2].innerHTML;
    $('#updateModal').modal('toggle');
}

function displayBrandList(list){
    console.log("Printing Brands");
    var $tbody=$('#brandTable').find('tbody');
    $tbody.empty();
    for(i in list){
        var b = list[i];
        var row ='<tr id="localID_'+b.id+'">'
        +'<th scope="row">'+String(parseInt(i)+1)+'</th>'
        +'<td>'+b.brand+'</td>'
        +'<td>'+b.category+'</td>'
        +'<td class="d-flex justify-content-end"> <button type="button" class="btn btn-secondary btn-sm float-right" onclick="editBrand(\'' + b.id + '\')" >Edit</button> </td>'
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
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = "brandList.pdf";
        link.click();
    }
    });
}


function init(){
    console.log("initialized");
    $('#addBrand').click(addBrand);
    $('#updateBrand').click(updateBrand);
    $('#uploadBrandButton').click(displayUploadData);
    $('#processBrand').click(processBrand);
    $('#downloadButton').click(downloadList);
    $('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName);
}

$(document).ready(init)
$(document).ready(getBrandList);