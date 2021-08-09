package com.example.news;

public class News {
    private final String title;
    private final String description;
    private final String link;
    private final String urlToImage;
    private final String publishedTime;
    private final String author;

    public News(String newsTitle, String newsDescription, String newsImage, String newsLink,  String newsPublishedTime, String newsAuthor){
        title = newsTitle;
        description = newsDescription;
        link =  newsLink;
        urlToImage = newsImage;
        publishedTime = newsPublishedTime;
        author = newsAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return link;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public String getAuthor() {
        return author;
    }
}
