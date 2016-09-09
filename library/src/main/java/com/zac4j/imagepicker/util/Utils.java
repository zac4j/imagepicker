package com.zac4j.imagepicker.util;

import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Utilities
 * Created by zac on 16-6-18.
 */

public class Utils {

  public static void close(Closeable closeable) {
    if (closeable == null) {
      return;
    }
    try {
      closeable.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isPhoto(String filename) {
    return filename.endsWith(".jpg")
        || filename.endsWith(".jpeg")
        || filename.endsWith(".bmp")
        || filename.endsWith(".png");
  }

  public static String getFilename(String filepath) {
    if (TextUtils.isEmpty(filepath)) {
      return null;
    }
    String fullName = new File(filepath).getName();
    int suffixIndex = fullName.lastIndexOf(".");
    return fullName.substring(0, suffixIndex);
  }

  public static String urlFromFile(String filePath) {
    return String.format("%s%s", "file://", filePath);
  }
}
