package com.rongyingwang.app.util;

import android.os.Handler;
import android.os.Looper;
import happiness.okhttpcookie.CookieJarImpl;
import happiness.okhttpcookie.store.CookieStore;
import happiness.okhttpcookie.store.HasCookieStore;
import happiness.okhttpcookie.store.MemoryCookieStore;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by song on 2016/4/28.
 */
public class OkHttpUtils {
  public static final long DEFAULT_MILLISECONDS = 10000;
  private static OkHttpUtils mInstance;
  private OkHttpClient mOkHttpClient;
  private Handler mDelivery;

  public OkHttpUtils(OkHttpClient okHttpClient) {
    if (okHttpClient == null) {
      OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
      //cookie enabled
      okHttpClientBuilder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
      okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
        @Override public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });

      mOkHttpClient = okHttpClientBuilder.build();
    } else {
      mOkHttpClient = okHttpClient;
    }

    init();
  }

  private void init() {
    mDelivery = new Handler(Looper.getMainLooper());
  }

  public OkHttpUtils debug() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    mOkHttpClient = getOkHttpClient().newBuilder().addInterceptor(logging).build();
    return this;
  }

  public static OkHttpUtils getInstance(OkHttpClient okHttpClient) {
    if (mInstance == null) {
      synchronized (OkHttpUtils.class) {
        if (mInstance == null) {
          mInstance = new OkHttpUtils(okHttpClient);
        }
      }
    }
    return mInstance;
  }

  public static OkHttpUtils getInstance() {
    if (mInstance == null) {
      synchronized (OkHttpUtils.class) {
        if (mInstance == null) {
          mInstance = new OkHttpUtils(null);
        }
      }
    }
    return mInstance;
  }

  public Handler getDelivery() {
    return mDelivery;
  }

  public OkHttpClient getOkHttpClient() {
    return mOkHttpClient;
  }

  public CookieStore getCookieStore() {
    final CookieJar cookieJar = mOkHttpClient.cookieJar();
    if (cookieJar == null) {
      new IllegalArgumentException(
          "you should invoked okHttpClientBuilder.cookieJar() to set a cookieJar.");
    }
    if (cookieJar instanceof HasCookieStore) {
      return ((HasCookieStore) cookieJar).getCookieStore();
    } else {
      return null;
    }
  }

  public void setConnectTimeout(int timeout, TimeUnit units) {
    mOkHttpClient = getOkHttpClient().newBuilder().connectTimeout(timeout, units).build();
  }

  public void setReadTimeout(int timeout, TimeUnit units) {
    mOkHttpClient = getOkHttpClient().newBuilder().readTimeout(timeout, units).build();
  }

  public void setWriteTimeout(int timeout, TimeUnit units) {
    mOkHttpClient = getOkHttpClient().newBuilder().writeTimeout(timeout, units).build();
  }
}
