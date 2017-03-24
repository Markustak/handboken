// jQuery för att diven alltid ska ligga centralt horisontellt
$( document ).ready(function() {
  var height = $(window).height();
  var divHeight = $("#main-content").outerHeight();
  $("#main-content").css("margin-top",(height-divHeight)/2);
});
$( window ).resize(function() {
  height = $(window).height();
	divHeight = $("#main-content").outerHeight();
	$("#main-content").css("margin-top",(height-divHeight)/2);
});$("#main-content").resize(function() {
  height = $(window).height();
	divHeight = $("#main-content").outerHeight();
	$("#main-content").css("margin-top",(height-divHeight)/2);
});
