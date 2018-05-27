package com.hackernews.listing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackernews.R;
import com.hackernews.pojo.News;
import com.hackernews.utils.AppConstants;
import com.hackernews.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyashg on 07/05/18.
 */

public class NewsListingAdapter extends RecyclerView.Adapter<NewsListingAdapter.ViewHolder> {

    private static final String TAG = NewsListingAdapter.class.getSimpleName();
    private NewsListingView view;
    private List<News> newsList = new ArrayList<>();
    private Context context;

    public NewsListingAdapter(NewsListingView view) {
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_news_list, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(holder);
        holder.news = newsList.get(position);
        if (!TextUtils.isEmpty(holder.news.getTitle())) {
            holder.tvNewsListTitle.setText(holder.news.getTitle());
        } else {
            holder.tvNewsListTitle.setText(AppConstants.TEXT_EMPTY);
        }
        String description = holder.news.getScore() + AppConstants.TEXT_SPACE + context.getString(R.string.label_points);
        if (!TextUtils.isEmpty(holder.news.getBy())) {
            description = description + AppConstants.TEXT_SPACE + context.getString(R.string.label_by) + AppConstants.TEXT_SPACE + holder.news.getBy();
        }
        if (holder.news.getTime() != AppConstants.TIME_EMPTY) {
            description = description + AppConstants.TEXT_SPACE + TimeUtils.toDuration(holder.news.getTime());
        }

        if (holder.news.getKids()!= null && !holder.news.getKids().isEmpty()) {
            description = description + AppConstants.TEXT_SPACE + AppConstants.TEXT_BAR + AppConstants.TEXT_SPACE
                    + holder.news.getKids().size() + AppConstants.TEXT_SPACE + context.getString(R.string.label_comments);
        }
        holder.tvNewsDetails.setText(description);
    }

    @Override
    public long getItemId(int position) {
        return newsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void addItemAndRefresh(News news) {
        newsList.add(news);
        this.notifyItemInserted(newsList.indexOf(news));
    }

    public void clearNewsList() {
        newsList.clear();
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private News news;
        TextView tvNewsListTitle, tvNewsDetails;

        public ViewHolder(View root) {
            super(root);
            tvNewsListTitle = (TextView) root.findViewById(R.id.tv_news_list_title);
            tvNewsDetails = (TextView) root.findViewById(R.id.tv_news_details);
        }

        @Override
        public void onClick(View view) {
            NewsListingAdapter.this.view.onNewsClicked(news);
        }
    }

}
