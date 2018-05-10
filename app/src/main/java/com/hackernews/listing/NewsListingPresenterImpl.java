package com.hackernews.listing;

import android.util.Log;

import com.hackernews.pojo.News;
import com.hackernews.utils.AppConstants;
import com.hackernews.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by suyashg on 07/05/18.
 */

public class NewsListingPresenterImpl implements NewsListingPresenter {

    private static final String TAG = NewsListingPresenterImpl.class.getSimpleName();
    private NewsListingView view;
    private NewsListingInteractor newsListingInteractor;
    private Disposable fetchSubscription;
    private List<News> loadedNewsList = new ArrayList<>();

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
        fetchSubscription = newsListingInteractor.fetchTopNews(AppConstants.NO_PAGES)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNewsFetchSuccess, this::onNewsFetchFailed);
    }

    private void onNewsFetchSuccess(List<Long> newsIds) {
        if (newsIds != null && !newsIds.isEmpty()) {
            fetchSubscription = newsListingInteractor.fetchNewsSequentially(newsIds)
                    .subscribe(this::onNewsListAddition, this::onNewsFetchItemFailed);
        } else {
            RxUtils.unsubscribe(fetchSubscription);
            onNewsFetchFailed(new IllegalStateException(AppConstants.ERR_EMPTY_LIST));
        }
    }

    private void onNewsListAddition(News news) {
        Log.e(TAG, "onNewsListAddition newsId ="+ news.getId());
        loadedNewsList.add(news);
        if (isViewAttached()) {
            view.updateNewsList(news);
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
    public List<News> getNewsList() {
        return loadedNewsList;
    }

    @Override
    public void setNewsList(List<News> newsList) {
        this.loadedNewsList = newsList;
    }

    @Override
    public void onPulledToRefresh() {
        RxUtils.unsubscribe(fetchSubscription);
        loadedNewsList.clear();
        if(view != null)
            view.clearNewsList();
        fetchNews();
    }

    private void onNewsFetchFailed(Throwable e) {
        if(view != null)
            view.loadingFailed(e.getMessage());
    }


    private boolean isViewAttached() {
        return view != null;
    }
}
