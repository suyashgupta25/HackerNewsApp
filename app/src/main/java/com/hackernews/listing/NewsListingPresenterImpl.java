package com.hackernews.listing;

import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.hackernews.pojo.News;
import com.hackernews.ui.customviews.PaginationScrollCallbacks;
import com.hackernews.utils.AppConstants;
import com.hackernews.utils.RxUtils;
import com.hackernews.utils.SimpleIdlingResource;
import com.hackernews.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by suyashg on 07/05/18.
 */

public class NewsListingPresenterImpl implements NewsListingPresenter, PaginationScrollCallbacks {

    private static final String TAG = NewsListingPresenterImpl.class.getSimpleName();
    private SimpleIdlingResource newsListIdlingResource;
    private NewsListingView view;
    private NewsListingInteractor newsListingInteractor;
    private Disposable fetchSubscription;
    private List<News> loadedNewsList = new ArrayList<>();
    private int pageIndex;
    private boolean isLoadingItems = false;
    private boolean isEndReached = false;

    public NewsListingPresenterImpl(NewsListingInteractor newsListingInteractor) {
        this.newsListingInteractor = newsListingInteractor;
    }

    @Override
    public void setView(NewsListingView view) {
        this.view = view;
        displayNewsList();
    }

    @Override
    public void destroy() {
        view = null;
        RxUtils.unsubscribe(fetchSubscription);
    }

    private void displayNewsList() {
        showLoading();
        fetchNews();
    }

    private void fetchNews() {
        if (newsListIdlingResource == null) {
            newsListIdlingResource = new SimpleIdlingResource();
            newsListIdlingResource.setIdleState(false);
        }
        isLoadingItems = true;
        fetchSubscription = newsListingInteractor.fetchTopNews(AppConstants.NO_PAGES)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNewsFetchSuccess, this::onNewsFetchFailed);
    }

    private void onNewsFetchSuccess(List<Long> newsIds) {
        if (newsIds != null && !newsIds.isEmpty()) {
            if (newsListingInteractor.isPaginationSupported()) {
                newsListingInteractor.setAllNewsList(newsIds);
                fetchItemsWithPageIndex(Utils.getElementsFromList(newsIds, pageIndex, AppConstants.PAGE_SIZE_NEWS_LIST));
            } else {
                fetchItemsWithPageIndex(newsIds);
            }
        } else {
            RxUtils.unsubscribe(fetchSubscription);
            onNewsFetchFailed(new IllegalStateException(AppConstants.ERR_EMPTY_LIST));
        }
    }

    private void fetchItemsWithPageIndex(List<Long> elementsFromList) {
        isLoadingItems = true;
        if(elementsFromList.size() < AppConstants.PAGE_SIZE_NEWS_LIST) {
            isEndReached = true;
            fetchSubscription = newsListingInteractor.fetchNewsSequentially(elementsFromList)
                    .subscribe(this::onNewsListAddition, this::onNewsFetchItemFailed, complete);
        } else {
            fetchSubscription = newsListingInteractor.fetchNewsSequentially(elementsFromList)
                    .subscribe(this::onNewsListAddition, this::onNewsFetchItemFailed, complete);
        }
    }

    private Action complete = new Action() {
        @Override
        public void run() throws Exception {
            isLoadingItems = false;
            if (newsListIdlingResource != null) {
                newsListIdlingResource.setIdleState(true);
            }

        }
    };

    private void onNewsListAddition(News news) {
        Log.d(TAG, "onNewsListAddition newsId ="+ news.getId());
        if(isLoadingItems) {
            loadedNewsList.add(news);
            if (isViewAttached()) {
                view.updateNewsList(news);
            }
        }
    }

    private void onNewsFetchItemFailed(Throwable e) {
        Log.e(TAG, "onNewsFetchItemFailed message ="+e.getMessage());
    }

    private void showLoading() {
        if (isViewAttached()) {
            view.loadingStarted();
        }
    }

    @Override
    public void onPulledToRefresh() {
        RxUtils.unsubscribe(fetchSubscription);
        isLoadingItems = false;
        loadedNewsList.clear();
        newsListingInteractor.getAllNewsList().clear();
        pageIndex = 0;
        isEndReached = false;
        if(view != null)
            view.clearNewsList();
        fetchNews();
    }

    private void onNewsFetchFailed(Throwable e) {
        if(view != null)
            view.loadingFailed(e.getMessage());
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public IdlingResource getNewsListIdlingResource() {
        return newsListIdlingResource;
    }

    @Override
    public void loadMoreItemsInList() {
        fetchItemsWithPageIndex(Utils.getElementsFromList(newsListingInteractor.getAllNewsList(),
                ++pageIndex, AppConstants.PAGE_SIZE_NEWS_LIST));
    }

    @Override
    public boolean isLastPage() {
        return isEndReached;
    }

    @Override
    public boolean isLoading() {
        return isLoadingItems;
    }

    @Override
    public PaginationScrollCallbacks getPaginationListener() {
        return this;
    }

    @Override
    public List<News> getNewsList() {
        return loadedNewsList;
    }

    @Override
    public void setNewsList(List<News> newsList) {
        this.loadedNewsList = newsList;
    }
}
