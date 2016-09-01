package com.zac4j.imagepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.zac4j.imagepicker.R;
import com.zac4j.imagepicker.model.Photo;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Gallery adapter for photo select grid view
 * Created by zac on 16-6-18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageHolder> {

  private Context mContext;
  private List<Photo> mPhotoList;
  private Set<String> mSelectItemSet;
  private int mSelectItemLimit;
  private int mCurrentSelectedNum;

  public GalleryAdapter(Context context, List<Photo> photoList) {
    mContext = context;
    mPhotoList = photoList;
    mSelectItemSet = new HashSet<>();
  }

  @Override public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(mContext).inflate(R.layout.activity_gallery_item_photo, parent, false);
    return new ImageHolder(view);
  }

  @Override public void onBindViewHolder(final ImageHolder holder, final int position) {
    final Photo photo = mPhotoList.get(position);
    photo.setId(position);
    holder.setItem(photo);

    File file = new File(photo.getPath());

    Glide.with(mContext)
        .load(file)
        .centerCrop()
        .placeholder(R.color.white)
        .crossFade()
        .into(holder.mImageView);

    holder.mPhotoContainer.setBackgroundResource(
        photo.isChecked() ? R.drawable.bg_photo_sel : R.drawable.bg_photo_nor);
  }

  @Override public int getItemCount() {
    return mPhotoList.size();
  }

  class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Photo mPhoto;

    private FrameLayout mPhotoContainer;
    private ImageView mImageView;

    ImageHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mImageView = (ImageView) itemView.findViewById(R.id.gallery_item_iv_photo);
      mPhotoContainer = (FrameLayout) itemView.findViewById(R.id.gallery_item_fr_container);
    }

    void setItem(Photo photo) {
      mPhoto = photo;
    }

    @Override public void onClick(View view) {
      if (mCurrentSelectedNum < mSelectItemLimit) {
        mPhoto.setChecked(!mPhoto.isChecked());
        mPhotoContainer.setBackgroundResource(
            mPhoto.isChecked() ? R.drawable.bg_photo_sel : R.drawable.bg_photo_nor);
        String path = mPhotoList.get(mPhoto.getId()).getPath();
        if (mPhoto.isChecked()) {
          mCurrentSelectedNum++;
          mSelectItemSet.add(path);
        } else {
          if (mSelectItemSet.contains(path)) {
            mSelectItemSet.remove(path);
          }
          mCurrentSelectedNum--;
        }
      } else {
        if (mPhoto.isChecked()) {
          mPhoto.setChecked(false);
          mPhotoContainer.setBackgroundResource(R.drawable.bg_photo_nor);
          String path = mPhotoList.get(mPhoto.getId()).getPath();
          if (mSelectItemSet.contains(path)) {
            mSelectItemSet.remove(path);
          }
          mCurrentSelectedNum--;
        } else {
          Toast.makeText(mContext, "Select item limit!", Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  public void addAll(List<Photo> photoList) {
    mPhotoList = photoList;
    notifyDataSetChanged();
  }

  public void setSelectItemLimit(int limit) {
    mSelectItemLimit = limit;
  }

  public ArrayList<String> getSelectItemSet() {
    return new ArrayList<>(mSelectItemSet);
  }
}
