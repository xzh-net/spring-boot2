package net.xzh.chat.server;


/**
 * 自定义聊天 协议
 * @author Kevin
 * @date 2020/12/28 13:00
 *
 */
public enum ChatProtocol {
    /*登录指令*/
    LOGIN,
    /*退出指令*/
    LOGOUT,
    /*聊天消息*/
    CHAT,
    /*系统消息*/
    SYSTEM;


}
