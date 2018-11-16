/*
 * Renders the preview window 
 */

// set up converter on loading script
var converter = new showdown.Converter();
converter.setOption('simplifiedAutoLink', true);
converter.setOption('tables', true);

// called when ready to parse and render an HTML element's text
var convert = function() {
	//var html = converter.makeHtml($('#text-area').val())
	var html = converter.makeHtml(document.getElementById('text-area').innerText)
	$('#preview-shade').html(html);
	console.log(html)
	// render math in the preview-shade with KaTeX 
	renderMathInElement(document.getElementById('preview-shade'), [
		{left: "$$", right: "$$", display: true},
		{left: "\\(", right: "\\)", display: false},
		{left: "\\[", right: "\\]", display: true}
	]);
};



/* Preview button code */
$('#preview-button').click(function() {
		convert()	
		console.log('render markdown + LaTeX in preview-shade')
});

//disable preview button if there is no text in input box
$('#text-area').keyup(function() { togglePreviewButton($('#text-area')) } );

function togglePreviewButton(element) {
	$('#preview-popover').popover('toggleEnabled') // toggle the popover
    if(element.text().length != 0) {
    	// turn on button, disable popover
    	$('#preview-popover').popover('hide') // hide the popover
    	$('#preview-popover').popover('disable') // disable the popover
    	$('#preview-button').removeClass('disabled');
    	$('#preview-button').prop('disabled', false); 
    }
    else {
    	// turn off button, enable popover
    	$('#preview-popover').popover('enable') // enable the popover
    	$('#preview-button').addClass('disabled');
    	$('#preview-button').prop('disabled', true);
    }
}