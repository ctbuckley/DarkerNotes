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
	socket.send("message");
	
	
}

$(document).ready(function () {
	socket = new WebSocket("ws://localhost:8080/final-project/ws");
});