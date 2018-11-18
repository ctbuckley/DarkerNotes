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
});