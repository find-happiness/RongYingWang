package com.rongyingwang.app.ui.main;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rongyingwang.app.R;
import com.rongyingwang.app.data.model.Invest;
import com.rongyingwang.app.widget.SpacesItemDecoration;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvestFragment extends Fragment {

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.avloadingIndicatorView) AVLoadingIndicatorView avloadingIndicatorView;
  @BindView(R.id.no_data) TextView noData;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @BindView(R.id.layoutRoot) CoordinatorLayout layoutRoot;

  @Inject InvestAdapter investAdapter;

  public static InvestFragment newInstance(String param1, String param2) {
    InvestFragment fragment = new InvestFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  public InvestFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
    }

    ((MainActivity) this.getActivity()).getActivityComponent().inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_invest, container, false);
    ButterKnife.bind(this, view);
    initView();

    ArrayList<Invest> listInvers = new ArrayList<>();

    for (int i = 0; i < 20; i++) {
      listInvers.add(new Invest());
    }

    investAdapter.setInvests(listInvers);
    return view;
  }

  private void initView() {

    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(
        new SpacesItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.spacing_medium),
            true));
    recyclerView.setAdapter(investAdapter);
    avloadingIndicatorView.setVisibility(View.GONE);
  }
}
