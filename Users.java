package com.example.shivani.shivani;

/**
 * Created by shivani on 7/7/17.
 */

public class Users {
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getthumburl() {

        return thumbnailUrl;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title=title;
    }
    public void setUrl(String url) {
        this.url=url;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl=thumbnailUrl;
    }

}
