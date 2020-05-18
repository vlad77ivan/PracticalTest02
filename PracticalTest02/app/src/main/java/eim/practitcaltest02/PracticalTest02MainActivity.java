package eim.practitcaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

public class PracticalTest02MainActivity extends AppCompatActivity {

    EditText editServerPort, editClientAddress, editClientPort;
    Button buttonStartServer, buttonClientRequest;
    TextView viewCurrencyValue;
    Spinner spinnerCurrencyType;

    ServerThread serverThread = null;



    protected class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonServer:
                    if (serverThread == null) {
                        serverThread = new ServerThread(editServerPort);
                        serverThread.startServer();
                    }
                    break;
                case R.id.buttonRequest:
                    ClientAsyncTask task = new ClientAsyncTask(viewCurrencyValue);
                    task.execute(editClientAddress.getText().toString(), editClientPort.getText().toString(), spinnerCurrencyType.getSelectedItem().toString());
                    break;
            }
        }
    }
    ButtonListener blistener = new ButtonListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        editServerPort = (EditText) findViewById(R.id.editPort);
        buttonStartServer = (Button) findViewById(R.id.buttonServer);

        editClientAddress = (EditText) findViewById(R.id.editAddressC);
        editClientPort = (EditText) findViewById(R.id.editPortC);
        spinnerCurrencyType = (Spinner) findViewById(R.id.spinnerCurrency);
        buttonClientRequest = (Button) findViewById(R.id.buttonRequest);

        viewCurrencyValue = (TextView) findViewById(R.id.textViewValue);

        buttonStartServer.setOnClickListener(blistener);
        buttonClientRequest.setOnClickListener(blistener);
    }

    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            try {
                serverThread.stopServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
