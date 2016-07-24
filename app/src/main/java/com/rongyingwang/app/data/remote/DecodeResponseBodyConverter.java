package com.rongyingwang.app.data.remote;

import com.google.gson.TypeAdapter;
import com.rongyingwang.app.util.Base64;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by song on 2016/7/11.
 */
public class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final TypeAdapter<T> adapter;

  DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
    this.adapter = adapter;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    //解密字符串
    return adapter.fromJson(new String (Base64.decode(value.string(), Base64.DEFAULT)));
  }
}
