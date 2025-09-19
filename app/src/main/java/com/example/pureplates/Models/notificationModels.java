package com.example.pureplates.Models;

public class notificationModels {

    int notificationImage;
    String notification;

    public notificationModels(int notificationImage, String notification) {
        this.notificationImage = notificationImage;
        this.notification = notification;
    }

    public int getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(int notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
