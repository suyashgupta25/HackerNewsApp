package com.hackernews.listing;

import com.hackernews.pojo.News;

/**
 * Created by suyashg on 07/05/18.
 */

public interface NewsListingView {

    void loadingStarted();

    void loadingFailed(String errorMessage);

    void updateNewsList(News news);

    void onNewsClicked(News news);

    void clearNewsList();
}
