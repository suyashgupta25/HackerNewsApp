package com.hackernews.listing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by suyashg on 06/05/18.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ListingScope {
}
