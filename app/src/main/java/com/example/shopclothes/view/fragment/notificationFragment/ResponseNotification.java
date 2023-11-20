package com.example.shopclothes.view.fragment.notificationFragment;

import com.example.shopclothes.model.Notification;

import java.util.List;

public class ResponseNotification {
    private String status;
    public List<Notification> notificationList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }
}
