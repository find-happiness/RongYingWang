package com.rongyingwang.app.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by song on 2016/3/2.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private int space;
  private boolean firstSpace = false;

  public SpacesItemDecoration(int space,boolean firstSpace) {
    this.space = space;
    this.firstSpace = firstSpace;

  }

  public SpacesItemDecoration(int space) {
    this.space = space;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view,
      RecyclerView parent, RecyclerView.State state) {
    //outRect.left = space;
    //outRect.right = space;
    outRect.bottom = space;

    // Add top margin only for the first item to avoid double space between items
    if(parent.getChildPosition(view) == 0 && firstSpace)
      outRect.top = space;
  }
}
