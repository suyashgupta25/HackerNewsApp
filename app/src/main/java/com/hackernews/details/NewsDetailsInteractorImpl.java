package com.hackernews.details;

import com.hackernews.network.AppWebServices;
import com.hackernews.pojo.NewsComment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by suyashg on 09/05/18.
 */

public class NewsDetailsInteractorImpl implements NewsDetailsInteractor {

    private AppWebServices webServices;

    public NewsDetailsInteractorImpl(AppWebServices webServices) {
        this.webServices = webServices;
    }

    @Override
    public Observable<NewsComment> fetchNewsComment(List<Long> commentIds) {
        return Observable.fromIterable(commentIds).flatMap(new Function<Long, ObservableSource<NewsComment>>() {
            @Override
            public ObservableSource<NewsComment> apply(Long commentId) throws Exception {
                return webServices.newsComment(commentId);
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
