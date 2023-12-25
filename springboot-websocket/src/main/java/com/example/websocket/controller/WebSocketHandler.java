package com.example.websocket.controller;

import com.example.websocket.utils.WebSocketUtil;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * websocket核心对话框
 * @author booty
 * @date 2021/5/21 10:54
 */
//表示接受的是STOMP协议提交的数据
@ServerEndpoint("/WebSocketHandler/{userName}")
@RestController
public class WebSocketHandler {

    /**
     * 建立链接
     * @param userName 用户名
     * @param session session会话
     */
    @OnOpen
    public void openSession(@PathParam("userName")String userName, Session session) {
        //消息
        String message = "欢迎:"+userName+"加入群聊";
        //加入聊天室
        WebSocketUtil.MESSAGE_MAP.put(userName, session);
        //发送消息
        WebSocketUtil.sendMessageToAll(message);
    }

    /**
     * 收到消息
     * @param userName 用户名
     * @param message 收到的消息
     */
    @OnMessage
    public void onMessage(@PathParam("userName")String userName,String message) {
        message = userName+":"+message;
        WebSocketUtil.sendMessageToAll(message);
    }

    /**
     * 断开链接
     * @param userName 用户名
     * @param session 会话
     */
    @OnClose
    public void onClose(@PathParam("userName")String userName,Session session) {
        //将当前用户从map中移除 注销
        WebSocketUtil.MESSAGE_MAP.remove(userName);
        //群发消息
        WebSocketUtil.sendMessageToAll("用户:"+userName+"离开聊天室");
        //关闭session
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接异常
     * @param session 会话
     * @param throwable 抛出的异常
     */
    @OnError
    public void onError(Session session,Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = throwable.getMessage();
        System.out.println(message);

    }


}
