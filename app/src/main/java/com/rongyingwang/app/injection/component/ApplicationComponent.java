package com.rongyingwang.app.injection.component;

import android.app.Application;
import android.content.Context;
import com.rongyingwang.app.data.DataManager;
import com.rongyingwang.app.data.local.DatabaseHelper;
import com.rongyingwang.app.data.local.PreferencesHelper;
import com.rongyingwang.app.data.remote.RongYingService;
import com.rongyingwang.app.injection.ApplicationContext;
import com.rongyingwang.app.injection.module.ApplicationModule;
import com.squareup.otto.Bus;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
    RongYingService liveService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    Bus eventBus();

}
