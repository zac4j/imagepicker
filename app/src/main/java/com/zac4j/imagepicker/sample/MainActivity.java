package com.zac4j.imagepicker.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zac4j.imagepicker.ImageLoader;
import com.zac4j.imagepicker.ImagePicker;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  // Request code for start image picker activity.
  private static final int REQUEST_PICK_PHOTO_CODE = 0xaa;
  private static final int REQUEST_CAPTURE_PHOTO_CODE = 0xbb;

  // Request code for request storage permission.
  private static final int REQUEST_STORAGE_PERMISSION_CODE = 0xcc;

  // Set default number of photo to select.
  private static final int SELECT_PHOTO_NUM = 3;

  private View mRootLayout;
  private TextView mTextView;
  private ImageView mImageView;

  private boolean mIsPickPhoto;
  private Uri mImageFileUri;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mRootLayout = findViewById(R.id.main_root_layout);
    mTextView = (TextView) findViewById(R.id.main_image_list);
    mImageView = (ImageView) findViewById(R.id.main_photo_container);
    findViewById(R.id.main_pick_photo).setOnClickListener(this);
    findViewById(R.id.main_capture_photo).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    mIsPickPhoto = v.getId() == R.id.main_pick_photo;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkStoragePermission(MainActivity.this);
    } else {
      getPhoto(mIsPickPhoto);
    }
  }

  public void getPhoto(boolean isPickPhoto) {
    if (isPickPhoto) {
      pickPhoto();
    } else {
      capturePhoto(MainActivity.this);
    }
  }

  private void pickPhoto() {
    ImagePicker.with(this).engine(ImageLoader.GLIDE) // Set load image engine as Glide, or Picasso
        .number(SELECT_PHOTO_NUM) // Set select number for picking photo
        .code(REQUEST_PICK_PHOTO_CODE) // Set request code for retrieving data
        .build();
  }

  public void capturePhoto(Context context) {
    File imageFile = null;
    try {
      imageFile = Utils.createImageFile();
    } catch (IOException e) {
      Snackbar.make(mRootLayout, "Sorry, your device does not support photo capture",
          Snackbar.LENGTH_LONG);
      // e.printStackTrace();
    }
    if (Utils.isCanCapture(context, imageFile)) {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

      mImageFileUri =
          FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider",
              imageFile);

      intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageFileUri);
      startActivityForResult(intent, REQUEST_CAPTURE_PHOTO_CODE);
    }
  }

  private void checkStoragePermission(Activity activity) {
    if (hasStoragePermission(activity)) {
      getPhoto(mIsPickPhoto);
    } else {
      // By asking for 'WRITE' permission, we will automatically be getting 'READ' permission.
      ActivityCompat.requestPermissions(activity,
          new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
          REQUEST_STORAGE_PERMISSION_CODE);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      getPhoto(mIsPickPhoto);
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
    return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK) {
      return;
    }
    switch (requestCode) {
      case REQUEST_PICK_PHOTO_CODE:
        onPickSuccess(data);
        break;
      case REQUEST_CAPTURE_PHOTO_CODE:
        onCaptureSuccess();
        break;
    }
  }

  private void onPickSuccess(Intent data) {
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

  private void onCaptureSuccess() {
    if (mImageFileUri != null) {
      mImageView.setImageURI(mImageFileUri);
    }
  }
}
