package com.hackernews.details;

import dagger.Subcomponent;

/**
 * Created by suyashg on 06/05/18.
 */
@DetailsScope
@Subcomponent(modules = {DetailsModule.class})
public interface DetailsComponent {

    void inject(NewsDetailsFragment target);

}
