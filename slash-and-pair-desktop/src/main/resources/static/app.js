
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
            stompClient.subscribe('/user/' + $("#userId").text() + '/desktop/mobileConnectionSuccess', function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });

            stompClient.subscribe('/desktop/receiveMobileData', function (greeting) {
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
        $("#alpha").text(json_obj.alpha);
        $("#betta").text(json_obj.beta);
        $("#gamma").text(json_obj.gamma);
    }

    $(document).ready(function() {
        connect();
        console.log("connected socket");
    });

