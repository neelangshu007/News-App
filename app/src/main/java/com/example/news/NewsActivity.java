package com.example.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.content.AsyncTaskLoader;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final String LOG_TAG = NewsActivity.class.getName();

    private final String NEWS_REQUEST_URL = "https://newsdata.io/api/1/news?apikey=pub_714a28d684b4579eeb106c98494e3db8973&country=in";

    private NewsAdapter adapter;

    private TextView mEmptyStateTextView;

    private static final int NEWS_LOADER_ID = 1;

    SwipeRefreshLayout swipeRefreshLayout;

    View loadingIndicator ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF03DAC5"));

        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

        ListView newsListView = findViewById(R.id.list);

        adapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(adapter);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        loadingIndicator = findViewById(R.id.loading_indicator);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }else{

            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No Internet Connection");
        }

        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(networkInfo != null && networkInfo.isConnected()){
                    LoaderManager loaderManager = getLoaderManager();

                    loaderManager.initLoader(NEWS_LOADER_ID, null, NewsActivity.this);

                }else{
                    loadingIndicator.setVisibility(View.GONE);

                    mEmptyStateTextView.setText("No Internet Connection");

                    Toast.makeText(NewsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = adapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(websiteIntent);

            }
        });

    }


    @Override
    public Loader<List<News>> onCreateLoader(int i,Bundle bundle) {
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }


    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText("No News Found");

        adapter.clear();

        if(news != null && !news.isEmpty()){
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

    public static class NewsLoader extends AsyncTaskLoader<List<News>>{
        private static final String LOG_TAG  = NewsLoader.class.getName();

        private String mUrl;

        public NewsLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading(){
            forceLoad();
        }


        @Override
        public List<News> loadInBackground() {
            if(mUrl == null){
                return null;
            }

            List<News> news = QueryUtils.fetchNewsData(mUrl);
            return news;
        }
    }




}