package com.example.android.funnews;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;


public class NewsAdapter extends ArrayAdapter<News> {

    private static final String REMOVETIME = "T";

    public NewsAdapter(Context context, List<News> article) {
        super(context, 0, article);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 7/22/18 LSB - Resuse list if we can or inflate a new one.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        TextView webTitleView = (TextView) listItemView.findViewById(R.id.webTitle);
        TextView sectionNameView = (TextView) listItemView.findViewById(R.id.sectionName);
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        TextView webPublicationDateView = (TextView) listItemView.findViewById(R.id.webPublicationDate);
        ImageView appImageView = (ImageView) listItemView.findViewById(R.id.appImageView);
        //7/27/18 - LSB - We don't want to put the long url in there, so no need to add it as a TextView
        // TextView webUrlView = (TextView) listItemView.findViewById(R.id.webUrl);

        News currentNews = getItem(position);

        String webTitle = currentNews.getWebTitle();
        webTitleView.setText(webTitle);

        String sectionName = currentNews.getSectionName();
        sectionNameView.setText(sectionName);

        String author = currentNews.getAuthor();
        authorView.setText(author);

        if (currentNews.getImage() != null) {
            Bitmap appImage = currentNews.getImage();
            appImageView.setImageBitmap(appImage);
            appImageView.setVisibility(ImageView.VISIBLE);
        } else {
            appImageView.setVisibility(ImageView.GONE);
        }


        String webPublicationDate = currentNews.getWebPublicationDate();
        //7/27/18 - LSB - We don't want to put the long url in there, so no need to add it as a TextView
        // String webUrl = currentNews.getWebUrl();
        //webUrlView.setText(webUrl);

        //7/27/18 - LSB - Remove the time from the Date/Time Values
        //7/27/18 - LSB - Learned lots about date/time  on the Slack ABND Bulletin Board
        if (webPublicationDate.contains(REMOVETIME)) {
            String[] parts = webPublicationDate.split(REMOVETIME);
            webPublicationDate = parts[0];
        }

        // 7/27/18 - LSB - Great article about formating date  https://stackoverflow.com/questions/10426492/change-date-string-format-in-android
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = formattedDate.parse(webPublicationDate);
        } catch (ParseException e) {

        }
        webPublicationDate = formattedDate.format(newDate);

        webPublicationDateView.setText(webPublicationDate);

        return listItemView;
    }
}
