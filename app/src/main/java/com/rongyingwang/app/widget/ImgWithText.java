package com.rongyingwang.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rongyingwang.app.R;

public class ImgWithText extends LinearLayout {
  private ImageView img;
  private TextView tvContent;

  public ImgWithText(Context context) {
    this(context, null);
  }

  public ImgWithText(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(R.layout.img_with_text, null);

    img = (ImageView) view.findViewById(R.id.img);
    tvContent = (TextView) view.findViewById(R.id.text);

    if (attrs != null) {
      //初始化状态View
      TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ImgWithText);

      int zoomViewResId = a.getResourceId(R.styleable.ImgWithText_img, 0);
      if (zoomViewResId > 0) {
        img.setImageResource(zoomViewResId);
      }

      int headerViewResId = a.getResourceId(R.styleable.ImgWithText_text, 0);
      if (headerViewResId > 0) {
        tvContent.setText(headerViewResId);
      }

      int color = a.getColor(R.styleable.ImgWithText_textColor, Color.BLACK);
      tvContent.setTextColor(color);
      a.recycle();
    }

    this.addView(view);
  }

  public void setImgDrawable(int drawableID) {
    this.img.setBackgroundResource(drawableID);
  }

  public void setText(int textID) {
    this.tvContent.setText(textID);
  }
}

