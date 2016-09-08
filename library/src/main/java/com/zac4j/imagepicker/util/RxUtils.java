package com.zac4j.imagepicker.util;

import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava Helper Utilities
 * Created by zac on 16-7-13.
 */

public class RxUtils {

  /**
   * Apply Single object schedulers
   *
   * @param <T> Single type
   * @return transformed Single object
   */
  public static <T> Single.Transformer<T, T> applyScheduler() {
    return new Single.Transformer<T, T>() {
      @Override public Single<T> call(Single<T> tSingle) {
        return tSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  /**
   * Unsubscribe subscription
   * @param subscription subscription to unsubscribe
   */
  public static void unsubscribe(Subscription subscription) {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
