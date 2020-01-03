$(document).ready(bindTiles);
var url;

function bindTiles() {
	$(".tile").mousedown(function(event) {
		event.preventDefault();
		if (event.which === 3) {
			url = $(this).attr("href");
			url = url.replace("open", "mark");
			$(".game-field-container").load(url, bindTiles);
		}
		if (event.which === 1) {
			url = $(this).attr("href");
			let position = url.indexOf("mark");
			if (position !== -1) {
				url = url.replace("mark", "open");
			}
			$(".game-field-container").load(url, bindTiles);
		}
	});

	$(".tile").contextmenu(function(event) {
		return false;
	});

	if ($("#game-won").length) {
		$("#game-won").remove();
		window.open("/minesweeper/refresh", "_self");
	}
}