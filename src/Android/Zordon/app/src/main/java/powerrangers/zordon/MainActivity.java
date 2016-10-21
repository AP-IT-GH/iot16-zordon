package powerrangers.zordon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import static android.R.attr.content;

public class MainActivity extends AppCompatActivity {

    String username = "xnkcayag";
    String password = "DtCGtuL2kVfk";
    String server = "ssl://m21.cloudmqtt.com:22452";
    String topic = "Android";
    String newmessage;
    int qos = 0;
    String clientId = MqttClient.generateClientId();
    public MqttAndroidClient client;

    protected static final int RESULT_SPEECH = 1;
    private Button PraatButton;
    private TextView GesprokenZin;
    Boolean send = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendMsg = (Button) findViewById(R.id.SendMessage);
        GesprokenZin = (TextView) findViewById(R.id.GesprokenZin);
        PraatButton = (Button) findViewById(R.id.PraatButton);


        client = new MqttAndroidClient(this.getApplicationContext(), server, clientId);

        try {
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(username);
            options.setPassword(password.toCharArray());

            IMqttToken token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.i("f", "GElukt");

                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.i("d", "GEfailed");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }



        if (client.isConnected() == true){
            TextView statusTxt = (TextView) findViewById(R.id.StatusLabel);
            statusTxt.setText("Status: Disconnected");
            Log.i("Status", "Disconnected");
        }
        else if (client.isConnected() == false){
            TextView statusTxt = (TextView) findViewById(R.id.StatusLabel);
            statusTxt.setText("Status: Connected");
            Log.i("Status", "Connected");
        }


        PraatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    GesprokenZin.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    GesprokenZin.setText(text.get(0));

                    String gesprokenpublish = GesprokenZin.getText().toString();

                    if (gesprokenpublish.contains("keuken")) {topic = "kitchen"; send = true;}
                    else{topic ="kappa"; newmessage="pride"; send = false;}

                    if (gesprokenpublish.contains("aan")) {newmessage = "on"; send = true;}
                    else if (gesprokenpublish.contains("uit")) { newmessage = "off"; send = true;}
                    else{topic ="kappa"; newmessage="pride"; send = false;}

                    if(send){
                        MqttMessage message = new MqttMessage(newmessage.getBytes());
                        message.setQos(qos);
                        message.setRetained(false);
                        try {
                            client.publish(topic, message);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }

                    }

                }
                break;
            }
        }
    }
    public void SendMessage(View view) throws MqttException {
        System.out.println("Message Arrived: " );
        
        int qos = 2;
        String content      = "Aan";
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        message.setRetained(false);
        client.publish("Keuken", message);


    }

    public void Connect(View view) {
        try {
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(username);
            options.setPassword(password.toCharArray());

            IMqttToken token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.i("f", "GElukt");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.i("d", "GEfailed");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}