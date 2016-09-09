package com.zac4j.imagepicker.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.zac4j.imagepicker.ui.GalleryActivity;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_IMAGES_CODE = 0x110;

  // Set select 3 photos from gallery
  private static final int SELECT_PHOTO_NUM = 3;

  private TextView mTextView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mTextView = (TextView) findViewById(R.id.images);
  }

  public void selectPhoto(View view) {
    Intent intent = new Intent(this, GalleryActivity.class);
    intent.putExtra(GalleryActivity.EXTRA_SELECT_NUM, SELECT_PHOTO_NUM);
    startActivityForResult(intent, REQUEST_IMAGES_CODE);
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
