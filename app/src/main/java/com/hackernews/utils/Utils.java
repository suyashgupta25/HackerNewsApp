package com.hackernews.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by suyashg on 09/05/18.
 */

public class Utils {

    public static boolean isNullOrEmpty( final Collection< ? > c ) {
        return c == null || c.isEmpty();
    }

    public static <T> List<T> getElementsFromList(List<T> list, int pageIndex, int pageSize) {
        List<T> newList = new ArrayList<>();
        int start = pageIndex * pageSize;//
        int end = start + pageSize;//
        if (list.size() < end) {
            end = list.size();
        }
        for(int i = start; i < end; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

}
