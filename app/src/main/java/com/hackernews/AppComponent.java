package com.hackernews;

import com.hackernews.details.DetailsComponent;
import com.hackernews.details.DetailsModule;
import com.hackernews.listing.ListingComponent;
import com.hackernews.listing.ListingModule;
import com.hackernews.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by suyashg on 06/05/18.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class})
public interface AppComponent {

    DetailsComponent plus(DetailsModule detailsModule);

    ListingComponent plus(ListingModule listingModule);

}
