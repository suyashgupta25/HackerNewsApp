package com.hackernews.listing;

import com.hackernews.pojo.News;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by suyashg on 07/05/18.
 */

public interface NewsListingInteractor {

    boolean isPaginationSupported();

    Observable<List<Long>> fetchTopNews(int page);

    Observable<News> fetchNewsSequentially(List<Long> newsIds);

    void setAllNewsList(List<Long> allNewsList);

    List<Long> getAllNewsList();

}
