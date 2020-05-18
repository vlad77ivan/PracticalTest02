package eim.practitcaltest02;

import android.widget.EditText;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread {
    EditText serverPort;
    ServerSocket serverSocket;

    boolean isRunning = false;

    Map<String, String> cache;

    public ServerThread(EditText serverPort) {
        this.serverPort = serverPort;
    }

    public void startServer() {
        isRunning = true;
        start();
    }

    public void stopServer() throws IOException {
        if (serverSocket != null)
            serverSocket.close();
    }

    @Override
    public void run() {
        cache = new HashMap<String, String>();
        cache.put("updatedUSD", "");
        cache.put("updatedEUR", "");

        cache.put("rateUSD", "");
        cache.put("rateEUR", "");

        CacheThread cachee = new CacheThread(cache);
        cachee.start();

        try {
            serverSocket = new ServerSocket(Integer.valueOf(serverPort.getText().toString()));
            while (isRunning) {
                Socket socket = serverSocket.accept();

                if (socket != null) {
                    CommunicationThread comm = new CommunicationThread(socket, cache);
                    comm.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
