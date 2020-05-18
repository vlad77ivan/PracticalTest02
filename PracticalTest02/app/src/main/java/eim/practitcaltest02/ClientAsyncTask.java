package eim.practitcaltest02;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientAsyncTask extends AsyncTask<String, String, Void> {
    TextView view;

    public ClientAsyncTask(TextView view) {
        this.view = view;
    }
    @Override
    protected Void doInBackground(String... strings) {
        try {
            String address = strings[0],
                    port = strings[1],
                    currency = strings[2];

            Socket socket = new Socket(address, Integer.valueOf(port));

            PrintWriter writer = Utilities.getWriter(socket);

            BufferedReader reader = Utilities.getReader(socket);

            String rate = reader.readLine();

            publishProgress(rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(String... result) {
        view.setText(result[0]);
    }

    protected void onPostExecute() {
    }
}
