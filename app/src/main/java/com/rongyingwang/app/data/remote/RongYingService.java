package com.rongyingwang.app.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rongyingwang.app.BuildConfig;
import com.rongyingwang.app.data.model.InitResult;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RongYingService {

  String RongYingHostDebug = "http://t.zghqzc.cn/";
  String RongYingHost = "http://www.rongyingwang.com/";

  String DebugApi = "mapi";
  String RelaseApi = "mapi/index.php";

  @GET("/{path}") Observable<InitResult> init(@Path("path") String path,
      @QueryMap Map<String, String> options);

  /******** Helper class that sets up a new services *******/
  class Creator {

    public static RongYingService newAiShangService() {
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
      //
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      //// set your desired log level
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      //
      OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
      //
      //// add logging as last interceptor
      httpClient.interceptors().add(logging);  // <-- this is the important line!
      //httpClient.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

      Retrofit retrofit = new Retrofit.Builder().baseUrl(
          BuildConfig.DEBUG ? RongYingService.RongYingHostDebug : RongYingService.RongYingHost)
          .addConverterFactory(
              DecodeConverterFactory.create(gson))//GsonConverterFactory.create(gson)
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .client(httpClient.build())
          .build();

      return retrofit.create(RongYingService.class);
    }
  }
}
