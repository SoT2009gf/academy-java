$(document).ready(function() {
	$(".secondTile").click(function(event) {
		window.setTimeout(window.open, 3000, $(this).attr("href"), "_self");
	});
});