package com.rongyingwang.app.injection.component;

import com.rongyingwang.app.injection.PerActivity;
import com.rongyingwang.app.injection.module.ActivityModule;
import com.rongyingwang.app.ui.MainActivity;
import dagger.Component;;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(MainActivity mainActivity);
}
