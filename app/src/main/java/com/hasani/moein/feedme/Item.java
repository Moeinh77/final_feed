package com.hasani.moein.feedme;

import java.io.Serializable;

/**
 * Created by Moein on 8/1/2017.
 */


public class Item implements Serializable {

    String title;
    String description;
    String pubdate;
    String imageURL;
    String URL_ADDRESS;

    public String getURL_ADDRESS() {
        return URL_ADDRESS;
    }

    public void setURL_ADDRESS(String URL_ADDRESS) {
        this.URL_ADDRESS = URL_ADDRESS;
    }

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

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return  "title=" + title + '\n' +
                ", Description=" + description + '\n' +
                ", pubdate=" + pubdate + '\n' +
                ", URL_address=" + URL_ADDRESS + '\n' +
                ", imageURL=" + imageURL + '\n' ;
    }
}

