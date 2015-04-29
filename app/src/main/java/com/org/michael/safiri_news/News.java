package com.org.michael.safiri_news;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import logging.L;

/**
 * Created by Michael on 15/04/2015.
 */
public class News implements Parcelable {
    public static final Parcelable.Creator<News> CREATOR
            = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            L.m("create from parcel :News");
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };
    private String title;
    private String urlNews;
    private String urlImg;
    private String domain;
    private long   timeStamp;
    private Date   publishedDate;


    public News() {

    }

    public News(Parcel input) {
        title = input.readString();
        urlNews = input.readString();
        urlImg = input.readString();
        domain = input.readString();
        timeStamp = input.readLong();
        long dateMillis=input.readLong();
        publishedDate = (dateMillis == -1 ? null : new Date(dateMillis));

    }


    public News(String title,
                String urlNews,
                String urlImg,
                String domain,
                Long timeStamp,
                Date publishedDate) {
        this.title = title;
        this.urlNews = urlNews;
        this.urlImg = urlImg;
        this.domain = domain;
        this.timeStamp = timeStamp;
        this.publishedDate = publishedDate;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlNews() {
        return urlNews;
    }

    public void setUrlNews(String urlNews) {
        this.urlNews = urlNews;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getTimestamp() {
        return timeStamp;
    }

    public void setTimestamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    @Override
    public String toString() {
        return  "\nTitle " + title +
                "\nurlNews " + urlNews +
                "\nurlImage " + urlImg +
                "\nDomain " + domain +
                "\nDate " + publishedDate +
                "\n";
    }

    @Override
    public int describeContents() {
//        L.m("describe Contents News");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        L.m("writeToParcel News");
        dest.writeString(title);
        dest.writeString(urlNews);
        dest.writeString(urlImg);
        dest.writeString(domain);
        dest.writeLong(publishedDate == null ? -1 : publishedDate.getTime());

    }
}
