package com.rongyingwang.app.ui.main;

import com.rongyingwang.app.data.model.InitResult;
import com.rongyingwang.app.ui.base.MvpView;

/**
 * Created by song on 2016/7/10.
 */
public interface MainFmMvpView extends MvpView {

  void showError(String messager);

  void showData(InitResult initResult);

  void showLoading();

  void dimissLoading();
}
