package com.zac4j.imagepicker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.zac4j.imagepicker.ImageLoader;
import com.zac4j.imagepicker.ImagePicker;
import com.zac4j.imagepicker.R;
import com.zac4j.imagepicker.adapter.GalleryAdapter;
import com.zac4j.imagepicker.model.Photo;
import com.zac4j.imagepicker.task.PhotoTask;
import com.zac4j.imagepicker.ui.widget.PhotoItemDecoration;
import com.zac4j.imagepicker.util.RxUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/**
 * Gallery
 * Created by zac on 16-6-18.
 */

public class GalleryActivity extends AppCompatActivity {

  public static final String EXTRA_SELECT_NUM = "extra_select_num";
  public static final String EXTRA_IMAGE_LOADER = "extra_image_loader";

  private CompositeDisposable mDisposable;
  private GalleryAdapter mAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gallery);

    final int selectNum = getIntent().getIntExtra(EXTRA_SELECT_NUM, 0);
    final ImageLoader imageLoader =
        (ImageLoader) getIntent().getSerializableExtra(EXTRA_IMAGE_LOADER);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    RecyclerView photosView = (RecyclerView) findViewById(R.id.gallery_rv_photos);
    final TextView button = (TextView) toolbar.findViewById(R.id.gallery_tv_select_complete);
    final TextView noPhotoView = (TextView) findViewById(R.id.gallery_no_photo);

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
    mAdapter.setImageLoader(imageLoader);
    photosView.setAdapter(mAdapter);

    mDisposable = new CompositeDisposable();
    mDisposable.add(PhotoTask.getPhotoList(getContentResolver())
        .compose(RxUtils.<List<Photo>>applyObservableScheduler())
        .subscribe(new Consumer<List<Photo>>() {
          @Override public void accept(List<Photo> photos) throws Exception {
            if (photos == null || photos.isEmpty()) {
              noPhotoView.setVisibility(View.VISIBLE);
              button.setVisibility(View.GONE);
            } else {
              mAdapter.addAll(photos);
            }
          }
        }));

    //    new Action1<List<Photo>>() {
    //  @Override public void call(List<Photo> photos) {
    //    if (photos == null || photos.isEmpty()) {
    //      noPhotoView.setVisibility(View.VISIBLE);
    //      button.setVisibility(View.GONE);
    //    } else {
    //      mAdapter.addAll(photos);
    //    }
    //  }
    //}
    //);

    button.setOnClickListener(new View.OnClickListener()

    {
      @Override public void onClick(View view) {
        ArrayList<String> photos = mAdapter.getSelectItemSet();
        if (photos == null || photos.isEmpty()) {
          Toast.makeText(GalleryActivity.this, "Haven't select image yet!", Toast.LENGTH_SHORT)
              .show();
        } else {
          Intent intent = new Intent();
          intent.putStringArrayListExtra(ImagePicker.DATA, photos);
          setResult(RESULT_OK, intent);
          GalleryActivity.this.finish();
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

  @Override protected void onDestroy() {
    if (mDisposable != null) {
      mDisposable.clear();
    }
    super.onDestroy();
  }
}
