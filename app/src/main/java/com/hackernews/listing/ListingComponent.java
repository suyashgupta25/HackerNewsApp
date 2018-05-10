package com.hackernews.listing;

import dagger.Subcomponent;

/**
 * Created by suyashg on 06/05/18.
 */
@ListingScope
@Subcomponent(modules = {ListingModule.class})
public interface ListingComponent {

    NewsListFragment inject(NewsListFragment fragment);

}
