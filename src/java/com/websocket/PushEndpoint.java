/*
 * This code is part of pirobase PIM.
 * This code is developed by the pirobase imperia gmbh.
 * Do not make any changes.
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * Copyright (C) pirobase imperia gmbh
 * All Rights Reserved.
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */
package com.websocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class PushEndpoint {
    
    private static PushSessionRegistry sessionRegistry = new PushSessionRegistry();
    
    @OnOpen
    public void open(Session session, EndpointConfig config) {
        sessionRegistry.add(session);
        System.out.println("WebSocket opened sessionId: " + session.getId() + ", Number of opened sessions: " + sessionRegistry.getAll().size());
    }
    
    @OnClose
    public void close(Session session, CloseReason reason) {
        sessionRegistry.remove(session);
        System.out.println("WebSocket closed sessionId: " + session.getId() + ", Number of opened sessions: " + sessionRegistry.getAll().size());
    }
    
    @OnError
    public void error(Throwable t) {
        System.out.println("WebSocket error: " + t.getMessage());
    }
    
    @OnMessage
    public void message(Session session, String json) {
        for(Session registeredSession : sessionRegistry.getAll()) {
            try {
                if (registeredSession.getId().equals(session.getId())) {
                    continue;
                }
                registeredSession.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("WebSocket message: Status Code " + json);
    }
    
}
