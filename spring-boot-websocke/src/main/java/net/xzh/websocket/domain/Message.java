package net.xzh.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	// 消息id
	public String msgId;
	// 消息类型
	public Type type;
	// 数据的类型
	public DataType dataType; // 文本，图片，表情 | 语音，视频，文件 | 位置，卡片 | 上线，离线
	// 发送的文本
	public String body;
	// 发送者name
	public String from;
	// 接收者name
	public String to;
	// 发送时间
	public Long sentTime;
	// 发送状态
	public String status;
	
	public enum DataType {
		txt,
		online,
		offline,
	}
	
	public enum Type {

        /**
         * broadcast message used in email like interface.
         */
		broadcast,

        /**
         * Typically short text message used in line-by-line chat interfaces.
         */
        chat,

        /**
         * Chat message sent to a groupchat server for group chats.
         */
        groupchat,

        /**
         * Text message to be displayed in scrolling marquee displays.
         */
        headline,

        /**
         * Indicates a messaging error.
         */
        error
    }

}
