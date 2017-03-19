package com.zac4j.imagepicker.util;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava Helper Utilities
 * Created by zac on 16-7-13.
 */

public class RxUtils {

  /**
   * Apply Maybe Source schedulers
   *
   * @param <T> Single type
   * @return transformed Single object
   */
  public static <T> MaybeTransformer<T, T> applyMaybeScheduler() {
    return new MaybeTransformer<T, T>() {
      @Override public MaybeSource<T> apply(Maybe<T> upstream) {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public static <T> ObservableTransformer<T, T> applyObservableScheduler() {
    return new ObservableTransformer<T, T>() {
      @Override public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }
}
