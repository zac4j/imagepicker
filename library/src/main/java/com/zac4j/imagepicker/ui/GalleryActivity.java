package com.zac4j.imagepicker.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.zac4j.imagepicker.R;
import com.zac4j.imagepicker.adapter.GalleryAdapter;
import com.zac4j.imagepicker.model.Photo;
import com.zac4j.imagepicker.task.PhotoTask;
import com.zac4j.imagepicker.ui.widget.PhotoItemDecoration;
import com.zac4j.imagepicker.util.RxUtils;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

/**
 * Gallery
 * Created by zac on 16-6-18.
 */

public class GalleryActivity extends AppCompatActivity {

  public static final String EXTRA_SELECT_NUM = "extra_select_num";

  private GalleryAdapter mAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gallery);

    final int selectNum = getIntent().getIntExtra(EXTRA_SELECT_NUM, 0);
    if (selectNum == 0) {
      throw new IllegalArgumentException(getString(R.string.illegal_select_item));
    }

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    RecyclerView photosView = (RecyclerView) findViewById(R.id.gallery_rv_photos);
    Button button = (Button) findViewById(R.id.gallery_item_btn_complete);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(GalleryActivity.this, 3);
    photosView.setLayoutManager(layoutManager);
    int space = getResources().getDimensionPixelSize(R.dimen.space_gallery);
    photosView.addItemDecoration(new PhotoItemDecoration(space));

    mAdapter = new GalleryAdapter(GalleryActivity.this, new ArrayList<Photo>());
    mAdapter.setSelectItemLimit(selectNum);
    photosView.setAdapter(mAdapter);

    PhotoTask.getPhotoList(getContentResolver())
        .compose(RxUtils.<List<Photo>>applyScheduler())
        .subscribe(new Action1<List<Photo>>() {
          @Override public void call(List<Photo> photos) {
            mAdapter.addAll(photos);
          }
        });

    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        ArrayList<String> photos = mAdapter.getSelectItemSet();
        if (photos == null || photos.isEmpty()) {
          Toast.makeText(GalleryActivity.this, "Haven't select image yet!", Toast.LENGTH_SHORT)
              .show();
        } else {
          // TODO select photo accomplish
        }
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
