package com.example.wesle.olfriends;

public class Comments {
    public String comment, time, date, username;
    public Comments()
    {

    };

    public Comments(String comment, String time, String date, String username) {
        this.comment = comment;
        this.time = time;
        this.date = date;
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
