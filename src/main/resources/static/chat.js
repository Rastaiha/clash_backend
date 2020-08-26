var stompClient = null;
var chatId = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#chats").html("");
}

function connect() {
    console.log("trying to connect")
    var socket = new SockJS('/websocket');
    //if you connect through WebSocket (without SockJS)
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
    });
}

//            function connect() {
//                var socket = new SockJS('/spring-mvc-java/chat');
//                stompClient = Stomp.over(socket);
//                stompClient.connect({}, function(frame) {
//                    setConnected(true);
//                    console.log('Connected: ' + frame);
//                    stompClient.subscribe('/topic/messages', function(messageOutput) {
//                        showMessageOutput(JSON.parse(messageOutput.body));
//                    });
//                });
//            }

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function join() {
	chatId = $("#chatId").val();
	$("#chatHeader").append(chatId);
	stompClient.subscribe('/chat/' + chatId, function (greeting) {
		showChat(JSON.parse(greeting.body).content);
	});
}

function send() {
    stompClient.send("/app/say", {}, JSON.stringify({
    	'content': $("#message").val(),
    	'chatId': chatId
    }));
}

function showChat(message) {
    $("#chats").append("<tr><td>" + message + "</td></tr>");
}

(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#join" ).click(function() { join(); });
    $( "#send" ).click(function() { send(); });
});
