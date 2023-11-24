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
			$("#log-container").append("<div class='bg-info'><label class='text-danger'>" + "服务端广播：" + "&nbsp;" + "</label><div class='text-success'>" + response.body + "</div></div><br>");
		});
		
		// 前端广播
		stompClient.subscribe('/topic/web', function(response) {
			$("#log-container").append("<div class='bg-info'><label class='text-danger'>" + "前端广播：" + "&nbsp;" + "</label><div class='text-success'>" + response.body + "</div></div><br>");
		});
		
		// 点对点
		stompClient.subscribe("/user/" + userId + "/topic", function(response) {
			var data = JSON.parse(response.body);
			$("#log-container").append("<div class='bg-info'><label class='text-danger'>" + data.from + "&nbsp;" + transformDate(data.sentTime) + "</label><div class='text-success'>" + data.body + "</div></div><br>");
		});
		
		// 订阅，一般只有订阅的时候在返回
		stompClient.subscribe("/subscribe/" + userId, function(response) {
			$("#user").text(response.body);
			$("#log-container").append("<div class='bg-info'><label class='text-danger'>" + new Date().format("yyyy-MM-dd hh:mm:ss") + "&nbsp;" + "</label><div class='text-success'>" + userId + " 登录成功" + "</div></div><br>");
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

function transformDate(timestamp) {
	let date = new Date(timestamp);
	let y = date.getFullYear();
	let m = date.getMonth() + 1; //月份从0开始，需要加1
	m = m < 10 ? ('0' + m) : m   
	let d = date.getDate();
	d = d < 10 ? ('0' + d) : d  
	let hh = date.getHours();
	hh = hh < 10 ? ('0' + hh) : hh; 
	let mm = date.getMinutes();
	let ss = date.getSeconds();
	mm = mm < 10 ? ('0' + mm) : mm;
	ss = ss < 10 ? ('0' + ss) : ss;
	return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
}

function scrollToBottom() {
    var div = document.getElementById('log-container');
    div.scrollTop = div.scrollHeight;
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}
	