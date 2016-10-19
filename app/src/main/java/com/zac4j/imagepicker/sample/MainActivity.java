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
import com.zac4j.imagepicker.ui.GalleryActivity;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_IMAGES_CODE = 0xaa;
  private static final int REQUEST_STORAGE_PERMISSION_CODE = 0xbb;

  // Set select 3 photos from gallery
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

  private void startGalleryActivity() {
    Intent intent = new Intent(this, GalleryActivity.class);
    intent.putExtra(GalleryActivity.EXTRA_SELECT_NUM, SELECT_PHOTO_NUM);
    startActivityForResult(intent, REQUEST_IMAGES_CODE);
  }

  private void checkStoragePermission(Activity activity) {
    if (hasStoragePermission(activity)) {
      startGalleryActivity();
    } else {
      ActivityCompat.requestPermissions(activity,
          new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
          REQUEST_STORAGE_PERMISSION_CODE);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      startGalleryActivity();
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

    String[] images = data.getStringArrayExtra(GalleryActivity.EXTRA_IMAGE_CONTAINER);

    if (images == null || images.length == 0) {
      return;
    }

    String showText = "";
    for (String image : images) {
      showText += image + "\n";
    }

    mTextView.setText(showText);
  }
}
