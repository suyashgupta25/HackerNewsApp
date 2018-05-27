package com.hackernews.listing;

import android.support.test.espresso.IdlingResource;

import com.hackernews.pojo.News;
import com.hackernews.ui.customviews.PaginationScrollCallbacks;

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

    PaginationScrollCallbacks getPaginationListener();

    boolean isViewAttached();

    IdlingResource getNewsListIdlingResource();

}
