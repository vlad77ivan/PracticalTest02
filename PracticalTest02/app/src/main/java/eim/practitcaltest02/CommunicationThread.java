package eim.practitcaltest02;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class CommunicationThread extends Thread {
    Socket socket;
    Dictionary<String, String> cache;

    public CommunicationThread(Socket socket, Dictionary<String, String> cache) {
        this.socket = socket;
        this.cache = cache;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = Utilities.getReader(socket);
            PrintWriter writer = Utilities.getWriter(socket);

            String currency = reader.readLine();

            if (currency.equals("USD")){
                writer.println(cache.get("rateUSD"));
                writer.flush();
            }

            if (currency.equals("EUR")){
                writer.println(cache.get("rateUSD"));
                writer.flush();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
