package com.example.vishnumurthy.vishnufinalproject2;

public class PictureText {
    private String title;
    private String picture;
    public PictureText(String title, String picture) {
        this.title = title;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setYear(String picture) {
        this.picture = picture;
    }
    public String toString(){return title+picture;}
}
