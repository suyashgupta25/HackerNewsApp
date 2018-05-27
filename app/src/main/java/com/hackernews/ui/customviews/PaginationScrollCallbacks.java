package com.hackernews.ui.customviews;

/**
 * Created by suyashg on 27/05/18.
 */

public interface PaginationScrollCallbacks {
    void loadMoreItemsInList();

    boolean isLastPage();

    boolean isLoading();
}
