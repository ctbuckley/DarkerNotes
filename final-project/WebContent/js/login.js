/*
 *	Functions for logging in or signing up
 *	interacts with Servlets
 *
 */

function validateLogin() {
	var emailIn = document.getElementById("emailIn").value;
	var passIn = document.getElementById("passIn").value;

	//Go to servlet to validateLogin()
	$.ajax({
        	type: "POST",
        	url: "ValidateLogin",
        	async: true,
        	data: {
				email: emailIn,
				pass: passIn
        	},
        	success: function(result) {
        		console.log("Result.success = " + result.success)
        		if (result.success == "true") {
        			//document.getElementById("errorMsg").innerHTML = "";
        			//Do session storage for name and email
					sessionStorage.setItem("name", result.data.name);
					console.log(sessionStorage.getItem("name"));
					sessionStorage.setItem("email", result.data.email);
					console.log(sessionStorage.getItem("email"));
					sessionStorage.setItem("signedin", true);
        			onLogIn();
        		}
        		else {
        			console.log("Failed to sign in")
        			//document.getElementById("errorMsg").innerHTML = result.data.errorMsg;
        		}
        	}
        })

}

function onLogIn() {
	console.log("In on log in")
	//do stuff on log in
	//display sidebar
	var emailIn = document.getElementById("emailIn").value;

    $.ajax({
       type: "POST",
       url: "GetFiles",
       async: true,
       data: {
            email: emailIn
       },
       success: function(result) {
              console.log(result)
              document.getElementById("sidebar-files").innerHTML = "";
              $("#sidebar-files").append(result);
       }
   });

	//change sign in button to show name and change onclick attribute to call a sign out function
	//display share button
}

function addUser() {
	var emailIn = document.getElementById("emailIn").value;
	var passIn = document.getElementById("passIn").value;
	var nameIn = document.getElementById("nameIn").value;

	console.log('data', emailIn, passIn, nameIn);

	$.ajax({
    	type: "POST",
    	url: "AddUser",
    	async: true,
    	data: {
			email: emailIn,
			pass: passIn,
			name: nameIn
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			console.log("Success")
    			onLogIn();
    		}
    		else {
    			//Update error message html to display error message
    			//document.getElementById("errorMsg").innerHTML = result.data.errorMsg;

    		}
    	}
    })
}

function loadFile(fileID){
	sessionStorage.setItem("currentFileID", fileID);
	//var emailIn = document.getElementById("emailIn").value;
	
	$.ajax({
    	type: "POST",
    	url: "GetFileContent",
    	async: true,
    	data: {
			file: fileID
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			//replace what's in the editor with data returned from the servlet
    			document.getElementById("text-area").innerHTML=result.data.rawData;
    		}
    		else {
    			//Update error message html to display error message
    			//document.getElementById("errorMsg").innerHTML = result.data.errorMsg;

    		}
    	}
    })	
}

function addFile(fileName,rawData){
	var emailIn = document.getElementById("emailIn").value;
	
	$.ajax({
    	type: "POST",
    	url: "AddFile",
    	async: true,
    	data: {
			email: emailIn,
			file: fileName,
			data: rawData
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			console.log("Success")   			
    		}
    		else {
    			//Update error message html to display error message
    			//document.getElementById("errorMsg").innerHTML = result.data.errorMsg;

    		}
    	}
    })
	
}
