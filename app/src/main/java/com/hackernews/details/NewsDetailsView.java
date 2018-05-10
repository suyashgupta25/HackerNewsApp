package com.hackernews.details;

import com.hackernews.pojo.NewsComment;

/**
 * Created by suyashg on 07/05/18.
 */

public interface NewsDetailsView {

    void loadingStarted();

    void loadingFailed(String errorMessage);

    void updateNewsCommentList(NewsComment newsComment);

}
