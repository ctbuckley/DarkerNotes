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
$('#preview-button').click(function() {
		convert()
		console.log('render markdown + LaTeX in preview-shade')
});