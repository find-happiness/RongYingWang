package com.rongyingwang.app.data;

import com.rongyingwang.app.data.local.DatabaseHelper;
import com.rongyingwang.app.data.local.PreferencesHelper;
import com.rongyingwang.app.data.remote.RongYingService;
import com.rongyingwang.app.util.EventPosterHelper;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.functions.Action0;

@Singleton public class DataManager {

  private static final String TAG = "DataManager";
  private final RongYingService mAiShangService;
  private final DatabaseHelper mDatabaseHelper;
  private final PreferencesHelper mPreferencesHelper;
  private final EventPosterHelper mEventPoster;

  @Inject public DataManager(RongYingService aiShangService, PreferencesHelper preferencesHelper,
      DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
    mAiShangService = aiShangService;
    mPreferencesHelper = preferencesHelper;
    mDatabaseHelper = databaseHelper;
    mEventPoster = eventPosterHelper;
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
