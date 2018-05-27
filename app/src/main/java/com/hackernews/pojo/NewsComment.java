package com.hackernews.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suyashg on 09/05/18.
 */

public class NewsComment {
    private Long id;
    private String by;
    private long[] kids;
    private Long parent;
    private String text;
    private Long time;
    private String type;

    public NewsComment() {
    }

    public String getBy() {
        return by;
    }

    public String getText() {
        return text;
    }
}
