package com.hackernews.listing;

import com.hackernews.pojo.News;

import java.util.List;

/**
 * Created by suyashg on 07/05/18.
 */

public interface NewsListingPresenter {

    void setView(NewsListingView view);

    void destroy();

    List<News> getNewsList();

    void setNewsList(List<News> newsList);

    void onPulledToRefresh();

}
