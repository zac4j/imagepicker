package com.zac4j.imagepicker.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.zac4j.imagepicker.ui.GalleryActivity;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void selectPhoto(View view) {
    startActivity(
        new Intent(this, GalleryActivity.class).putExtra(GalleryActivity.EXTRA_SELECT_NUM, 3));
  }
}
