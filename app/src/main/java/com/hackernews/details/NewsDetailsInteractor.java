package com.hackernews.details;

import com.hackernews.pojo.NewsComment;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by suyashg on 07/05/18.
 */

public interface NewsDetailsInteractor {

    Observable<NewsComment> fetchNewsComment(List<Long> commentIds);

}
