package com.hackernews.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hackernews.R;
import com.hackernews.details.NewsDetailsFragment;
import com.hackernews.listing.NewsListFragment;
import com.hackernews.pojo.News;

/**
 * Created by suyashg on 06/05/18.
 */

public class NewsListActivity extends AppCompatActivity implements NewsListFragment.Callback {

    public static final String TAG = NewsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        setToolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_news_container, new NewsListFragment())
                .commit();
      }

    private void setToolbar() {
        Log.d(TAG, "setToolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.news_list);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void loadDetailsFragment(News news) {
        Log.d(TAG, "loadDetailsFragment");
        NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.getInstance(news);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_news_container, newsDetailsFragment, NewsDetailsFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onNewsLoaded(News news) {
        Log.d(TAG, "onNewsLoaded");
    }

    @Override
    public void onNewsClicked(News news) {
        Log.d(TAG, "onNewsClicked");
        loadDetailsFragment(news);
    }
}
