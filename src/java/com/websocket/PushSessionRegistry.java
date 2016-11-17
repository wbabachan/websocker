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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

public class PushSessionRegistry {
    
    private final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    
    public synchronized Set<Session> getAll() {
        return Collections.unmodifiableSet(this.sessions);
    }
    
    public synchronized void add(Session session) {
        sessions.add(session);
    }
    
    public synchronized void remove(Session session) {
        sessions.remove(session);
    }
    
}
