/**
 * 
 */

var contacts = [];

function reset(){
	
	location.reload();
	
}

function UploadCSVFile(){
	
    console.log("submit event");
    var fd = new FormData(document.getElementById("fileinfo"));
    $.ajax({
      url: "UploadCSVFile",
      type: "POST",
      data: fd,
      enctype: 'multipart/form-data',
      dataType: 'json',
      processData: false,  // tell jQuery not to process the data
      contentType: false   // tell jQuery not to set contentType
    }).done(function( data ) {
        contacts = data;
        console.log(contacts);
        $("#historique tr").remove();
        $("#historique").append("<tr><th>Name</th><th>Email</th><th>Phone</th></tr>");
        drawContacts(contacts);        
    });

    return false;
}

function GetEventMessage() {

    $.ajax({
        type: 'POST',
        url: "GetEventMessage",
        dataType: 'json',

        success: function(data) {
            console.log(data);
            $("#message").val(data);
          },
        error: function(data) {
            console.log(data);
        }        
        
    });
	
}

function GetSampleMessage() {

    $.ajax({
        type: 'POST',
        url: "GetSampleMessage",
        dataType: 'json',

        success: function(data) {
            console.log(data);
            $("#message").val(data);
          },
        error: function(data) {
            console.log(data);
        }        
        
    });
	
}

function GetEventContacts() {

    $.ajax({
        type: 'POST',
        url: "GetEventContacts",
        dataType: 'json',

        success: function(data) {
            console.log(data);
            contacts = data;
            $("#historique tr").remove();
            $("#historique").append("<tr><th>Name</th><th>Email</th><th>Phone</th></tr>");
            drawContacts(data);
          },
        error: function(data) {
            console.log(data);
        }        
        
    });
	
}

function SendSMS() {
 
	
	console.log(contacts);
    // get inputs
    var parms = new Object();
    parms.message = $('#message').val();
    parms.contacts = contacts;
    parms.gsms = $('#gsms').val().split(',');
    
    $.ajax({
        type: 'POST',
        url: "SendSMS",
        dataType: 'json',
        data: JSON.stringify(parms),        

        success: function(data) {
            console.log(data);
            $("#historique tr").remove();
            $("#historique").append("<tr><th>Sid</th><th>When</th><th>From</th><th>To</th><th>Status</th><th>About</th></tr>");
            drawTable(data);
          },
        error: function(data) {
            console.log(data);
        }        
        
    });
}

function drawContacts(data) {
	
	var gsms = "";
    for (var i = 0; i < data.length; i++) {
        drawContact(data[i]);
        gsms += data[i].gsm + ",";
    }
    console.log(gsms);
    $('#gsms').val(gsms);
    
}

function drawContact(rowData) {
    var row = $("<tr />");
    $("#historique").append(row);
    row.append($("<td>" + rowData.nom + " " + rowData.prenom + "</td>"));
    row.append($("<td>" + rowData.email + "</td>"));
    row.append($("<td>" + rowData.gsm + "</td>"));
}


function drawTable(data) {
	
    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
    
}

function drawRow(rowData) {
    var row = $("<tr />");
    $("#historique").append(row);
    row.append($("<td>" + rowData.sid + "</td>"));
    row.append($("<td>" + rowData.hwhen + "</td>"));
    row.append($("<td>" + rowData.from + "</td>"));
    row.append($("<td>" + rowData.to + "</td>"));
    row.append($("<td>" + rowData.status + "</td>"));
    row.append($("<td>" + rowData.body + "</td>"));
}

$(document).on('change', '.btn-file :file', function() {
	  var input = $(this),
	      numFiles = input.get(0).files ? input.get(0).files.length : 1,
	      label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	  input.trigger('fileselect', [numFiles, label]);
	  console.log("numFiles=" + numFiles);
	  console.log("label=" + label);
	  $('#infos').text(label + " has been successfully loaded and is now ready to be uploaded.");
});

