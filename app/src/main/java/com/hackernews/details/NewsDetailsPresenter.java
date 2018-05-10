package com.hackernews.details;

import com.hackernews.pojo.News;
import com.hackernews.pojo.NewsComment;

import java.util.List;

/**
 * Created by suyashg on 07/05/18.
 */

public interface NewsDetailsPresenter {

    void setView(NewsDetailsView view);

    void setNews(News news);

    News getNews();

    void destroy();

    List<NewsComment> getNewsCommentsList();

    void setNewsCommentsList(List<NewsComment> newsCommentsList);

}
