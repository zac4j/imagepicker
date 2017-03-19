package com.zac4j.imagepicker.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utilities
 * copy from: https://coding.net/u/zac4j/p/Camera/git/blob/master/app/src/main/java/com/zac4j/camera/util/PictureUtils.java
 * Created by zac on 3/19/2017.
 */

public class Utils {

  /**
   * 检验是否可以调用相机
   *
   * @param context 下上文
   * @param photoFile 图片文件
   * @return true 假如可以调用相机，否则返回 false
   */
  public static boolean isCanCapture(Context context, File photoFile) {
    return photoFile != null
        && new Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(context.getPackageManager())
        != null;
  }
  /**
   * 获取图片文件名
   *
   * @return 图片文件名
   */
  private static String getImageName() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    String timestamp = sdf.format(new Date());
    return "IMG_" + timestamp;
  }
  /**
   * 创建临时文件
   *
   * @return 临时文件
   */
  public static File createImageFile() throws IOException {
    File storageDir = Environment.getExternalStorageDirectory();
    return File.createTempFile(getImageName(), /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */);
  }
  /**
   * 获取图片文件路径
   *
   * @param file 图皮文件
   * @return 图片文件路径
   */
  public static String getImageFilePath(File file) {
    return "file:" + file.getAbsolutePath();
  }

}
