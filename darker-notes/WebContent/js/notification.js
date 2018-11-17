// handle Notification popover
// toggles show/hide when #notification-button
// hides #notification-button when user clicks outside of notification button
$('#notification-button').click(function() {
    $(this).popover('toggle');
}).blur(function() {
    $(this).popover('hide');
});


function handleNotification(id) {
	console.log("handleNotification id ", id);
	
	//display preview of file
	//servlet call
	
	//retrieve rawData and fileName w/ notification id
	
	//pass notification id
	
	$.ajax({
    	type: "POST",
    	url: "RetrieveNotification",
    	async: true,
    	data: {
			notificationId: id
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			//display preview modal with option to accept/decline file
    			
    			//Use below to set up preview window
    			//result.data.rawData
    			//result.data.fileName
    			//result.data.fromName
    			
    			//also pass "id" to acceptNotification() or declineNotification()
    			//set up 
    			
    			
    		} else {
    			console.log("Error in RetrieveNotification")
    		}
    	}
    })
	
}

function acceptNotification(notificationId, fileName, rawData) {
	console.log('accept notificiation id', notificationId)
	
	$.ajax({
    	type: "POST",
    	url: "AcceptNotification",
    	async: true,
    	data: {
			notificationId: notificationId,
			fileName: fileName,
			rawData: rawData
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			//display preview modal with option to accept/decline file
    			updateSidebar();
    			loadNotifications()
    			
    			//Use below to set up preview window
    			//result.data.rawData
    			//result.data.fileName
    			//result.data.fromName
    			
    			//also pass "id" to acceptNotification() or declineNotification()
    			//set up 
    			
    			
    		} else {
    			console.log("Error in acceptNotification")
    		}
    	}
    })
	
	//accept the file
	//servlet call
	
	//set notification isRead = 1;
	//generate new file in Files
	//generate new fow in Access for specified user
	
	//on success, add to sidebar, update notification table
	
}

function declineNotification(id) {
	console.log('decline notificiation id', id)
	
	//decline the file
	//servlet call
	
	$.ajax({
    	type: "POST",
    	url: "DeclineNotification",
    	async: true,
    	data: {
			notificationId: notificationId
    	},
    	success: function(result) {
    		if (result.success == "true") {
    			//display preview modal with option to accept/decline file
    			loadNotifications()
    			
    			//Use below to set up preview window
    			//result.data.rawData
    			//result.data.fileName
    			//result.data.fromName
    			
    			//also pass "id" to acceptNotification() or declineNotification()
    			//set up 
    			
    			
    		} else {
    			console.log("Error in declineNotification")
    		}
    	}
    })
}

// listen for clicks on accept and decline buttons
$('#notification-accept-button').click(acceptNotification(id))
$('#notification-decline-button').click(declineNotification(notificationId, fileName, rawData))