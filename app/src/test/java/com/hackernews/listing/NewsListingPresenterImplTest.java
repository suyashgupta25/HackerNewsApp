package com.hackernews.listing;


import com.hackernews.pojo.News;
import com.hackernews.utils.AppConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author suyashg
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsListingPresenterImplTest {
    @Mock
    private NewsListingInteractor interactor;
    @Mock
    private NewsListingView view;

    private List<Long> ids = new ArrayList<>(1);
    private News news = new News();
    private NewsListingPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new NewsListingPresenterImpl(interactor);
    }

    @After
    public void teardown() {
        presenter.destroy();
    }

    @Test
    public void shouldBeAbleToDisplayNews() {
        // given:
        Observable<News> responseObservable = Observable.just(news);
        when(interactor.fetchNewsSequentially(anyList())).thenReturn(responseObservable);

        ids.add(349875785L);
        Observable<List<Long>> responseIdsObservable = Observable.just(ids);
        when(interactor.fetchTopNews(AppConstants.NO_PAGES)).thenReturn(responseIdsObservable);
        // when:
        presenter.setView(view);

        // then:
        verify(view,timeout(5000).times(1)).updateNewsList(news);
    }
}