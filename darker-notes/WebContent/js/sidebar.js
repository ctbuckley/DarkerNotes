/** For the sidebar animations **/
// for the button menu -> x
$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
    	$(this).toggleClass('collapsed');
    });
});

// for the slidein/slideout functionality
$("#sidebarCollapse").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
    // listen for mouse hovers to spawn delete button
    deleteButtonListener();
});

// listen for hover to let delete button appear
function deleteButtonListener() {
	$('.file-table-row').hover(function() {
		$(this).find('.delete-button').toggleClass('d-none');
	}, function() {
		$(this).find('.delete-button').toggleClass('d-none');
	});
}
