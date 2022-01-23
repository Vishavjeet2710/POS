var userID;
function addUser(){
    console.log("Adding user");
    var $form = $("#userForm");
    var json = toJson($form);
    $.ajax({
        url: '../api/user',
        type: 'POST',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("User Created");
            getUserList();
            resetForm("userForm");
        },
        error: handleAjaxError
    });
    return false;
}

function getUserList(){
    $.ajax({
        url: '../api/user',
        type: 'GET',
        success: function(response){
            console.log("User list fetched");
            displayUserList(response);
        },
        error: handleAjaxError
    });
    return false;
}

function updatePassowrd(){
    if($("#newPasswordInput").val()!=$("#newPasswordConfirmInput").val()){
        alert("Password is not confirmed correctly");
        return false;
    }
    console.log("Updating Password");
    var ele = document.getElementById('localID_'+userID+'');
    console.log();
    var $form={"email":ele.cells[1].innerHTML, "password":$("#newPasswordInput").val()};
    var json = JSON.stringify($form);
    console.log(json);
    $.ajax({
        url: '../api/user/'+userID+'',
        type: 'PUT',
        data: json,
        contentType:'application/json; charset=utf-8',
        success: function(response){
            console.log("Password Updated");
            $('#updatePasswordModal').modal('toggle');
            getUserList();
            resetForm("passwordUpdateForm");
        },
        error: handleAjaxError
    });
    return false;
}

function updatePassowrdStore(id){
    userID=id;
    resetForm("updatePasswordModal");
}


function displayUserList(list){
    console.log("Printing Brands");
    var $tbody=$('#userTable').find('tbody');
    $tbody.empty();
    for(i in list){
        var b = list[i];
        var row ='<tr id="localID_'+b.id+'">'
        +'<th scope="row">'+String(parseInt(i)+1)+'</th>'
        +'<td>'+b.email+'</td>'
        +'<td>'+'<i class="fa fa-check" aria-hidden="true"></i>'+'</td>'
        +'<td class="d-flex justify-content-end"> <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#updatePasswordModal" id="updatePasswordButton"  onclick="updatePassowrdStore(\'' + b.id + '\')">Update Password</button></td>'
        +'</tr>';
        $tbody.append(row);
    }
}


function init(){
    console.log("initialized");
    $('#addUser').click(addUser);
    $("#updatePassword").click(updatePassowrd);
}

$(document).ready(init)
$(document).ready(getUserList);