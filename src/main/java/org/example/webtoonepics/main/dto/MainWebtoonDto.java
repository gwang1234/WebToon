package org.example.webtoonepics.main.dto;

public class MainWebtoonDto {

    private Long id;
    private String title;
    private String imageurl;

    public MainWebtoonDto(Long id, String title, String imageurl) {
        this.id = id;
        this.title = title;
        this.imageurl = imageurl;
    }

    public Long getId() {return id;}
    public String getTitle() {
        return title;
    }

    public String getImageurl() {
        return imageurl;
    }
}
