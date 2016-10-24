package com.zac4j.imagepicker.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.zac4j.imagepicker.ImageLoader;
import com.zac4j.imagepicker.ImagePicker;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  // Request code for start image picker activity.
  private static final int REQUEST_IMAGES_CODE = 0xaa;

  // Request code for request storage permission.
  private static final int REQUEST_STORAGE_PERMISSION = 0xbb;

  // Set default number of photo to select.
  private static final int SELECT_PHOTO_NUM = 3;

  private TextView mTextView;
  private View mRootLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mRootLayout = findViewById(R.id.root_layout);
    mTextView = (TextView) findViewById(R.id.image_list);
  }

  public void selectPhoto(View view) {
    checkStoragePermission(MainActivity.this);
  }

  private void startPicker() {
    ImagePicker.with(this).engine(ImageLoader.GLIDE) // Set load image engine as Glide, or Picasso
        .number(SELECT_PHOTO_NUM) // Set select number for picking photo
        .code(REQUEST_IMAGES_CODE) // Set request code for retrieving data
        .build();
  }

  private void checkStoragePermission(Activity activity) {
    if (hasStoragePermission(activity)) {
      startPicker();
    } else {
      ActivityCompat.requestPermissions(activity,
          new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, REQUEST_STORAGE_PERMISSION);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      startPicker();
    } else {
      Snackbar.make(mRootLayout, "Bad permission request, you could not select multiple photo now.",
          Snackbar.LENGTH_LONG);
    }
  }

  /**
   * Check if app has read storage permission
   *
   * @param context Context to support check permission
   * @return {@code true} if has read storage permission
   */
  private boolean hasStoragePermission(Context context) {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode != REQUEST_IMAGES_CODE || resultCode != RESULT_OK || data == null) {
      return;
    }

    List<String> images = data.getStringArrayListExtra(ImagePicker.DATA);

    if (images == null || images.isEmpty()) {
      return;
    }

    String showText = "";
    for (String image : images) {
      showText += image + "\n";
    }

    mTextView.setText(showText);
  }
}
