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

function isDecimalKey(txt, evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 46) {
      //Check if the text already contains the . character
      if (txt.value.indexOf('.') === -1) {
        return true;
      } else {
        return false;
      }
    } else {
      if (charCode > 31 &&
        (charCode < 48 || charCode > 57))
        return false;
    }
    return true;
}

function isIntegerKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function resetForm(val){
    document.getElementById(val).reset();
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
    console.log(arr);
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