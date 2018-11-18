//Adds submit using enter functionality to specified input field id, calling functionCall
function submitOnEnter(id, functionCall) {
	$(id).on("keydown", function(e) {
		if ($(id).is(":focus") && e.keyCode == 13) {
			console.log("enter has been pressed!");
			functionCall();
		}	
	});
	console.log("Testing submit on enter!");
}
//for signin
submitOnEnter("#passIn", validateLogin);
//for file share
submitOnEnter("#shareEmail", sendFile);

