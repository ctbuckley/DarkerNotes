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
}

function acceptNotification(id) {
	console.log('decline notificiation id', id)
}

function declineNotification(id) {
	console.log('decline notificiation id', id)
}

// listen for clicks on accept and decline buttons
$('#notification-accept-button').click(acceptNotification(id))
$('#notification-decline-button').click(declineNotification(id))