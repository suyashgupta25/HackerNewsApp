package com.hackernews.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suyashg on 09/05/18.
 */

public class NewsComment implements Parcelable {
    private Long id;
    private String by;
    private long[] kids;
    private Long parent;
    private String text;
    private Long time;
    private String type;

    public NewsComment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public long[] getKids() {
        return kids;
    }

    public void setKids(long[] kids) {
        this.kids = kids;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.by);
        dest.writeLongArray(this.kids);
        dest.writeValue(this.parent);
        dest.writeString(this.text);
        dest.writeValue(this.time);
        dest.writeString(this.type);
    }

    protected NewsComment(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.by = in.readString();
        this.kids = in.createLongArray();
        this.parent = (Long) in.readValue(Long.class.getClassLoader());
        this.text = in.readString();
        this.time = (Long) in.readValue(Long.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Parcelable.Creator<NewsComment> CREATOR = new Parcelable.Creator<NewsComment>() {
        @Override
        public NewsComment createFromParcel(Parcel source) {
            return new NewsComment(source);
        }

        @Override
        public NewsComment[] newArray(int size) {
            return new NewsComment[size];
        }
    };
}
