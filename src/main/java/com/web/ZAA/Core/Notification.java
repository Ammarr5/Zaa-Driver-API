package com.web.ZAA.Core;

import java.sql.Timestamp;

public class Notification {
    String title;
    Timestamp time;
    Notifiable payload;
    boolean isRead;

    public Notification(String title, Timestamp time, Notifiable payload) {
        this.title = title;
        this.time = time;
        this.payload = payload;
        isRead = false;
    }

    @Override
    public String toString() {
        return title + (isRead? "" : "*");
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getTime() {
        return time;
    }

    public Notifiable getPayload() {
        return payload;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setPayload(Notifiable payload) {
        this.payload = payload;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
