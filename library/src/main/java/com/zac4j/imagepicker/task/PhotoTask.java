package com.zac4j.imagepicker.task;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.zac4j.imagepicker.model.Photo;
import com.zac4j.imagepicker.util.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import rx.Single;

/**
 * Show gallery photo list task
 * Created by zac on 16-6-18.
 */

public class PhotoTask {

  public static Single<List<Photo>> getPhotoList(final ContentResolver resolver) {
    return Single.defer(new Callable<Single<List<Photo>>>() {
      @Override public Single<List<Photo>> call() throws Exception {
        List<Photo> photoList = getPhotoPathList(resolver);
        return Single.just(photoList);
      }
    });
  }

  /**
   * Get photo path list
   *
   * @param resolver ContentResolver
   * @return the photo path list
   */
  private static List<Photo> getPhotoPathList(ContentResolver resolver) {
    Uri resources = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String mimeType = MediaStore.Images.Media.MIME_TYPE;
    String[] projection = new String[] { MediaStore.Images.Media.DATA };
    String selection = mimeType + "=? or " + mimeType + "=? or " + mimeType + "=?";
    String[] selectionArgs = new String[] { "image/jpg", "image/jpeg", "image/png" };
    String sortOrder = MediaStore.Images.Media.DATE_MODIFIED;

    Cursor cursor = null;
    try {
      cursor = resolver.query(resources, projection, selection, selectionArgs, sortOrder);
      if (cursor == null) {
        return null;
      }
      List<Photo> photoPathList = new ArrayList<>();
      while (cursor.moveToNext()) {
        String photoPath = cursor.getString(0);
        Photo photo = new Photo();
        if (!TextUtils.isEmpty(photoPath) && Utils.isPhoto(photoPath)) {
          photo.setPath(photoPath);
          photo.setChecked(false);
          photoPathList.add(photo);
        }
      }
      Collections.reverse(photoPathList);
      return photoPathList;
    } finally {
      Utils.close(cursor);
    }
  }
}
