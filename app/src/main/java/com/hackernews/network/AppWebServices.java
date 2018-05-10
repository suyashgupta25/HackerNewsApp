package com.hackernews.network;

import com.hackernews.BuildConfig;
import com.hackernews.pojo.News;
import com.hackernews.pojo.NewsComment;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by suyashg on 06/05/18.
 */

public interface AppWebServices {

    @GET(BuildConfig.DOMAIN_TOP_NEWS)
    Observable<List<Long>> topNewsList();

    @GET(BuildConfig.DOMAIN_NEWS_DETAIL)
    Observable<News> newsDetail(@Path(BuildConfig.PARAM_NEWS_ID) long newsId);

    @GET(BuildConfig.DOMAIN_NEWS_DETAIL)
    Observable<NewsComment> newsComment(@Path(BuildConfig.PARAM_NEWS_ID) long newsId);

}
