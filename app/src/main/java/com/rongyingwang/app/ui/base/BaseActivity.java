package com.rongyingwang.app.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.github.anzewei.parallaxbacklayout.ParallaxActivityBase;
import com.rongyingwang.app.RongYingApplication;
import com.rongyingwang.app.injection.component.ActivityComponent;
import com.rongyingwang.app.injection.component.DaggerActivityComponent;
import com.rongyingwang.app.injection.module.ActivityModule;

public class BaseActivity extends ParallaxActivityBase {

  private ActivityComponent mActivityComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public ActivityComponent getActivityComponent() {
    if (mActivityComponent == null) {
      mActivityComponent = DaggerActivityComponent.builder()
          .activityModule(new ActivityModule(this))
          .applicationComponent(RongYingApplication.get(this).getComponent())
          .build();
    }
    return mActivityComponent;
  }
}
