package com.hackernews.listing;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackernews.BaseApplication;
import com.hackernews.R;
import com.hackernews.pojo.News;
import com.hackernews.utils.AppConstants;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by suyashg on 06/05/18.
 */

public class NewsListFragment extends Fragment implements NewsListingView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NewsListFragment.class.getSimpleName();

    @Inject
    NewsListingPresenter newsListingPresenter;

    private NewsListingAdapter adapter;
    private RecyclerView newsListing;
    private Callback callback;
    private SwipeRefreshLayout srlNewsListing;

    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseApplication) getActivity().getApplication()).createListingComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        srlNewsListing.setOnRefreshListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        srlNewsListing = (SwipeRefreshLayout) view.findViewById(R.id.srl_news_listing);
        newsListing = (RecyclerView) view.findViewById(R.id.news_listing);
        initLayoutReferences();
        if (savedInstanceState != null) {
            ArrayList<News> newsList = savedInstanceState.getParcelableArrayList(AppConstants.PARAM_NEWS_ARRAYLIST);
            newsListingPresenter.setNewsList(newsList);
            adapter.notifyDataSetChanged();
            newsListing.setVisibility(View.VISIBLE);
        } else {
            newsListingPresenter.setView(this);
        }
    }

    private void initLayoutReferences() {
        newsListing.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        newsListing.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsListing.getContext(),
                mLayoutManager.getOrientation());
        newsListing.addItemDecoration(dividerItemDecoration);
        adapter = new NewsListingAdapter(this, newsListingPresenter.getNewsList());
        newsListing.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(AppConstants.PARAM_NEWS_ARRAYLIST, (ArrayList<? extends Parcelable>) newsListingPresenter.getNewsList());
    }

    @Override
    public void loadingStarted() {
        Log.d(TAG, "loadingStarted");
        if (newsListingPresenter.getNewsList().isEmpty())
            Snackbar.make(newsListing, R.string.loading_news, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loadingFailed(String errorMessage) {
        Log.d(TAG, "loadingFailed errorMessage="+errorMessage);
        Snackbar.make(newsListing, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updateNewsList(News news) {
        Log.d(TAG, "showNews");
        newsListing.setVisibility(View.VISIBLE);
        adapter.addItemAndRefresh(news);
        callback.onNewsLoaded(news);
    }

    @Override
    public void onNewsClicked(News news) {
        Log.d(TAG, "onNewsClicked");
        callback.onNewsClicked(news);
    }

    @Override
    public void clearNewsList() {
        adapter.clearNewsList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsListingPresenter.destroy();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((BaseApplication) getActivity().getApplication()).releaseListingComponent();
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onPulledToRefresh");
        newsListingPresenter.onPulledToRefresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                srlNewsListing.setRefreshing(false);
            }
        },2000);
    }


    public interface Callback {
        void onNewsLoaded(News news);

        void onNewsClicked(News news);
    }
}
