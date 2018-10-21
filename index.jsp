<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>CS201 Project</title>
		<link rel="stylesheet" href="style/style.css" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	</head>
	<body>
		<header> </header>
		<div id="mainArea">
			<input placeholder=" Email" type="email" id="emailIn">
			<input placeholder=" Password" type="text" onfocus="(this.type='password')" id="passIn">
			<button onclick="validateLogin();" id="signInButton" style="display: block;">Sign In</button>
			<input placeholder=" Name" type="text" id="nameIn">
			<button onclick="addUser();" id="addUserButton" style="display: block;">Add User</button>
			<div id = "errorMsg"></div>
		</div>
		<footer> </footer>
		<script>
			function validateLogin() {
				var emailIn = document.getElementById("emailIn").value;
				var passIn = document.getElementById("passIn").value;
				
				//Go to servlet to validateLogin()
				jQuery.ajax({
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
	                			document.getElementById("errorMsg").innerHTML = "";
	                			//Do session storage for name and email
	                			
	                			onLogIn();
	                		}
	                		else {
	                			console.log("Failed to sign in")
	                			document.getElementById("errorMsg").innerHTML = result.data.errorMsg;
	                		}
	                	}
	                })
				
			}
			
			function onLogIn() {
				console.log("In in on log in")
				//do stuff on log in
				//display sidebar
				//change sign in button to show name and change onclick attribute to call a sign out function
				//display share button
			}
			
			function addUser() {
				var emailIn = document.getElementById("emailIn").value;
				var passIn = document.getElementById("passIn").value;
				var nameIn = document.getElementById("nameIn").value;
				
				jQuery.ajax({
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
                			document.getElementById("emailIn").innerHTML = result.data.errorMsg;
                		}
                	}
                })
			}
		</script>
	</body>
</html>