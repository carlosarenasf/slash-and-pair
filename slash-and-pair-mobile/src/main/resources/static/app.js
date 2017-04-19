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
    var socket = new SockJS('/slash-and-pair');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/mobile/sendMobileData', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
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
	    	stompClient.send("/app/dataMobile/gyroscope", {}, JSON.stringify({'alpha': event.alpha, 'beta' : event.beta, 'gamma' : event.gamma}));
	    }, true);
	} 
}


function sendClick(){
	stompClient.send("/app/dataMobile/click", {}, JSON.stringify({'positionX': "1", 'positionY' : "2"}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendSomeDataMobile() {
	var i = 3; 
	alert("We are going to send that: " + i );
    //stompClient.send("/app/dataMobile", {}, JSON.stringify({'code': i}));
    gyroscope();
    
}

$(document).ready(function() {
    connect();
    console.log("connected socket");
    sendSomeDataMobile();
    
});
    



$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName($('#name').val()); });
    $(".grey-area").click(function(){ sendClick(); });
    //gyroscope();
});

/**

(function() {

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
    	alert("Is connected now?");
    	console.log("init connection");
        var socket = new SockJS('/slash-and-pair');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/user/' + $("#userId").text() + '/mobile/sendMobileData', function () {
                alert("Mobile connected!");
            });

            stompClient.subscribe('/mobile/sendMobileData', function (greeting) {
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

	function sendSomeDataMobile() {
		var i = 3; 
		alert("We are going to send that: " + i );
	    stompClient.send("/app/dataMobile", {}, JSON.stringify({'code': i}));
	}

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    $(document).ready(function() {
        connect();
        console.log("connected socket");
        sendSomeDataMobile()
    });
    
      
})();  
    
function sendName(code) {
    stompClient.send("/app/code", {}, JSON.stringify({'code': code}));
}

function sendSomeDataMobile() {
	var i = 3; 
	alert("We are going to send that: " + i );
    stompClient.send("/app/dataMobile", {}, JSON.stringify({'code': i}));
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

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName($('#name').val()); });
    //gyroscope();
});
    
*/ 