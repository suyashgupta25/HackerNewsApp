package com.hackernews.details;

import com.hackernews.pojo.News;
import com.hackernews.pojo.NewsComment;
import com.hackernews.utils.AppConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by suyashg on 10/05/18.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class NewsDetailsPresenterImplTest {

    @Mock
    private NewsDetailsView view;
    @Mock
    private NewsDetailsInteractor newsDetailsInteractor;
    @Mock
    NewsComment newsComment;

    private NewsDetailsPresenterImpl newsDetailsPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        newsDetailsPresenter = new NewsDetailsPresenterImpl(newsDetailsInteractor);
    }

    @After
    public void teardown() {
        newsDetailsPresenter.destroy();
    }

    @Test
    public void shouldBeAbleToDisplayNewsDetails() {
        // given:
        Observable<NewsComment> responseObservable = Observable.just(newsComment);
        when(newsDetailsInteractor.fetchNewsComment(anyList())).thenReturn(responseObservable);

        News news = new News();
        news.setKids(new ArrayList<>(Arrays.asList(9287489342L)));
        newsDetailsPresenter.setNews(news);

        // when:
        newsDetailsPresenter.setView(view);

        // then:
        verify(view, timeout(5000).times(1)).updateNewsCommentList(newsComment);
    }

    @Test
    public void shouldNotBeAbleToDisplayNewsDetails() {
        // given:
        Observable<NewsComment> responseObservable = Observable.just(newsComment);
        when(newsDetailsInteractor.fetchNewsComment(anyList())).thenReturn(responseObservable);

        newsDetailsPresenter.setNews(new News());

        // when:
        newsDetailsPresenter.setView(view);

        // then:
        verify(view, timeout(5000).times(1)).loadingFailed(AppConstants.ERR_EMPTY_LIST);
    }



}
