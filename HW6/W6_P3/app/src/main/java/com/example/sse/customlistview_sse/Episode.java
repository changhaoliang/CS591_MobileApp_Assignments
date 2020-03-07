package com.example.sse.customlistview_sse;


public class Episode {
    private String title;
    private String description;
    private Integer image;
    private float rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Episode(String title, String description, Integer image, float rating) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.rating = rating;
    }
}
