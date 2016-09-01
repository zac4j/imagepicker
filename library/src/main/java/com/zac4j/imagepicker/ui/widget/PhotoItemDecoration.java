package com.zac4j.imagepicker.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Photo item decoration in recycler view
 * Created by zac on 16-6-25.
 */

public class PhotoItemDecoration extends RecyclerView.ItemDecoration {
  private int mSpace;

  public PhotoItemDecoration(int space) {
    mSpace = space;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.left = mSpace;
    outRect.right = mSpace;
    outRect.bottom = mSpace;

    if (parent.getChildLayoutPosition(view) == 0) {
      outRect.top = mSpace;
    } else {
      outRect.top = 0;
    }
  }
}
