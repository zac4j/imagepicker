package com.zac4j.imagepicker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.zac4j.imagepicker.ui.GalleryActivity;

/**
 * Image Picker
 * Created by zac on 16-10-20.
 */

public class ImagePicker {

  public static ImagePicker with(@NonNull Context context) {
    if (context == null) { // 为了防止瞎子
      throw new IllegalArgumentException("context == null");
    }
    return new Builder(context).build();
  }

  public ImagePicker(Context context, PickerListener pickerListener, ImageLoader imageLoader,
      int selectNumber) {
    startGalleryActivity(context, pickerListener, imageLoader, selectNumber);
  }

  private void startGalleryActivity(Context context, PickerListener pickerListener,
      ImageLoader imageLoader, int selectNumber) {
    Intent intent = new Intent(context, GalleryActivity.class);
    // FIXME: 16-10-21
    intent.putExtra(GalleryActivity.EXTRA_PICKER_LISTENER, pickerListener);
    intent.putExtra(GalleryActivity.EXTRA_IMAGE_LOADER, imageLoader);
    intent.putExtra(GalleryActivity.EXTRA_SELECT_NUM, selectNumber);
    context.startActivity(intent);
  }

  public static class Builder {
    private final Context context;
    private PickerListener pickerListener;
    private ImageLoader imageLoader;
    private int selectNumber;

    public Builder(@NonNull Context context) {
      if (context == null) { // 为了防止瞎子
        throw new IllegalArgumentException("context == null");
      }
      this.context = context;
    }

    public Builder engine(ImageLoader imageLoader) {
      if (imageLoader == null) {
        throw new IllegalArgumentException("ImageLoader must not be null.");
      }
      this.imageLoader = imageLoader;
      return this;
    }

    public Builder listener(PickerListener pickerListener) {
      if (pickerListener == null) {
        throw new IllegalArgumentException("Picker Listener must not be null.");
      }
      this.pickerListener = pickerListener;
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
      Context context = this.context;

      if (imageLoader == null) {
        throw new IllegalStateException("ImageLoader must not be null.");
      }

      if (pickerListener == null) {
        throw new IllegalStateException("PickerListener must not be null.");
      }

      if (selectNumber <= 0) {
        throw new IllegalStateException("select number must has a validate value.");
      }

      return new ImagePicker(context, pickerListener, imageLoader, selectNumber);
    }
  }
}
