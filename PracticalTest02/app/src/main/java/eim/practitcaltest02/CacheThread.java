package eim.practitcaltest02;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Map;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class CacheThread extends Thread {
    Map<String, String> cache;

    boolean isRunning = false;

    public CacheThread(Map<String, String> cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String currency = "USD";

                HttpClient client = new DefaultHttpClient();

                HttpGet request = new HttpGet(Constants.SITE + currency + ".json");
                ResponseHandler<String> handler = new BasicResponseHandler();

                String page_source = client.execute(request, handler);

                JSONObject content = new JSONObject(page_source);

                JSONObject bpi = new JSONObject(content.getString("bpi"));
                JSONObject jcurrency = new JSONObject(bpi.getString(currency));

                String rate = jcurrency.getString("rate");

                JSONObject time = new JSONObject(content.getString("time"));

                String updated = jcurrency.getString("updated");

                cache.put("updatedUSD", updated);
                cache.put("rateUSD", rate);

                currency = "EUR";

                client = new DefaultHttpClient();

                request = new HttpGet(Constants.SITE + currency + ".json");
                handler = new BasicResponseHandler();

                page_source = client.execute(request, handler);

                content = new JSONObject(page_source);

                bpi = new JSONObject(content.getString("bpi"));
                jcurrency = new JSONObject(bpi.getString(currency));

                rate = jcurrency.getString("rate");

                time = new JSONObject(content.getString("time"));

                updated = jcurrency.getString("updated");

                cache.put("updatedEUR", "");
                cache.put("rateEUR", "");


                Thread.sleep(1 *   // minutes to sleep
                        60 *   // seconds to a minute
                        1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
