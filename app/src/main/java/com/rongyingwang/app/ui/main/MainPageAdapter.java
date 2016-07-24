package com.rongyingwang.app.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongyingwang.app.R;
import com.shizhefei.view.indicator.IndicatorViewPager;

import javax.inject.Inject;

/**
 * Created by song on 2016/1/16.
 */
public class MainPageAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
  private int[] tabIcons = {
      R.drawable.tab_main_selector, R.drawable.tab_mine_selector, R.drawable.tab_more_selector,
      R.drawable.tab_more_selector
  };
  private LayoutInflater inflater;

  @Inject MainActivity context;

  public MainPageAdapter(FragmentManager fragmentManager, MainActivity context) {
    super(fragmentManager);
    context = context;
    inflater = LayoutInflater.from(context);
  }

  @Override public int getCount() {
    return tabIcons.length;
  }

  @Override public View getViewForTab(int position, View convertView, ViewGroup container) {
    if (convertView == null) {
      convertView = (ImageView) inflater.inflate(R.layout.tab_main, container, false);
    }
    ImageView img = (ImageView) convertView;
    img.setImageResource(tabIcons[position]);
    return img;
  }

  @Override public Fragment getFragmentForPage(int position) {
    switch (position) {
      case 0:
        return new MainFragment();
      case 1:
        return new InvestFragment();
      case 2:
        return new LoginFragment();
      case 3:
        return new RegisterFragment();
    }
    return null;
  }
}
