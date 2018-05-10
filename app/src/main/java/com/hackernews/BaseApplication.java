package com.hackernews;

import android.app.Application;
import android.os.StrictMode;

import com.hackernews.details.DetailsComponent;
import com.hackernews.details.DetailsModule;
import com.hackernews.listing.ListingComponent;
import com.hackernews.listing.ListingModule;
import com.hackernews.network.NetworkModule;

/**
 * Created by suyashg on 06/05/18.
 */

public class BaseApplication extends Application {

    private AppComponent appComponent;
    private DetailsComponent detailsComponent;
    private ListingComponent listingComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.enableDefaults();
        appComponent = createAppComponent();
    }

    private AppComponent createAppComponent()
    {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public DetailsComponent createDetailsComponent() {
        detailsComponent = appComponent.plus(new DetailsModule());
        return detailsComponent;
    }

    public void releaseDetailsComponent() {
        detailsComponent = null;
    }

    public ListingComponent createListingComponent() {
        listingComponent = appComponent.plus(new ListingModule());
        return listingComponent;
    }

    public void releaseListingComponent()
    {
        listingComponent = null;
    }

    public ListingComponent getListingComponent()
    {
        return listingComponent;
    }
}
