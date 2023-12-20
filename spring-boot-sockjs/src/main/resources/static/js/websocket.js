var stompClient = null;
var wsCreateHandler = null;
var userId = null;

function connect() {
	var host = window.location.host; // 带有端口号
	userId = GetQueryString("userId");
	var socket = new SockJS("http://" + host + "/websocket");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		$("#log-container").append("<div class='text-success'>" + frame + "</div></div><br>");
		// 服务端广播
		stompClient.subscribe('/topic/broadcast', function(response) {
			$("#log-container").append("<div class='bg-info'><div class='text-success'><label class='text-danger'>服务端广播：</label>" + response.body + "</div></div><br>");
		});
		
		// 前端广播
		stompClient.subscribe('/topic/web', function(response) {
			$("#log-container").append("<div class='bg-info'><div class='text-success'><label class='text-danger'>前端广播：</label>" + response.body + "</div></div><br>");
		});
		
		// 点对点
		stompClient.subscribe("/queue/user_" + userId, function(response) {
			var data = JSON.parse(response.body);
			$("#log-container").append("<div class='bg-info'><label class='text-danger'>" + data.from + ":</label><div class='text-success'>" + data.body + "</div></div><br>");
		});
		
		// 订阅，一般只有订阅的时候在返回
		stompClient.subscribe("/app/subscribe/" + userId, function(response) {
			$("#user").text(response.body);
			$("#log-container").append("<div class='bg-info'><div class='text-success'>" + userId + " 登录成功" + "</div></div><br>");
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

function scrollToBottom() {
    var div = document.getElementById('log-container');
    div.scrollTop = div.scrollHeight;
}