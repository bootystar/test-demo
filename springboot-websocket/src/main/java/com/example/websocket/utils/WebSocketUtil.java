package com.example.websocket.utils;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author booty
 * @date 2021/5/21 10:55
 */
public class WebSocketUtil {
    //储存所有在线客户端
    public static final Map<String, Session> MESSAGE_MAP = new ConcurrentHashMap<>();

    //发送消息给客户端
    public static void sendMessage(Session session, String message) {
        if (session!=null) {
            //获取远程端点
            final RemoteEndpoint.Basic basic = session.getBasicRemote();
            if (basic!=null) {
                try {
                    //发送消息回客户端
                    basic.sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //发送给所有人
    public static void sendMessageToAll(String message) {
        //遍历Map取出session，并向其发送消息
        for (Map.Entry<String, Session> entry : MESSAGE_MAP.entrySet()) {
            Session session = entry.getValue();
            sendMessage(session, message);
        }
    }

}
