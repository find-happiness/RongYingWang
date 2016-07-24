package com.rongyingwang.app.ui.main;

import com.rongyingwang.app.data.DataManager;
import com.rongyingwang.app.data.model.InitResult;
import com.rongyingwang.app.ui.base.BasePresenter;
import com.rongyingwang.app.util.Constants;
import com.rongyingwang.app.util.RongYingUtils;
import java.util.Map;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by song on 2016/7/10.
 */
public class MainFmPresenter extends BasePresenter<MainFmMvpView> {

  private final DataManager mDataManager;
  private Subscription mSubscription;

  @Inject public MainFmPresenter(DataManager dataManager) {
    mDataManager = dataManager;
  }

  @Override public void attachView(MainFmMvpView mvpView) {
    super.attachView(mvpView);
  }

  @Override public void detachView() {
    super.detachView();
    if (mSubscription != null) mSubscription.unsubscribe();
  }

  public void initNetwork() {
    checkViewAttached();
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }

    Map<String, String> queryMap = RongYingUtils.gennerParams("init");
    getMvpView().showLoading();
    mSubscription = mDataManager.init(queryMap)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Subscriber<InitResult>() {
          @Override public void onCompleted() {
          }

          @Override public void onError(Throwable e) {
            getMvpView().dimissLoading();
            getMvpView().showError(Constants.RESPONSE_ERROR);
          }

          @Override public void onNext(InitResult initResult) {
            getMvpView().dimissLoading();
            if (initResult.getResponse_code() == 1) {
              getMvpView().showData(initResult);
            } else {
              getMvpView().showError(Constants.RESPONSE_ERROR);
            }
          }
        });
  }
}
