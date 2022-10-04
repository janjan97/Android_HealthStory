$(document).ready(function () {
	generateCalendar();
	setStatus();
});

function oneArmCurl() {
	window.location.href='./domains/OneArmCurl.html';
}

function setStatus() {
	$(".js-did-workout").on("click", function () {
		window.location.href='./domains/OneArmCurl.html';

		
	});

	$(".js-no-workout").on("click", function () {
		var currentDay = $(".js-schedule-list").find(
			'.js-day[data-status="false"]:first'
		);
		currentDay.attr("data-status", "failed");
		var originalDay = currentDay.data("day");
		updateCalendar(originalDay);
		updateDays();
		checkNextDay();
	});
}

function checkNextDay() {
	var dayAfter =  $('.js-day[data-status="false"]').first();
	
	if ($('.js-day[data-status="false"]').length == 0) {
		$('.rest-day, .workout-day').css('display', 'none');
		$('.amazing-day').css('display','flex'); 
	} else if ($('.workout__rest', dayAfter).length) {
		$('.rest-day').css('display', 'flex');
		$('.workout-day').css('display','none');
	} else {
		$('.rest-day').css('display','none');
		$('.workout-day').css('display', 'flex');
	}
}