package com.rongyingwang.app.data;

import com.rongyingwang.app.BuildConfig;
import com.rongyingwang.app.data.local.DatabaseHelper;
import com.rongyingwang.app.data.local.PreferencesHelper;
import com.rongyingwang.app.data.model.InitResult;
import com.rongyingwang.app.data.remote.RongYingService;
import com.rongyingwang.app.util.EventPosterHelper;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Action0;

@Singleton public class DataManager {

  private static final String TAG = "DataManager";
  private final RongYingService mRongYingService;
  private final DatabaseHelper mDatabaseHelper;
  private final PreferencesHelper mPreferencesHelper;
  private final EventPosterHelper mEventPoster;
  String path = BuildConfig.DEBUG ? RongYingService.DebugApi : RongYingService.RelaseApi;

  @Inject public DataManager(RongYingService rongYingService, PreferencesHelper preferencesHelper,
      DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
    mRongYingService = rongYingService;
    mPreferencesHelper = preferencesHelper;
    mDatabaseHelper = databaseHelper;
    mEventPoster = eventPosterHelper;
  }

  public Observable<InitResult> init(Map<String, String> map) {
    return mRongYingService.init(path, map);
  }

  public PreferencesHelper getPreferencesHelper() {
    return mPreferencesHelper;
  }

  /// Helper method to post events from doOnCompleted.
  private Action0 postEventAction(final Object event) {
    return new Action0() {
      @Override public void call() {
        mEventPoster.postEventSafely(event);
      }
    };
  }
}
