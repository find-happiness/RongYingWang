package com.rongyingwang.app.ui.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.john.waveview.WaveView;
import com.rongyingwang.app.R;
import com.rongyingwang.app.data.model.Invest;
import com.rongyingwang.app.data.model.MainBanner;
import com.rongyingwang.app.data.remote.RongYingService;
import com.rongyingwang.app.injection.ActivityContext;
import com.rongyingwang.app.ui.bid.BidDetailActivity;
import com.rongyingwang.app.util.CommonUtil;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by song on 2016/7/9.
 */
public class InvestAdapter extends RecyclerView.Adapter<InvestAdapter.InvestHolder> {

  private static final String TAG = "MainAdapter";

  private Context ctx;
  private LayoutInflater mLayoutInflater;

  private ArrayList<Invest> invests;

  @Inject public InvestAdapter(@ActivityContext Context ctx) {
    invests = new ArrayList<>();
    mLayoutInflater = LayoutInflater.from(ctx);
    this.ctx = ctx;
  }

  public void setInvests(ArrayList<Invest> invests) {
    this.invests = invests;
  }

  @Override public InvestAdapter.InvestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new InvestHolder(mLayoutInflater.inflate(R.layout.item_invest, parent, false));
  }

  @Override public void onBindViewHolder(InvestAdapter.InvestHolder holder, final int position) {

    InvestHolder holder2 = ((InvestHolder) holder);
    holder2.name.setText("abc " + position);
    int pro = (int) (Math.random() * 100);
    holder2.progress.setText(pro + "%");
    holder2.waveView.setProgress(pro);
    holder2.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ctx.startActivity(BidDetailActivity.getStartIntent(ctx, null));
      }
    });
  }

  @Override public int getItemCount() {
    return invests.size();
  }

  static class InvestHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.status) TextView status;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.rate) TextView rate;
    @BindView(R.id.deadline) TextView deadline;
    @BindView(R.id.wave_view) WaveView waveView;
    @BindView(R.id.progress) TextView progress;

    public InvestHolder(View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }
  }
}