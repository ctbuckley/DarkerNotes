/* 
 * for client side websockets implementation
 * needed for autosave
 */
// wait until page is loaded

//setup before functions
var typingTimer;                //timer identifier
var doneTypingInterval = 2000;  //time in ms (2 seconds)
var socket;

//on keyup, start the countdown
$('#text-area').keyup(function(){
    clearTimeout(typingTimer);
    if (document.getElementById("text-area").innerHTML.length > 0) {
        typingTimer = setTimeout(doneTyping, doneTypingInterval);
    }
});

//user is "finished typing," do something
function doneTyping () {
    //Get Current FileID
	var currFileID = sessionStorage.getItem("currentFileID");
	//Get rawData
	var rawFileData = document.getElementById("text-area").innerHTML;
	//Get email
	var emailIn = sessionStorage.getItem("email");
	//Get Filename
	var currFileName = sessionStorage.getItem("fileName");
	
	if (sessionStorage.getItem("signedin") == "true") {
		console.log("sending a message to server now")
		
		$.ajax({
	    	type: "POST",
	    	url: "autoSave",
	    	async: true,
	    	data: {
				email: emailIn,
				fileID: currFileID,
				fileName: currFileName,
				fileContent: rawFileData 
	    	}
			/*
	    	success: function(result) {
	    		if (result.success == "true") {
	    			console.log("Success")
	    			
	    		}
	    		else {
	    			//Update error message html to display error message
	    			//document.getElementById("errorMsg").innerHTML = result.data.errorMsg;
	
	    		}
	    	}
	    	*/
		})
		
		
		
		
	}
	
	//How Do we want to handle the case where a user is making a new file???
	//How do we want to handle the case where a user has not specified a filename
}

function sendFile() {
	
	var emailToUser = document.getElementById("shareEmail").value;
	var rawFileData = document.getElementById("text-area").innerHTML;
	var currFileID = sessionStorage.getItem("currentFileID");
	
	socket.send(JSON.stringify({
		action: "SendFile",
		email: sessionStorage.getItem("email"),
		emailTo: emailToUser,
		fileID: currFileID,
		rawData: rawFileData
	}));
}

//Setting up the WebSocket connection for client side
$(document).ready(function () {
	socket = new WebSocket("ws://localhost:8080/darker-notes/ws");
	
	sessionStorage.setItem("signedin", false);
	
	sessionStorage.setItem("currentFileID", -1);
	
	//sessionStorage.setItem("currentFileID", "1");
	sessionStorage.setItem("fileName", "Default File");
	//sessionStorage.setItem("fileName", "TestFileConnor");
	
	socket.onopen = function(event) {
		console.log("Connected in socket.js")
	}
	socket.onmessage = function(event) {
		console.log("Message in socket.js" + event.data)
		
		//A message sent from server to client
		
		//If action == updateFileID {
		//Server sent back a fileID for a new file
		//sessionStorage.setItem("currentFileID", event.data);
		//}
		
		//If action == notification {
		// Display a new notification to the user
		//}
	}
	socket.onclose = function(event) {
		console.log("Disconnected in socket.js")
	}
	
	
});