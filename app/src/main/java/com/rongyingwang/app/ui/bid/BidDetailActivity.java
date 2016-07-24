package com.rongyingwang.app.ui.bid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rongyingwang.app.R;
import com.rongyingwang.app.data.model.InitResult;
import com.rongyingwang.app.ui.base.BaseActivity;

public class BidDetailActivity extends BaseActivity {

  public static final String ID = "id";
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.name) TextView name;
  @BindView(R.id.contract) TextView contract;
  @BindView(R.id.borrow_amount_format) TextView borrowAmountFormat;
  @BindView(R.id.need_money) TextView needMoney;
  @BindView(R.id.min_loan_money_format) TextView minLoanMoneyFormat;
  @BindView(R.id.rate_foramt_w) TextView rateForamtW;
  @BindView(R.id.repay_time) TextView repayTime;
  @BindView(R.id.loantype) TextView loantype;
  @BindView(R.id.rish_rank) TextView rishRank;
  @BindView(R.id.remain_time_format) TextView remainTimeFormat;

  public static Intent getStartIntent(Context ctx,
      InitResult.IndexListEntity.DealListEntity entity) {
    Intent intent = new Intent(ctx, BidDetailActivity.class);
    intent.putExtra(ID, entity);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bid_detail);
    ButterKnife.bind(this);

    InitResult.IndexListEntity.DealListEntity entity =
        (InitResult.IndexListEntity.DealListEntity) this.getIntent().getSerializableExtra(ID);
    initView();

    if (entity != null) {
      name.setText(entity.getColor_name());
      contract.setText("合同编号:" + entity.getSub_name());
      borrowAmountFormat.setText(entity.getBorrow_amount_format());
      needMoney.setText(entity.getNeed_money());
      minLoanMoneyFormat.setText(entity.getMin_loan_money_format());
      rateForamtW.setText(entity.getRate_foramt_w());
      StringBuffer sb = new StringBuffer(entity.getRepay_time());
      switch (entity.getRepay_time_type()) {
        case "0":
          sb.append("天");
          break;
        case "1":
          sb.append("月");
          break;
      }
      repayTime.setText(sb.toString());

      String loanType = "";
      switch (entity.getLoantype()) {
        case "0":
          loanType = "等额本息";
          break;
        case "1":
          loanType = "付息还本";
          break;
        case "2":
          loanType = "到期还本息";
          break;
      }
      loantype.setText(loanType);

      //;1:中;2:高
      String rish_rank = "低";
      switch (entity.getRisk_rank()) {
        case "0":
          rish_rank = "低";
          break;
        case "1":
          rish_rank = "中";
          break;
        case "2":
          rish_rank = "高";
          break;
        default:
          rish_rank = "低";
          break;
      }

      rishRank.setText(rish_rank);
      remainTimeFormat.setText(entity.getRemain_time_format());
    }
  }

  private void initView() {
    initToolbar();
  }

  private void initToolbar() {
    toolbar.setTitle("");
    this.setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.iconfont_livesvg);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
  }
}
