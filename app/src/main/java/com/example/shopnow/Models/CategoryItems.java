package com.example.shopnow.Models;

public class CategoryItems {

    private String title,picUrl;
    private int id;

    public CategoryItems(String title, String picUrl, int id) {
        this.title = title;
        this.picUrl = picUrl;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
