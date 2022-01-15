function toJson($form){
    var serialized = $form.serializeArray();
    var s='';
    var data={};
    for(s in serialized){
        data[serialized[s]['name']]=serialized[s]['value'];
    }
    var json = JSON.stringify(data);
    console.log(json);
    return json;
}

function handleAjaxError(response){
    console.log("Error occured");
    var response = JSON.parse(response.responseText);
    alert(response.message);
}

function readFileData(file, callback){
    console.log("Reading File");
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
    console.log("Parsing File");
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click(); 
}

function init(){
    console.log("initialized");
}

$(document).ready(init)