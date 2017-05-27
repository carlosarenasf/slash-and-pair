
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
    //$("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/slash-and-pair');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/desktop/mobileConnectionSuccess', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
            alert("Conection success");
            $('#divQR').css('display', 'none');
        });

        stompClient.subscribe('/user/desktop/receiveMobileData', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
            //$("connectedInformation").show();
            showGreeting(greeting.body);
            
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


function showGreeting(message) {
    //$("#greetings").append("<tr><td>" + message + "</td></tr>");
    var parse = message;
	var json_obj = JSON.parse(parse);
	var alpha = parseFloat(json_obj.alpha);
	var beta = parseFloat(json_obj.beta);
	var gamma = parseFloat(json_obj.gamma);
	if(beta > 10.0){
		KeyboardJS.bind.sensorDown("right");
		console.log("press");
	} else {
        KeyboardJS.bind.sensorUp("right");
        console.log("up");
	}
	if(beta < -10.0){
		KeyboardJS.bind.sensorDown("left");
		console.log("press");
	} else {
        KeyboardJS.bind.sensorUp("left");
        console.log("up");
	}
	if(gamma > 10.0){
		KeyboardJS.bind.sensorDown("up");
		console.log("press");
	} else {
        KeyboardJS.bind.sensorUp("up");
        console.log("up");
	}
	if(gamma < -10.0){
		KeyboardJS.bind.sensorDown("down");
		console.log("press");
	} else {
        KeyboardJS.bind.sensorUp("down");
        console.log("up");
	}
}

/*$(document).ready(function() {
    connect();
    console.log("connected socket");
    
});*/

