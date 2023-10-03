var stompClient = null;
var wsCreateHandler = null;
var userId = null;

function connect() {
	var host = window.location.host; // 带有端口号
	userId = GetQueryString("userId");
	var socket = new SockJS("http://" + host + "/webSocket");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		writeToScreen("connected: " + frame);
		// 广播
		stompClient.subscribe('/topic/broadcast', function(response) {
			writeToScreen("系统消息:" + response.body);
		});

		// 用户p2p
		stompClient.subscribe("/queue/" + userId + "/topic", function(response) {
			writeToScreen(response.body);
		});

		// web模式
		stompClient.subscribe('/topic/web', function(response) {
			writeToScreen("web:" + response.body);
		});

		// 订阅，一般只有订阅的时候在返回
		stompClient.subscribe("/app/subscribe/" + userId, function(response) {
			writeToScreen("订阅成功：" + response.body);
		});

	}, function(error) {
		wsCreateHandler && clearTimeout(wsCreateHandler);
		wsCreateHandler = setTimeout(function() {
			console.log("重连...");
			connect();
			console.log("重连完成");
		}, 1000);
	})
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	writeToScreen("disconnected");
}

function writeToScreen(message) {
	if (DEBUG_FLAG) {
		$("#debuggerInfo").val($("#debuggerInfo").val() + "\n" + message);
	}
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg); // 获取url中"?"符后的字符串并正则匹配
	var context = "";
	if (r != null)
		context = r[2];
	reg = null;
	r = null;
	return context == null || context == "" || context == "undefined" ? ""
			: context;
}
