var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName(code) {
    stompClient.send("/app/code", {}, JSON.stringify({'code': code}));
}

function gyroscope(){
	//get orientation info
	if (window.DeviceOrientationEvent) 
	{
	    window.addEventListener("deviceorientation", function () 
	    {
//	        sendGyros(event.alpha, event.beta, event.gamma);
//	    	sendName(event.alpha);
	    	stompClient.send("/app/hello", {}, JSON.stringify({'alpha': event.alpha, 'beta' : event.beta, 'gamma' : event.gamma}));
	    }, true);
	} 
}

//function sendGyros(alpha, beta, gamma){
//	stompClient.send("/app/hello", {}, JSON.stringify({'name': alpha}));
//}

function showGreeting(message) {
    //$("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName($('#name').val()); });
    //gyroscope();
});