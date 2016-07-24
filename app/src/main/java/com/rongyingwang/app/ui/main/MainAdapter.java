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
import com.rongyingwang.app.data.model.InitResult;
import com.rongyingwang.app.data.model.InitResult.IndexListEntity;
import com.rongyingwang.app.data.model.MainBanner;
import com.rongyingwang.app.data.remote.RongYingService;
import com.rongyingwang.app.injection.ActivityContext;
import com.rongyingwang.app.ui.bid.BidDetailActivity;
import com.rongyingwang.app.util.CommonUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by song on 2016/7/8.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final String TAG = "MainAdapter";

  public enum ITEM_TYPE {
    ITEM1,
    ITEM2
  }

  private Context ctx;
  private LayoutInflater mLayoutInflater;

  private List<IndexListEntity.DealListEntity> invests;

  private MainBanner banner;

  @Inject public MainAdapter(@ActivityContext Context ctx) {
    invests = new ArrayList<>();
    mLayoutInflater = LayoutInflater.from(ctx);
    this.ctx = ctx;
  }

  public void setInvests(List<IndexListEntity.DealListEntity> invests) {
    this.invests = invests;
  }

  public void setBanner(MainBanner banner) {
    this.banner = banner;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //加载Item View的时候根据不同TYPE加载不同的布局
    if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
      return new Item1Holder(mLayoutInflater.inflate(R.layout.head_main_fragment, parent, false));
    } else {
      return new Item2Holder(mLayoutInflater.inflate(R.layout.item_invest, parent, false));
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    if (holder instanceof Item1Holder) {
      initBanner(((Item1Holder) holder).banner);
    } else if (holder instanceof Item2Holder) {
      Item2Holder holder2 = ((Item2Holder) holder);

      final IndexListEntity.DealListEntity entity = invests.get(position - 1);

      float progress_point = Float.parseFloat(entity.getProgress_point());

      if (progress_point < 100.0) {
        holder2.status.setText("进行中");
      }

      holder2.progress.setText(progress_point + "%");

      holder2.name.setText(entity.getColor_name());
      holder2.waveView.setProgress((int) progress_point);
      holder2.price.setText(entity.getBorrow_amount_format());
      holder2.rate.setText(entity.getRate_foramt_w());

      StringBuffer sb = new StringBuffer(entity.getRepay_time());
      switch (entity.getRepay_time_type()) {
        case "0":
          sb.append("天");
          break;
        case "1":
          sb.append("月");
          break;
      }

      holder2.deadline.setText(sb.toString());

      holder2.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          ctx.startActivity(BidDetailActivity.getStartIntent(ctx, entity));
        }
      });
    }
  }

  @Override public int getItemCount() {
    return invests.size() + 1;
  }

  @Override public int getItemViewType(int position) {
    return position == 0 ? ITEM_TYPE.ITEM1.ordinal() : ITEM_TYPE.ITEM2.ordinal();
  }

  private void initBanner(final ConvenientBanner banner) {

    int[] size = CommonUtil.getHeightWithScreenWidth((Activity) ctx, 640, 240);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size[0], size[1]);

    banner.setLayoutParams(layoutParams);

    Observable.from(this.banner.getAdvListEntities())
        .map(new Func1<InitResult.IndexListEntity.AdvListEntity, String>() {
          @Override public String call(InitResult.IndexListEntity.AdvListEntity advListEntity) {
            return advListEntity.getImg();
          }
        })
        .toList()
        .subscribe(new Action1<List<String>>() {
          @Override public void call(List<String> urls) {
            banner.setPages(new CBViewHolderCreator<NetImageHolderView>() {
              @Override public NetImageHolderView createHolder() {
                return new NetImageHolderView();
              }
            }, urls)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(
                    new int[] { R.drawable.ellipse_nomal_2, R.drawable.ellipse_select_2 })
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
          }
        });
  }

  static class Item1Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.banner) ConvenientBanner banner;

    public Item1Holder(View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }
  }

  static class Item2Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.status) TextView status;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.rate) TextView rate;
    @BindView(R.id.deadline) TextView deadline;
    @BindView(R.id.wave_view) WaveView waveView;
    @BindView(R.id.progress) TextView progress;

    public Item2Holder(View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }
  }

  static class NetImageHolderView implements Holder<String> {
    private ImageView imageView;

    public View createView(Context context) {
      imageView = new ImageView(context);
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      return imageView;
    }

    @Override public void UpdateUI(Context context, int position, String data) {

      Glide.with(context)
          .load(data)
          .error(R.drawable.banner)
          .placeholder(R.drawable.banner)
          .into(imageView);
    }
  }
}
