package com.hackernews.details;

import com.hackernews.network.AppWebServices;

import dagger.Module;
import dagger.Provides;

/**
 * Created by suyashg on 06/05/18.
 */
@Module
public class DetailsModule {

    @Provides
    NewsDetailsInteractor provideMovieDetailsInteractor(AppWebServices webServices) {
        return new NewsDetailsInteractorImpl(webServices);
    }

    @Provides
    NewsDetailsPresenter provideNewsDetailsPresenter(NewsDetailsInteractor interactor) {
        return new NewsDetailsPresenterImpl(interactor);
    }
}
