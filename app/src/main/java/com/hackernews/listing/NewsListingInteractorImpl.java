package com.hackernews.listing;

import android.util.Log;

import com.hackernews.network.AppWebServices;
import com.hackernews.pojo.News;
import com.hackernews.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by suyashg on 07/05/18.
 */

public class NewsListingInteractorImpl implements NewsListingInteractor {

    private static final String TAG = NewsListingInteractorImpl.class.getSimpleName();

    private AppWebServices appWebServices;

    public NewsListingInteractorImpl(AppWebServices appWebServices) {
        this.appWebServices = appWebServices;
    }

    @Override
    public boolean isPaginationSupported() {
        return false;
    }

    @Override
    public Observable<List<Long>> fetchTopNews(int page) {
        if (page == AppConstants.NO_PAGES) {
            return appWebServices.topNewsList().subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .flatMap(new Function<List<Long>, ObservableSource<List<Long>>>() {
                        @Override
                        public ObservableSource<List<Long>> apply(List<Long> ids) throws Exception {
                            Log.d(TAG, "ids fetched");
                            return Observable.just(ids);
                        }
                    });
        }
        return Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<News> fetchNewsSequentially(List<Long> newsIds) {
        Log.e(TAG, "fetchNewsSequentially newsIds ="+ newsIds.size());
        return Observable.fromIterable(newsIds).flatMap(new Function<Long, ObservableSource<News>>() {
            @Override
            public ObservableSource<News> apply(Long newsId) throws Exception {
                return appWebServices.newsDetail(newsId);
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
