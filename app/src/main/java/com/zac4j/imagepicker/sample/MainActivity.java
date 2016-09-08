package com.zac4j.imagepicker.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.zac4j.imagepicker.ui.GalleryActivity;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

  public static final int REQUEST_IMAGES_CODE = 0x110;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void selectPhoto(View view) {
    startActivityForResult(
        new Intent(this, GalleryActivity.class).putExtra(GalleryActivity.EXTRA_SELECT_NUM, 3),
        REQUEST_IMAGES_CODE);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK || data == null) {
      return;
    }

    if (requestCode == REQUEST_IMAGES_CODE) {
      String[] imageContainer = data.getStringArrayExtra(GalleryActivity.EXTRA_IMAGE_CONTAINER);
      System.out.println("images: " + Arrays.toString(imageContainer));
    }
  }
}
