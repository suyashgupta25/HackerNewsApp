package com.hackernews.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyashg on 06/05/18.
 */

public class News implements Parcelable {

    private long id;
    private String title;
    private String type;
    private String url;
    private long score;
    private String by;
    private long time;
    private long descendants;
    private List<Long> kids = new ArrayList<>();;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getScore() {
        return score;
    }

    public String getBy() {
        return by;
    }

    public long getTime() {
        return time;
    }

    public List<Long> getKids() {
        return kids;
    }

    public void setKids(List<Long> kids) {
        this.kids = kids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public News(long id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeLong(this.score);
        dest.writeString(this.by);
        dest.writeLong(this.time);
        dest.writeLong(this.descendants);
        dest.writeList(this.kids);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.score = in.readLong();
        this.by = in.readString();
        this.time = in.readLong();
        this.descendants = in.readLong();
        this.kids = new ArrayList<Long>();
        in.readList(this.kids, Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
