package com.demo.student.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final long sessionExpiration;

    private final Map<String, Long> userSessions = new ConcurrentHashMap<>();

    public SessionManager(@Value("${session.expiration}") long sessionExpiration) {
        this.sessionExpiration = sessionExpiration;
    }

    public void createSession(String username) {
        userSessions.put(username, System.currentTimeMillis());
    }

    public boolean isSessionExpired(String username) {
        Long lastActiveTime = userSessions.get(username);
        if (lastActiveTime == null) {
            return true; // No session exists
        }
        return System.currentTimeMillis() - lastActiveTime > sessionExpiration;
    }

    public void refreshSession(String username) {
        userSessions.put(username, System.currentTimeMillis());
    }

    public void invalidateSession(String username) {
        userSessions.remove(username);
    }
}