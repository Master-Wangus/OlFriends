package com.example.wesle.olfriends;

public class Guide {

    private int id;
    private String title, desc;
    private int image;


    public Guide(int id, String title, String desc, int image) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getImage() {
        return image;
    }

}
