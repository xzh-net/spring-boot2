var client = null;
var wsCreateHandler = null;
var userId = null;

function connect() {
	var host = window.location.host; // 带有端口号
	var socket = new SockJS("http://" + host + "/ws");
	client = Stomp.over(socket);
	var token = window.localStorage.getItem("token");
	
	client.connect({'token': token}, function(frame) {
		console.log("连接成功");
		$("#log-container").append("<div class='text-success'>" + frame + "</div></div><br>");
		userId = frame.headers['user-name']
		$("#user").text(userId);
		// 服务端广播
		client.subscribe('/topic/chat', function(response) {
			$("#log-container").append("<div class='bg-info'><div class='text-success'><label class='text-danger'>服务端广播：</label>" + response.body + "</div></div><br>");
		});
		
		// 前端广播
		client.subscribe('/topic/web', function(response) {
			$("#log-container").append("<div class='bg-info'><div class='text-success'><label class='text-danger'>前端广播：</label>" + response.body + "</div></div><br>");
		});
		
		// 点对点
		client.subscribe("/queue/user_" + userId, function(response) {
			var data = JSON.parse(response.body);
			$("#log-container").append("<div class='bg-info'><label class='text-danger'>" + data.fromUserId + ":</label><div class='text-success'>" + data.msg + "</div></div><br>");
		});
		
	}, function(error) {
		wsCreateHandler && clearTimeout(wsCreateHandler);
		wsCreateHandler = setTimeout(function() {
			console.log("重连...");
			connect();
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