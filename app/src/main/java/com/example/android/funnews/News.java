package com.example.android.funnews;

//LSB 7/22/18 - Details about an Article
public class News {
    private String mWebTitle;
    private String mSectionName;
    private String mAuthor;
    private String mWebPublicationDate;
    private String mWebUrl;


    //7/22/18 - LSB - Constructor
    public News(String webTitle, String sectionName, String author, String webPublicationDate, String webUrl){
        mWebTitle = webTitle;
        mSectionName = sectionName;
        mAuthor = author;
        mWebPublicationDate= webPublicationDate;
        mWebUrl = webUrl;
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


}