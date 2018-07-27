package com.example.android.funnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

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
       // TextView webUrlView = (TextView) listItemView.findViewById(R.id.webUrl);

        News currentNews = getItem(position);

        String webTitle = currentNews.getWebTitle();
        webTitleView.setText(webTitle);

        String sectionName = currentNews.getSectionName();
        sectionNameView.setText(sectionName);

        String author = currentNews.getAuthor();
        authorView.setText(author);

        String webPublicationDate = currentNews.getWebPublicationDate();
        webPublicationDateView.setText(webPublicationDate);

       // String webUrl = currentNews.getWebUrl();
        //webUrlView.setText(webUrl);


        return listItemView;
    }
}
