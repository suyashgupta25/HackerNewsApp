package com.hackernews.listing;

import com.hackernews.network.AppWebServices;

import dagger.Module;
import dagger.Provides;

/**
 * Created by suyashg on 06/05/18.
 */

@Module
public class ListingModule {

    @Provides
    NewsListingInteractor provideMovieListingInteractor(AppWebServices webServices) {
        return new NewsListingInteractorImpl(webServices);
    }

    @Provides
    NewsListingPresenter provideNewsListingPresenter(NewsListingInteractor interactor) {
        return new NewsListingPresenterImpl(interactor);
    }
}
