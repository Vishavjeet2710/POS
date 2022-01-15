function addInventory(){
    console.log("Adding Inventory");
    var $form = $("#inventoryForm");
    var json = toJson($form);
    $.ajax({
        url: '../api/inventory',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Inventory Created");
            getInventoryList();
        },
        error: handleAjaxError
    });
    return false;
}

function getInventoryList(){
    console.log("Fetching Inventory List");
    $.ajax({
        url: '../api/inventory',
        type: 'GET',
        success: function(response){
            console.log("Inventory list fetched");
            displayInventoryList(response);
        },
        error: handleAjaxError
    });
    return false;
}

function updateInventory(){
    console.log("Updating Inventory");
    var $form = $("#inventoryUpdateForm");
    var json = toJson($form);
    $.ajax({
        url: '../api/inventory/update',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Inventory Updated");
            $('#updateModal').modal('toggle');
            getInventoryList();
        },
        error: handleAjaxError
    });
    return false;
}

// Upload functions
var fileData = [];
var errorData = [];
var processCount = 0;

function processInventory(){
    var file = $('#inventoryFile')[0].files[0];
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
        getInventoryList();
        return;
    }
    // Process next row
    var row = fileData[processCount];
    processCount++;

    var json = JSON.stringify(row);
    var url = '../api/inventory';

    // Make ajax call
    $.ajax({
        url: '../api/inventory',
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
    document.querySelector("#inventoryFileName").textContent = this.files[0].name;
}

function downloadErrors(){
    writeFileData();
}

function resetUploadDialog(){
    var $file = $('#inventoryFile');
    $file.val('');
    $('#inventoryFileName').html("Choose File");
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

function displayInventoryList(list){
    console.log("Printing Inventory");
    var $tbody=$('#inventoryTable').find('tbody');
    $tbody.empty();
    for(i in list){
        var b = list[i];
        var row ='<tr>'
        +'<th scope="row">'+b.id+'</th>'
        +'<td>'+b.barcode+'</td>'
        +'<td>'+b.quantity+'</td>'
        +'<td class="d-flex justify-content-end"> Edit | Delete </td>'
        +'</tr>';
        $tbody.append(row);
    }
}

function init(){
    console.log("initialized");
    $('#addInventory').click(addInventory);
    $('#updateInventory').click(updateInventory);
    $('#uploadInventoryButton').click(displayUploadData);
    $('#processInventory').click(processInventory);
    $('#inventoryFile').on('change', updateFileName);
}

$(document).ready(init)
$(document).ready(getInventoryList);