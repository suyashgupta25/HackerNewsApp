package com.hackernews.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackernews.BaseApplication;
import com.hackernews.R;
import com.hackernews.listing.NewsListFragment;
import com.hackernews.listing.NewsListingAdapter;
import com.hackernews.pojo.News;
import com.hackernews.pojo.NewsComment;
import com.hackernews.utils.AppConstants;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by suyashg on 09/05/18.
 */

public class NewsDetailsFragment extends Fragment implements NewsDetailsView {

    private static final String TAG = NewsDetailsFragment.class.getSimpleName();

    @Inject
    NewsDetailsPresenter newsDetailsPresenter;

    private NewsDetailsAdapter adapter;
    private RecyclerView newsCommentsListing;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseApplication) getActivity().getApplication()).createDetailsComponent().inject(this);
    }

    public NewsDetailsFragment()  {
        // Required empty public constructor
    }

    public static NewsDetailsFragment getInstance(@NonNull News news) {
        Bundle args = new Bundle();
        args.putParcelable(AppConstants.PARAM_NEWS, news);
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        newsDetailsFragment.setArguments(args);
        return newsDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    private void initLayoutReferences() {
        newsCommentsListing.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        newsCommentsListing.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newsCommentsListing.getContext(),
                mLayoutManager.getOrientation());
        newsCommentsListing.addItemDecoration(dividerItemDecoration);
        adapter = new NewsDetailsAdapter(this, newsDetailsPresenter.getNewsCommentsList());
        newsCommentsListing.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsCommentsListing = (RecyclerView) view.findViewById(R.id.news_details_listing);
        initLayoutReferences();
        if (getArguments() != null) {
            News news = (News) getArguments().get(AppConstants.PARAM_NEWS);
            if (news != null) {
                newsDetailsPresenter.setNews(news);
                newsDetailsPresenter.setView(this);
                ((TextView)getView().findViewById(R.id.tv_news_list_title)).setText(news.getTitle());
                ((TextView)getView().findViewById(R.id.tv_news_details)).setText(news.getBy());
            }
        }
    }

    @Override
    public void loadingStarted() {
        Log.d(TAG, "loadingStarted");
        Snackbar.make(newsCommentsListing, R.string.loading_comments, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loadingFailed(String errorMessage) {
        Log.d(TAG, "loadingFailed errorMessage="+errorMessage);
        Snackbar.make(newsCommentsListing, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updateNewsCommentList(NewsComment newsComment) {
        Log.d(TAG, "updateNewsCommentList");
        newsCommentsListing.setVisibility(View.VISIBLE);
        adapter.addItemAndRefresh(newsComment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsDetailsPresenter.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((BaseApplication) getActivity().getApplication()).releaseDetailsComponent();
    }
}
