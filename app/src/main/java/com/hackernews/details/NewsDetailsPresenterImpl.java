package com.hackernews.details;

import android.util.Log;

import com.hackernews.pojo.News;
import com.hackernews.pojo.NewsComment;
import com.hackernews.utils.AppConstants;
import com.hackernews.utils.RxUtils;
import com.hackernews.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by suyashg on 09/05/18.
 */

public class NewsDetailsPresenterImpl implements NewsDetailsPresenter {

    private static final String TAG = NewsDetailsPresenterImpl.class.getSimpleName();
    private NewsDetailsInteractor interactor;
    private News news;
    private NewsDetailsView view;
    private Disposable commentsSubscription;
    private List<NewsComment> loadedNewsCommentList = new ArrayList<>();

    public NewsDetailsPresenterImpl(NewsDetailsInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(NewsDetailsView view) {
        this.view = view;
        showLoading();
        fetchComments();
    }

    private void fetchComments() {
        if (news != null && !Utils.isNullOrEmpty(news.getKids())) {
            commentsSubscription = interactor.fetchNewsComment(news.getKids())
                    .subscribe(this::onNewsCommentListAddition, this::onNewsCommentFetchFailed);
        } else {
            onNewsCommentFetchFailed(new IllegalStateException(AppConstants.ERR_EMPTY_LIST));
        }
    }

    private void onNewsCommentListAddition(NewsComment newsComment) {
        Log.e(TAG, "onNewsListAddition newsId ="+ news.getId());
        loadedNewsCommentList.add(newsComment);
        if (isViewAttached()) {
            view.updateNewsCommentList(newsComment);
        }
    }

    private void onNewsCommentFetchFailed(Throwable e) {
        Log.e(TAG, "onNewsFetchItemFailed message ="+e.getMessage());
        if (isViewAttached()) {
            view.loadingFailed(e.getMessage());
        }
    }

    private void showLoading() {
        if (isViewAttached()) {
            view.loadingStarted();
        }
    }

    @Override
    public void destroy() {
        view = null;
        RxUtils.unsubscribe(commentsSubscription);
    }

    @Override
    public List<NewsComment> getNewsCommentsList() {
        return loadedNewsCommentList;
    }

    @Override
    public void setNewsCommentsList(List<NewsComment> newsCommentsList) {
        this.loadedNewsCommentList = newsCommentsList;
    }

    private boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public News getNews() {
        return news;
    }
}
