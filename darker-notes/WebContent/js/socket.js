/* 
 * for client side websockets implementation
 * needed for autosave
 */
// wait until page is loaded

//setup before functions
var typingTimer;                //timer identifier
var typingTimer2;
var doneTypingInterval = 500;  //time in ms (2 seconds)
var socket;

//on keyup, start the countdown
$('#text-area').keyup(function(){
    clearTimeout(typingTimer);
    if (document.getElementById("text-area").innerHTML.length > 0) {
        typingTimer = setTimeout(doneTyping, doneTypingInterval);
    }
});

$('#text-title').keyup(function(){
    clearTimeout(typingTimer2);
    if (document.getElementById("text-title").innerHTML.length > 0) {
        typingTimer2 = setTimeout(doneTyping, doneTypingInterval);
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
	var currFileName = "";
	if (document.getElementById("text-title").innerHTML.length > 0) {
		currFileName = document.getElementById("text-title").innerText;
	} else {
		currFileName = "New File";
	}
	
	console.log("File name: " + currFileName)
	
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
	    	},
	    	success: function(result) {
	    		sessionStorage.setItem("currentFileID", result)
	    		console.log("Returned from autoSave, updating sidebar")
	    		updateSidebar();
	    		
	    	},
	    	error: function(result) {
	    		console.log("Error from autoSave, updating sidebar")
	    		updateSidebar();
	    	}
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
	sessionStorage.setItem("fileName", "New File");
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