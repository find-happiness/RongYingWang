package com.rongyingwang.app.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rongyingwang.app.R;
import com.rongyingwang.app.data.model.InitResult;
import com.rongyingwang.app.data.model.Invest;
import com.rongyingwang.app.data.model.MainBanner;
import com.rongyingwang.app.util.CommonUtil;
import com.rongyingwang.app.util.NetworkUtil;
import com.rongyingwang.app.widget.SpacesItemDecoration;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements MainFmMvpView {

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.avloadingIndicatorView) AVLoadingIndicatorView avloadingIndicatorView;
  @BindView(R.id.no_data) TextView noData;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @BindView(R.id.layoutRoot) CoordinatorLayout layoutRoot;

  @Inject MainAdapter mainAdapter;
  @Inject MainFmPresenter presenter;

  public static MainFragment newInstance(String param1, String param2) {
    MainFragment fragment = new MainFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  public MainFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((MainActivity) this.getActivity()).getActivityComponent().inject(this);
    presenter.attachView(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, view);
    initView();
    return view;
  }

  private void initView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new SpacesItemDecoration(
        this.getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

    avloadingIndicatorView.setVisibility(View.VISIBLE);

    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        loadData();
      }
    });
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loadData();
  }

  @Override public void showError(String message) {

    CommonUtil.toastMessage(this.getContext(), message);
  }

  @Override public void showData(InitResult initResult) {

    InitResult.IndexListEntity indexListEntity = initResult.getIndex_list();

    MainBanner banner = new MainBanner();
    banner.setAdvListEntities(indexListEntity.getAdv_list());
    mainAdapter.setBanner(banner);

    mainAdapter.setInvests(indexListEntity.getDeal_list());

    mainAdapter.notifyDataSetChanged();

    recyclerView.setAdapter(mainAdapter);
  }

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void dimissLoading() {
    swipeRefresh.setRefreshing(false);
    avloadingIndicatorView.setVisibility(View.GONE);
  }

  private void loadData() {
    if (NetworkUtil.isNetworkConnected(MainFragment.this.getActivity())) {
      presenter.initNetwork();
    } else {
      CommonUtil.toastMessage(this.getContext(), R.string.no_net);
    }
  }
}
