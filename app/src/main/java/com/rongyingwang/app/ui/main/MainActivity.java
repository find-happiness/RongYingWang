package com.rongyingwang.app.ui.main;

import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rongyingwang.app.R;
import com.rongyingwang.app.ui.base.BaseActivity;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

public class MainActivity extends BaseActivity {

  @BindView(R.id.tabmain_viewPager) SViewPager mViewPager;
  @BindView(R.id.tabmain_indicator) FixedIndicatorView mIndicator;
  private IndicatorViewPager indicatorViewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initView();
    setBackEnable(false);
  }

  private void initView() {

    indicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
    indicatorViewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager(), this));
    indicatorViewPager.setPageOffscreenLimit(3);
    mViewPager.setCanScroll(true);
  }
}
