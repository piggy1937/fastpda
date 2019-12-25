package com.step.pda.app.net.interceptor;

import com.step.pda.app.Configurator;
import com.step.pda.app.Pda;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhushubin
 * @date 2019-12-25.
 * GitHub：
 * email： 604580436@qq.com
 * description：
 */
public class MainInterceptor implements Interceptor {
    private static MainInterceptor sInterceptor;
    private String mScheme;
    private String mHost;

    public static MainInterceptor get() {
        if (sInterceptor == null) {
            sInterceptor = new MainInterceptor();
        }
        return sInterceptor;
    }

    private MainInterceptor() {
    }



    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = (String) Pda.getConfigurations().get(Configurator.ConfigType.API_HOST.name());
        HttpUrl httpUrl = HttpUrl.parse(url);
        mScheme = httpUrl.scheme();
        mHost = httpUrl.host();
        Request original = chain.request();
        if (mScheme != null && mHost != null) {
            HttpUrl newUrl = original.url().newBuilder()
                    .scheme(mScheme)
                    .host(mHost)
                    .build();
            original = original.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(original);
    }
}
