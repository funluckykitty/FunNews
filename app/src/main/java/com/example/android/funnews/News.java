package com.example.android.funnews;

import android.graphics.Bitmap;

//LSB 7/22/18 - Details about an Article
public class News {
    private String mWebTitle;
    private String mSectionName;
    private String mAuthor;
    private String mWebPublicationDate;
    private String mWebUrl;
    private Bitmap mAppImage;


    //7/22/18 - LSB - Constructor
    //7/27/18 - LSB - Remember the order is imporant!
    public News(String webTitle, String sectionName, String author, String webPublicationDate, String webUrl, Bitmap appImage){
        mWebTitle = webTitle;
        mSectionName = sectionName;
        mAuthor = author;
        mWebPublicationDate= webPublicationDate;
        mWebUrl = webUrl;
        mAppImage = appImage;
    }

    //7/22/18 - LSB - Returns the data
    public String getWebTitle() {
        return mWebTitle;
    }
    public String getSectionName() {
        return mSectionName;
    }
    public String getAuthor() {
        return mAuthor;
    }
    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }
    public String getWebUrl(){
        return mWebUrl;
    }
    public Bitmap getImage(){
        return mAppImage;
    }
}