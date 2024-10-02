package org.example.webtoonepics.main.dto;

public class MainWebtoonDto {

    private String title;
    private String imageurl;

    public MainWebtoonDto(String title, String imageurl) {
        this.title = title;
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageurl() {
        return imageurl;
    }
}
