package com.example.tecpie.ZhiheSocket.pojo;

/**
 * Created by AmeKing on 2016/8/1.
 */
public class CustomMessage {
    private String title;
    private String date;
    private String message;

    public CustomMessage(String title, String date, String message) {
        this.title = title;
        this.date = date;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
