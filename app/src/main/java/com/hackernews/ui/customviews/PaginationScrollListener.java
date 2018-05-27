package com.hackernews.ui.customviews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by suyashg on 27/05/18.
 */

public final class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;
    private PaginationScrollCallbacks callbacks;
    private static final int VALUE_ZERO = 0;

    public PaginationScrollListener(LinearLayoutManager layoutManager, PaginationScrollCallbacks callbacks) {
        this.layoutManager = layoutManager;
        this.callbacks = callbacks;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!callbacks.isLoading() && !callbacks.isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= VALUE_ZERO) {
                callbacks.loadMoreItemsInList();
            }
        }
    }
}
