package com.hackernews.details;

import com.hackernews.network.AppWebServices;
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
import io.reactivex.functions.Consumer;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by suyashg on 10/05/18.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class NewsDetailsInteractorImplTest {

    @Mock
    private AppWebServices webServices;
    @Mock
    NewsComment newsComment;

    private NewsDetailsInteractorImpl newsDetailsInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        newsDetailsInteractor = new NewsDetailsInteractorImpl(webServices);
    }

    @After
    public void teardown() {
    }

    @Test
    public void shouldBeAbleToDisplayNewsDetails() {
        // given:
        Observable<NewsComment> responseObservable = Observable.just(newsComment);
        when(webServices.newsComment(anyLong())).thenReturn(responseObservable);

        // when:
        Observable<NewsComment> newsCommentObservable = newsDetailsInteractor.fetchNewsComment(Arrays.asList(9287489342L));
        newsCommentObservable.subscribe(new Consumer<NewsComment>() {
            @Override
            public void accept(NewsComment newsComment) throws Exception {
                verify(newsComment);
            }
        });
    }
}
