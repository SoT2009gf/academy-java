$(document).ready(function() {
	if($(".secondTile").length) {
		$(".tile").addClass("disabled");
		setTimeout(window.open, 2000, "/pairs/close", "_self");
	}
});