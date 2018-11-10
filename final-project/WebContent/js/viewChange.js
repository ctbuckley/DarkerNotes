var showing = false;
$("#sidebarCollapse").click(function() {
    if (showing) {
        $("#text-area").css("margin-right", "20%");
        showing = false;
    }
    else {
        $("#text-area").css("margin-right", "0");
        showing = true;
    }
    console.log("Changed CSS!");
});
