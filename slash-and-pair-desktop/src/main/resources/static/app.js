
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
        stompClient.subscribe('/user/sendFourCode', function (greeting) {
            changeToCode(greeting.body);
        });
        stompClient.subscribe('/user/mobileConnectionSuccess', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
            $('#divQR').css('display', 'none');
            $('.overlay').css('display', 'none');
        });

        stompClient.subscribe('/user/receiveMobileData', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
            //$("connectedInformation").show();
            showGreeting(greeting.body);
            
        });
    
	}, function(message) {
	  connect();
	  
	});
}

function changeToCode(valueCode){
	$('#divQR').find('img').after("<h1 style='font-size:3em;'>"+valueCode+"</h1>");
	$('#divQR').find('img').remove();
	//changeToCode(greeting.body);
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

function request4Digits(){
	var code = "4digitscode";
    stompClient.send("/app/fourCode", {}, JSON.stringify({'code': "codigo"}));
	
    
}

/*$(document).ready(function() {
    connect();
    console.log("connected socket");
    
});*/

