package com.hackernews.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackernews.R;
import com.hackernews.pojo.NewsComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyashg on 07/05/18.
 */

public class NewsDetailsAdapter extends RecyclerView.Adapter<NewsDetailsAdapter.ViewHolder> {

    private NewsDetailsView view;
    private List<NewsComment> newsCommentList = new ArrayList<>();
    private Context context;

    public NewsDetailsAdapter(NewsDetailsView view) {
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_news_comment_list, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.newsComment = newsCommentList.get(position);
        holder.tvNewsCommentListTitle.setText(Html.fromHtml(holder.newsComment.getText()));
        holder.tvNewsCommentDetails.setText(holder.newsComment.getBy());
    }

    @Override
    public int getItemCount() {
        return newsCommentList.size();
    }

    public void addItemAndRefresh(NewsComment newsComment) {
        newsCommentList.add(newsComment);
        this.notifyItemInserted(newsCommentList.indexOf(newsComment));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private NewsComment newsComment;
        TextView tvNewsCommentListTitle, tvNewsCommentDetails;

        public ViewHolder(View root) {
            super(root);
            tvNewsCommentListTitle = (TextView) root.findViewById(R.id.tv_news_comment_list_title);
            tvNewsCommentDetails = (TextView) root.findViewById(R.id.tv_news_comment_details);
        }
    }

}
