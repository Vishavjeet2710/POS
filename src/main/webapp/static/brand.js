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

function displayBrandList(list){
    console.log("Printing Brands");
    var $tbody=$('#brandTable').find('tbody');
    $tbody.empty();
    for(i in list){
        var b = list[i];
        var row ='<tr>'
        +'<th scope="row">'+b.id+'</th>'
        +'<td>'+b.brand+'</td>'
        +'<td>'+b.category+'</td>'
        +'<td class="d-flex justify-content-end"> Edit | Delete </td>'
        +'</tr>';
        $tbody.append(row);
    }
}

function init(){
    console.log("initialized");
    $('#addBrand').click(addBrand);
    $('#updateBrand').click(updateBrand);
    $('#uploadBrandButton').click(displayUploadData);
    $('#processBrand').click(processBrand);
    $('#brandFile').on('change', updateFileName);
}

$(document).ready(init)
$(document).ready(getBrandList);