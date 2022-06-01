/*
 * 通用工具处理js
 * @author Kevin
 * @date 2020/12/28 17:38
 */
var util={
    /*
     * 获取url参数
     * @param key 键
     */
    getUrlParam:function(key){
        //获取url?后面字符串
        var query=window.location.search.substring(1);
        // 按"&" 分割，得到键值对字符串 例如：【userName=Kevin】
        var params=query.split("&");
        for(var i=0;i<params.length;i++){
            var pair=params[i].split("=");
            if(pair[0]==key) return pair[1];
        }
        return null;
    },

    /*
     * 拖拽方法
     * @param dragElement 要拖拽的元素
     * @param moveTarget 真正移动的目标对象
     */
    drag:function(dragElement,moveTarget){
        // 鼠标按下标识
        var isMouseDown=false;
        // 元素左顶点与窗体左顶点距离值
        var left=0;
        // 元素顶点与窗体顶点距离值
        var top=0;
        // 鼠标按下X坐标值
        var mouseDownX=0;
        // 鼠标按下Y坐标值
        var mouseDownY=0;
        /* 监听元素区域鼠标按下事件 */
        dragElement.onmousedown=function(e){
            dragElement.style.cursor="move";
            isMouseDown=true;
            // 获取x坐标和y坐标
            mouseDownX=e.clientX;
            mouseDownY=e.clientY;
            // 获取左部和顶部的偏移量
            left=moveTarget.offsetLeft;
            top=moveTarget.offsetTop;
        }
        /* 监听鼠标移动事件 */
        document.onmousemove=function(e){
            if(!isMouseDown) return;
            var newX=e.clientX;
            var newY=e.clientY;
            // 计算移动后的左偏移量和顶部的偏移量
            var newLeft = newX - (mouseDownX-left);
            var newTop = newY - (mouseDownY-top);
            moveTarget.style.left=newLeft+"px";
            moveTarget.style.top=newTop+"px";
        }
        /* 监听元素区域鼠标抬起事件 */
        dragElement.onmouseup=function(e){
            dragElement.style.cursor="default";
            isMouseDown=false;
        }
    },
    /*
     * 改变窗口大小
     * @param element 可改变位置元素
     * @param resizeTarget 真正改变大小的目标对象
     */
    resize:function(resizeElement,resizeTarget){
        window.onload=window.onresize=function(){
            // 鼠标按下标识
            var isMouseDown1=false;
            /* 监听元素区域鼠标按下事件 */
            resizeElement.onmousedown=function(e){
                isMouseDown1=true;
            }
            /* 监听鼠标移动事件 */
            document.onmousemove=function(e){
                if(!isMouseDown1) return;
                resizeTarget.left=(document.documentElement.clientWidth-resizeTarget.offsetWidth+50)+"px";

            }
            /* 监听鼠标抬起事件 */
            document.onmouseup=function(e){
                isMouseDown1=false;
            }


        }
    },
    /*
     * 最大化聊天窗口
     * @param elementTarget 最大化目标元素
     */
    maxChatWindow:function(elementTarget){
        var winWidth=document.documentElement.clientWidth;
        var winHeight=document.documentElement.clientHeight;
        elementTarget.style.width=winWidth-20+"px";
        elementTarget.style.height=winHeight-20+"px";
    }
};
/*
 * 浏览器对象
 * @author Kevin
 * @date 2021/1/2 14:25
 */
var browser={
    Title:{
        currentTitle:document.title,
        messageTitle:null,
        count:0,
        currentExecutionEvent:null,
        isCurrentExecutionEvent:false,
        setTitle:function(text){
            this.count=0;
            document.title=text;
            this.messageTitle=text;
        },
        marquee:function(){
                var len=this.messageTitle.length;
                var newTitle=this.messageTitle.substring(this.cougitnt,len);
                document.title=newTitle;
                console.log("marquee-->title=="+document.title+":this.currentExecutionEvent===>"+this.currentExecutionEvent);
                this.count++;
                if(this.count==this.messageTitle.length){
                    this.count=0;
                }
                console.log(new Date());
                clearTimeout(this.currentExecutionEvent);
                this.currentExecutionEvent=setTimeout("browser.Title.marquee()",10);
        },
        newMsg:function(){
            clearTimeout(this.currentExecutionEvent);
            this.currentExecutionEvent=setTimeout("browser.Title.newMsg()",200);
            var msgs=["【您有新的消息】","【　　   】"];
            document.title=document.title==msgs[0]?document.title=msgs[1]:document.title=msgs[0];
        }
    },
    Event:{
        isListenerVisibilityChange:false,
        listenerVisibilityChange:function(){
            if(this.isListenerVisibilityChange) return;
            this.isListenerVisibilityChange=true
            document.addEventListener("visibilitychange",function(){
                if(!document.hidden){
                    document.title=browser.Title.currentTitle;
                    browser.Event.clearTimeOutEvent(browser.Title.currentExecutionEvent);
                }
            });
        },
        clearTimeOutEvent:function(eObj){
            if(eObj){
                browser.Title.currentExecutionEvent=null;
                clearTimeout(eObj);
            }
        }
    }
}