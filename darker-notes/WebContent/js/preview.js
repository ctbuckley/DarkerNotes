
var inPreview = false;

$("#preview-button").click(function() {
    if (inPreview && $.trim($("#text-area")).length) {
        $("#print-button").toggleClass("d-none");
        $("#title").css("padding-right", "-=131.5px");
        inPreview = false;
        console.log("1st inPreview is now False.");
    }
    else if (!inPreview && $.trim($("text-area")).length) {
        $("#print-button").toggleClass("d-none");
        $("#title").css("padding-right", "+=131.5px");
        inPreview = true;
        console.log("2nd inPreview is now True.");
    }
    else {

    }
});

function getPreview() {
    window.print();
}

$("#print-button").click(function() {
    getPreview();
});
