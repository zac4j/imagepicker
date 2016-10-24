package com.zac4j.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.zac4j.imagepicker.ui.GalleryActivity;

/**
 * Image Picker
 * Created by zac on 16-10-20.
 */

public class ImagePicker {

  public static final String DATA = "extra_data";

  public static Builder with(@NonNull Activity activity) {
    if (activity == null) { // 为了防止瞎子
      throw new IllegalArgumentException("context == null");
    }
    return new Builder(activity);
  }

  public ImagePicker(Activity activity, ImageLoader imageLoader, int requestCode,
      int selectNumber) {
    startGalleryActivity(activity, imageLoader, requestCode, selectNumber);
  }

  private void startGalleryActivity(Activity activity, ImageLoader imageLoader, int requestCode,
      int selectNumber) {
    Intent intent = new Intent(activity, GalleryActivity.class);
    intent.putExtra(GalleryActivity.EXTRA_IMAGE_LOADER, imageLoader);
    intent.putExtra(GalleryActivity.EXTRA_SELECT_NUM, selectNumber);

    activity.startActivityForResult(intent, requestCode);
  }

  public static class Builder {
    private final Activity activity;
    private ImageLoader imageLoader;
    private int requestCode;
    private int selectNumber;

    public Builder(@NonNull Activity activity) {
      if (activity == null) { // 为了防止瞎子
        throw new IllegalArgumentException("context == null");
      }
      this.activity = activity;
    }

    public Builder engine(ImageLoader imageLoader) {
      if (imageLoader == null) {
        throw new IllegalArgumentException("ImageLoader must not be null.");
      }
      this.imageLoader = imageLoader;
      return this;
    }

    public Builder code(int requestCode) {
      if (requestCode <= 0) {
        throw new IllegalArgumentException("Picker Listener must not be null.");
      }
      this.requestCode = requestCode;
      return this;
    }

    public Builder number(int selectNumber) {
      if (selectNumber <= 0) {
        throw new IllegalArgumentException("select number must has a validate value.");
      }
      this.selectNumber = selectNumber;
      return this;
    }

    public ImagePicker build() {

      if (imageLoader == null) {
        throw new IllegalStateException("ImageLoader must not be null.");
      }

      if (requestCode <= 0) {
        throw new IllegalStateException("Request code must has a validate value.");
      }

      if (selectNumber <= 0) {
        throw new IllegalStateException("Select number must has a validate value.");
      }

      return new ImagePicker(activity, imageLoader, requestCode, selectNumber);
    }
  }
}
