package mx.sos.sos.net;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import mx.sos.sos.BuildConfig;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;
import retrofit.client.OkClient;


public final class SOSApiSingleton {

    private static final String LOG_TAG = SOSApiSingleton.class.getName();

    private static final long MAX_CACHE_SIZE = 10 * 1024 * 1024;    // 10 MB
    private static final long CONNECT_TIMEOUT_MILLIS = 100L * 1000L; // 15 seconds
    private static final long READ_TIMEOUT_MILLIS = 20L * 1000L;    // 20 seconds

    private static SOSApiInterface sSOSAPiInterface;

    private SOSApiSingleton() {
        /* Do nothing */
    }

    public synchronized static SOSApiInterface getApiInterfaceInstance() {
        if (sSOSAPiInterface == null) {

            final RestAdapter.LogLevel logLevel = BuildConfig.DEBUG ? LogLevel.FULL
                    : RestAdapter.LogLevel.NONE;

            final OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

            final Client client = new OkClient(okHttpClient);

            final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(logLevel)
                    .setEndpoint(SOSApiBaseUrl.URL_API_BASE).setClient(client).build();
            sSOSAPiInterface = restAdapter.create(SOSApiInterface.class);
        }
        return sSOSAPiInterface;
    }

}
