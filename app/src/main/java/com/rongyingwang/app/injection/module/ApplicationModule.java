package com.rongyingwang.app.injection.module;

import android.app.Application;
import android.content.Context;
import com.rongyingwang.app.data.remote.RongYingService;
import com.rongyingwang.app.injection.ApplicationContext;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton RongYingService provideRibotsService() {
        return RongYingService.Creator.newAiShangService();
    }

}
