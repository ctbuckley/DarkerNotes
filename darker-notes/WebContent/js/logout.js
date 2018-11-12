/*
 * Logout user function
 */

function logout() {
	console.log("logging out " + sessionStorage.getItem("name"))
	
	// clear sessionStorage
	sessionStorage.clear();
	sessionStorage.setItem("currentFileID", -1);
	sessionStorage.setItem("fileName", "Default File");
	sessionStorage.setItem("signedin", false);
	
	// hide elements, put back into guest mode
	$("#signin-button").toggleClass("d-none");
	$("#signout-button").toggleClass("d-none");
	$("#share-button").toggleClass("d-none");
	$("#sidebarCollapse").toggleClass("d-none");
	$("#notification-button").toggleClass("d-none");
}