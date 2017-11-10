<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Hello WebSocket</title>
    <script src="${ctxStatic}/js/stomp.js"></script>
    <script src="${ctxStatic}/js/sockjs.js"></script>
    <script src="${ctxStatic}/js/jquery-1.11.3.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            connect();
            //checkoutUserlist();
        });

        var stompClient = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
            document.getElementById('response').innerHTML = '';
        }

        //this line.
        function connect() {
            var userid = document.getElementById('name').value;
            var socket = new SockJS("http://127.0.0.1:8080/personalFrame/hello");
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                // 该方法是接收广播消息。
                stompClient.subscribe('/topic/greetings', function(greeting){
                    showGreeting(JSON.parse(greeting.body).content);
                });

                // 该方法表示接收一对一消息，其主题是"/user/"+userId+"/message"，
                // 不同客户端具有不同的id。如果两个或多个客户端具有相同的id，
                // 那么服务器端给该userId发送消息时，这些客户端都可以收到。
                stompClient.subscribe('/user/' + userid + '/message',function(greeting){
                    alert(JSON.parse(greeting.body).content);
                    showGreeting(JSON.parse(greeting.body).content);
                });
            });
        }

        function sendName() {
            var name = document.getElementById('name').value;
            stompClient.send("/app/hello", {atytopic:"greetings"}, JSON.stringify({ 'name': name }));
        }

        function connectAny() {
            var socket = new SockJS("http://127.0.0.1:8080/hello");
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/feed', function(greeting){
                    alert(JSON.parse(greeting.body).content);
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


        function showGreeting(message) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            response.appendChild(p);
        }
    </script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="connectAny" onclick="connectAny();">ConnectAny</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
    </div>
    <div id="conversationDiv">
        <label>What is your name?</label><input type="text" id="name" />
        <button id="sendName" onclick="sendName();">Send</button>
        <p id="response"></p>
    </div>
</div>
</body>
</html>