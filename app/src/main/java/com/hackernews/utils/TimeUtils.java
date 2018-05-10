package com.hackernews.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by suyashg on 10/05/18.
 */

public class TimeUtils {

    public static final List<String> timesString = Arrays.asList("year","month","day","hour","minute","second");
    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final String TIME_DEFAULT = "0 seconds ago";

    public static String toDuration(long duration) {

        StringBuffer res = new StringBuffer();
        for(int i=0;i< TimeUtils.times.size(); i++) {
            Long current = TimeUtils.times.get(i);
            long temp = duration/current;
            if(temp>0) {
                res.append(temp).append(" ").append( TimeUtils.timesString.get(i) ).append(temp != 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if(AppConstants.TEXT_EMPTY.equals(res.toString()))
            return TIME_DEFAULT;
        else
            return res.toString();
    }
}
