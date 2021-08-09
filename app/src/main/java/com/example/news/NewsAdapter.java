package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0, news);
    }


    public View getView(int position, View convertView , ViewGroup parent){
        View listItemView  = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        String articleAuthor;

        TextView titleTextView = listItemView.findViewById(R.id.title);
        titleTextView.setText(currentNews.getTitle());

        TextView descriptionTextView = listItemView.findViewById(R.id.description);
        if(currentNews.getDescription().equals("null")){
            descriptionTextView.setText("Description");
        }
        else{
            descriptionTextView.setText(currentNews.getDescription());
        }

        TextView timeTextView = listItemView.findViewById(R.id.time);
        timeTextView.setText("Published At :- " + currentNews.getPublishedTime());

        ImageView imageView = listItemView.findViewById(R.id.imageView);
        if(!currentNews.getUrlToImage().equals("null")) {
            Glide.with(getContext()).load(currentNews.getUrlToImage()).into(imageView);
        }
        else{
            Glide.with(getContext()).load("https://kare.ee/images/no-image.jpg").into(imageView);
        }

        TextView authorTextView = listItemView.findViewById(R.id.author);
        if(currentNews.getAuthor().equals("null")){
            authorTextView.setText("Author");
        }
        else{
            articleAuthor = currentNews.getAuthor().replaceAll("\"", "");
            articleAuthor = articleAuthor.replace("[", "");
            articleAuthor = articleAuthor.replace("]", "");
            authorTextView.setText(articleAuthor);
        }


        return listItemView;

    }

}
