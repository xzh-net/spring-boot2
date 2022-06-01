/*
 * 聊天客户端服务
 * @author Kevin
 * @date 2020/12/28 17:35
 */
var chat={
    /* 服务器地址 */
    serverAddress:"ws://" + window.location.hostname + ":1111/ws",
    /*
     * 自定义聊天协议
     *    ProtocolType:
     *     LOGIN:登录
     *     LOGOFF:注销
     *     CHAT:聊天
     *     SYSTEM:系统消息
     */
    protocolType:{login:"LOGIN",logoff:"LOGOFF",chat:"CHAT",system:"SYSTEM"},
    /* webSocket与服务器通讯对象 */
    webSocket:null,
    /* 当前登录用户名称 */
    loginName:null,
    /*
     * 初始化方法
     */
    init:function(){
        // 当前登录人赋值
        chat.loginName=document.getElementById("loginName").value;
        // 按回车发送消息
        chatMessage.listeningKeyBoardEnterEvent();
        // 拖拽聊天窗口
        util.drag(document.getElementsByClassName("chat-head")[0],document.getElementsByClassName("chat-container")[0]);
        // 改变聊天窗口大小
        //util.resize(document.getElementsByClassName("chat-resize-bottom-right")[0],document.getElementsByClassName("chat-container")[0]);
        if(window.WebSocket){
            chat.webSocket= new WebSocket(chat.serverAddress);
            //接收消息回调事件
            chat.webSocket.onmessage=function(e){
                //消息写到页面
                chatMessage.messageWritePage(e.data);
            };
            //连接成功回调事件
            chat.webSocket.onopen=function(e){
                 console.log("连接开启");
                //var loginName=util.getUrlParam("loginName"); //url地址获取
                var loginName=document.getElementById("loginName").value;
                var obj={};
                obj.loginName=loginName;
                obj.protocolType=chat.protocolType.login;
                chat.webSocket.send(JSON.stringify(obj));
            };

            //关闭连接回调事件
            chat.webSocket.onclose=function(e){
                console.log("连接关闭");
            }

            //异常回调事件
            chat.webSocket.onerror=function(e){

            }
        }else{
            alert("你的浏览器不支持WebSocket协议，请更换谷歌或火狐浏览器~~");
        }
    },
    /*
     * 发送消息
     * @param message 消息内容
     */
    send:function(message){
        //判断连接是否打开
        if(chat.webSocket.readyState===WebSocket.OPEN){
            //向服务器发送消息
            var msg=document.getElementById("sendMessage").value;
            // 消息为空则return
            if(chatMessage.isEmpty(msg)) return;
            //var loginName=util.getUrlParam("loginName"); //url地址获取
            var loginName=document.getElementById("loginName").value;
            var obj={};
            obj.message=msg;
            obj.loginName=loginName;
            obj.protocolType=chat.protocolType.chat;
            var sendContext=JSON.stringify(obj);
            chat.webSocket.send(sendContext);
            // 清空消息框
            chatMessage.clearMessageBox();
            //光标保持在发送消息内
            chatMessage.setFocus();
        }else{
            alert("WebSocket连接没有建立成功!");
        }
    }


};

/*
 * 聊天消息处理
 * @author Kevin
 * @date 2020/12/29 11:22
 */
var chatMessage={
    /*
     * 是否为空 undefined|null|空字符串
     * @param obj 判断对象
     * @return true:空; false:非空
     */
    isEmpty:function(obj){
        return (obj===undefined||obj===null||obj==="")?true:false;

    },
    /*
     * 消息写到页面
     * @param responseMsg 服务端返回的消息信息
     */
    messageWritePage:function(responseMsg){
        //console.log(responseMsg);
        // 解析消息
        var msgObj=chatMessage.messageParser(responseMsg);
        // 消息转为html格式字符串
        var msgHtml=chatMessage.messageFormatHtml(msgObj);
        // 重置在线人数
        chatMessage.resetTotal(msgObj);
        // 根据class名获取到消息内容容器，并把msgHtml追加到消息列表中
        var chatContentObj=document.getElementsByClassName("chat-body-content")[0];
        // 消息拼接，并把消息重新载入页面
        chatContentObj.innerHTML=chatContentObj.innerHTML+msgHtml;
        // 设置滚动条到最底部
        chatMessage.setScrollBarBottom();
        // 未读消息提醒
        this.unreadMessageReminder(msgObj);
    },
    /*
     * 未读消息提醒
     * @param msgObj 消息对象信息
     */
    unreadMessageReminder:function(msgObj){
        //消息提醒
        if(document.hidden){
            console.log("未读消息提醒~");
            browser.Title.setTitle(msgObj.message);
            browser.Event.listenerVisibilityChange();
            //browser.Title.marquee();
            browser.Title.newMsg();
        }
    }
    ,
    /*
     * 重置在线人数
     * @param msgObj 消息对象信息
     */
    resetTotal:function(msgObj){
        if(msgObj.total){
            document.getElementById("total").innerHTML=msgObj.total;
        }
    }
    ,
    /*
     * 消息解析器
     * @param responseMsg 消息字符串
     * @return 消息对象
     */
    messageParser:function(responseMsg){
        return JSON.parse(responseMsg);
    },
    /*
     * 消息转为html格式字符串
     * @param responseMsg 消息对象
     * @return 拼装好的消息字符串
     */
    messageFormatHtml:function(msg){
        var _html='';
        // 自己上线不通知
        if(msg.protocolType==chat.protocolType.system&&msg.loginName==chat.loginName) return _html;
        if(msg.protocolType==chat.protocolType.system){
            _html+=' <div class="chat-system-info"><span class="msg">'+msg.message+'</span></div> ';
        }else{
            var msgTitleClass=chat.loginName==msg.loginName?"chat-msg-title-me":"chat-msg-title-other";
            _html+=' <ul class="chat-msg"> ';
            _html+='     <li class="'+msgTitleClass+'">';
            _html+='        <span class="chat-name">'+msg.loginName+'</span>';
            _html+='        <span class="chat-date">'+msg.date+'</span>';
            _html+='     </li>';
            _html+='     <li class="chat-msg-content">'+msg.message+'</li>';
            _html+=' </ul>';
        }
        return _html;
    },
    /*
     * 设置滚动条到最底部
     */
    setScrollBarBottom:function(){
        var element=document.getElementsByClassName("chat-body-content")[0];
        element.scrollTop=element.scrollHeight;
    },
    /*
     * 光标保持在发送消息内
     */
    setFocus:function(){
        document.getElementById("sendMessage").focus();
    },
    /*
     *  清空消息框
     */
    clearMessageBox:function(){
        document.getElementById("sendMessage").value="";
    },
    /*
     *  监听键盘回车事件
     */
    listeningKeyBoardEnterEvent:function(){
        document.onkeydown=function(event){
            if(event.keyCode==13){
                // 发送消息
                chat.send();
            }
        }
    }
};

/*
 * 聊天窗体
 * @author Kevin
 * @date 2020/12/29 11:22
 */
var chatWin={
    chatContainer:document.getElementsByClassName("chat-container")[0],
    /* 窗口最大化 */
    max:function(){
        util.maxChatWindow(chatWin.chatContainer);
    },
    /* 窗口关闭 */
    close:function(){
        if(window.confirm("退出聊天？")){
             window.location.href="/";
        }
    }
}
