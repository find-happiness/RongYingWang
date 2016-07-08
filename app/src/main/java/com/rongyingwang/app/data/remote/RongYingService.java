package com.rongyingwang.app.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public interface RongYingService {

  String ENDPOINT = "https://api.ribot.io/";
  String AiShangHost = "http://www.aishanglvju.com/";
  String IMG_URL = "http://www.aishanglvju.com/";

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

      Retrofit retrofit = new Retrofit.Builder().baseUrl(RongYingService.AiShangHost)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .client(httpClient.build())
          .build();

      return retrofit.create(RongYingService.class);
    }
  }
}
