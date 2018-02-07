package com.example.xiangxuchen.architecture_mvvm_opt.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.xiangxuchen.architecture_mvvm_opt.BuildConfig;
import com.example.xiangxuchen.architecture_mvvm_opt.constants.Constants;
import com.example.xiangxuchen.architecture_mvvm_opt.network.cookie.OkCookieManager;
import com.example.xiangxuchen.architecture_mvvm_opt.utils.common.AppUtils;
import com.example.xiangxuchen.architecture_mvvm_opt.utils.common.DeviceUtils;
import com.example.xiangxuchen.architecture_mvvm_opt.utils.common.SharedPreferencesUtils;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceFactory {


    public static final String ENV_DEV = "dev";
    public static final String ENV_TEST = "test";
    public static final String ENV_RELEASE = "release";


    //请求头的Host
    private static final String HOST_HEADER_DEV = "dev.api.nanguazufang.cn";
    private static final String HOST_HEADER_TEST = "test.api.nanguazufang.cn";


    //SERVE Host
    private static final String HOST_DEV_AND_TEST = "10.23.64.8";
    private static final String HOST_RELEASE = "api.nanguazufang.cn";


    private static String CURR_DEV;
    private static OkHttpClient httpClient;

    public static String SERVER;

    /**
     * called after init
     *
     * @param env
     */
    public static void switchEnv(String env) {
        final String BASE_URL;
        CURR_DEV = env;
        if (ENV_DEV.equals(env)) {
            SERVER = "http://" + HOST_DEV_AND_TEST;
        } else if (ENV_TEST.equals(env)) {
            SERVER = "http://" + HOST_DEV_AND_TEST;
        } else {
            SERVER = "http://" + HOST_RELEASE;
        }
        BASE_URL = SERVER + "/api/v1/";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
        SharedPreferencesUtils.putString(Constants.SP_KEY_ENV, env);
    }


    /**
     * 统计使用 pv.kuaizhan.com ，不论哪种环境
     *
     * @param urlHost
     * @return
     */
    public static String getHost(String urlHost) {
        if (HOST_DEV_AND_TEST.equals(urlHost)) {
            if (CURR_DEV.equals(ENV_DEV)) {
                return HOST_HEADER_DEV;
            } else {
                return HOST_HEADER_TEST;
            }
        } else {
            return null;
        }
    }


    public static String currEnv() {
        return CURR_DEV;
    }


    private static final long TIMEOUT = 7;

    private static Retrofit retrofit;

    private static ConcurrentHashMap<Class<?>, Object> services = new ConcurrentHashMap<>();

    public static <T> T getService(final Class<T> service) {
        if (services.contains(service)) {
            return (T) services.get(service);
        } else {
            T t = retrofit.create(service);
            services.put(service, t);
            return t;
        }
    }

    public static void init(Context application) {
        // 添加通用的Header
        httpClient = new OkHttpClient.Builder()
                // 添加通用的Header
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        String urlHost = chain.request().url().host();
                        Request.Builder builder = chain.request().newBuilder();
                        builder.header("User-Agent", System.getProperty("http.agent"));
                        builder.addHeader("X-App-Version", "Android/" + AppUtils.getAppVersionName());
                        builder.addHeader("X-Device-ID", DeviceUtils.getDeviceId());
                        builder.addHeader("X-Channel-Code", AppUtils.getUMengChannel());
                        String headHost = getHost(urlHost);
                        if (!TextUtils.isEmpty(headHost)) {
                            builder.header("Host", headHost);
                        }
                        return chain.proceed(builder.build());
                    }
                })
                .cookieJar(OkCookieManager.getInstance(application))
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
        String env = SharedPreferencesUtils.getString(Constants.SP_KEY_ENV);
        env = TextUtils.isEmpty(env) ? BuildConfig.ENV : env;
        switchEnv(env);
    }


}
