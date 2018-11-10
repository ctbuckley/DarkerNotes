/* 
 * for client side websockets implementation
 * needed for autosave
 */
// wait until page is loaded
//setup before functions
var typingTimer;                //timer identifier
var doneTypingInterval = 5000;  //time in ms (5 seconds)
var socket;

//on keyup, start the countdown
$('#text-area').keyup(function(){
    clearTimeout(typingTimer);
    if ($('#text-area').val()) {
        typingTimer = setTimeout(doneTyping, doneTypingInterval);
    }
});

//user is "finished typing," do something
function doneTyping () {
    //do something
	socket.send("TestMessageSentFrom Socket.js");
	
	//CHECK IF USER IS SIGNED IN, OTHERWISE DO NOT SAVE ANYTHING
	
	//How Do we want to handle the case where a user is making a new file???
	//How do we want to handle the case where a user has not specified a filename
	
}

$(document).ready(function () {
	socket = new WebSocket("ws://localhost:8080/final-project/ws");
	
	
	
	
});