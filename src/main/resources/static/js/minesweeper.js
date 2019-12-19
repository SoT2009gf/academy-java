$(document).ready(function() {
	$(".tile").mousedown(function(event) {
		if (event.which === 3) {
			let url = $(this).attr("href");
			url = url.replace("open", "mark");
			window.open(url, "_self");
		}
		if (event.which === 1) {
			var url = $(this).attr("href");
			let position = url.indexOf("mark");
			if (position !== -1) {
				url = url.replace("mark", "open");
				window.open(url, "_self");
			}
		}
	});

	$(".tile").contextmenu(function(event) {
		return false;
	});
});