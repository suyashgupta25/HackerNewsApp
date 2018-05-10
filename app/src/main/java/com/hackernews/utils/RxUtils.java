package com.hackernews.utils;

import io.reactivex.disposables.Disposable;

/**
 * Created by suyashg on 07/05/18.
 */

public class RxUtils {
    public static void unsubscribe(Disposable subscription)
    {
        if (subscription != null && !subscription.isDisposed())
        {
            subscription.dispose();
        } // else subscription doesn't exist or already unsubscribed
    }
}
