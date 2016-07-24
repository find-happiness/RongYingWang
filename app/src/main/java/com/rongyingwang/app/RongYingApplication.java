package com.rongyingwang.app;

import android.app.Application;
import android.content.Context;
import com.rongyingwang.app.injection.component.ApplicationComponent;
import com.rongyingwang.app.injection.component.DaggerApplicationComponent;
import com.rongyingwang.app.injection.module.ApplicationModule;

/**
 * Created by song on 2016/7/8.
 */
public class RongYingApplication extends Application {
  ApplicationComponent mApplicationComponent;

  public static RongYingApplication get(Context context) {
    return (RongYingApplication) context.getApplicationContext();
  }

  public ApplicationComponent getComponent() {
    if (mApplicationComponent == null) {
      mApplicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
    return mApplicationComponent;
  }

  // Needed to replace the component with a test specific one
  public void setComponent(ApplicationComponent applicationComponent) {
    mApplicationComponent = applicationComponent;
  }
}
