package com.rongyingwang.app.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2016/7/11.
 */
public class RongYingUtils {

  public static Map<String, String> gennerParams(String act) {

    Map<String, String> queryMap = new HashMap<>();
    queryMap.put("act", "init");
    queryMap.put("i_type", "1");
    queryMap.put("r_type", "0");
    queryMap.put("app_type", "2");

    return queryMap;
  }
}
