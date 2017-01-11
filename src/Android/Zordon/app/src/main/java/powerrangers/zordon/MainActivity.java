package powerrangers.zordon;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Connection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
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
import android.widget.ToggleButton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.util.Strings;

import java.io.UnsupportedEncodingException;

import static android.R.attr.content;

public class MainActivity extends AppCompatActivity {

    MqttAndroidClient client;

    String server = "ssl://m21.cloudmqtt.com:22452";
    String username = "xnkcayag";
    String password = "DtCGtuL2kVfk";

    String topic = "Android/#";
    String newmessage;
    int qos = 1;
    String clientId = MqttClient.generateClientId();

    String Manueelsend = "test";
    private Adapter mAdapter;

    private ListView lv;

    String[] Places = {"keuken", "slaapkamer", "berging", "badkamer", "deur"};;

    protected static final int RESULT_SPEECH = 1;
    private ImageButton PraatButton;
    private TextView GesprokenZin;
    private TextView LatestMessage;
    private TextView KamerTemp;
    private TextView ConnectStatus;
    private Button Devices;
    private ToggleButton Verwarming;

    Boolean send = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendMsg = (Button) findViewById(R.id.SendMessage);
        GesprokenZin = (TextView) findViewById(R.id.GesprokenZin);
        PraatButton = (ImageButton) findViewById(R.id.PraatButton);
        ConnectStatus = (TextView) findViewById(R.id.StatusLabel);
        lv = (ListView) findViewById(R.id.lv);

        //String[] array = getIntent().getStringArrayExtra("lijst");



        //Speech To Text API
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



        //Connect to Mqtt
        client = new MqttAndroidClient(this.getApplicationContext(), server, clientId);
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String server) {
                if (reconnect) {
                    addToHistory("Reconnected to: " + server);
                    ConnectStatus.setText("Status: Connected");
                    SubscribeToTopic();
                } else {
                    addToHistory("Connected to: " + server);
                    ConnectStatus.setText("Status: Connected");
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                addToHistory("The connection was lost.");
                ConnectStatus.setText("Status: Disconnected");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                addToHistory("Incoming message: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setConnectionTimeout(240000);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            client.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    client.setBufferOpts(disconnectedBufferOptions);
                    SubscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("failed to connect to: " + server);
                }
            });
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    private void addToHistory(String mainText){
        System.out.println("LOG: " + mainText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    public void SubscribeToTopic(){
        try {
            client.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to subscribe");
                }
            });

            //Subscribe to all topics on Android
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.bell3);
            client.subscribe(topic, 0, new IMqttMessageListener() {
                @Override
                public void messageArrived(final String topic, MqttMessage message) throws Exception {
                    final MqttMessage test = message;
                    System.out.println("Message: " + "Android/#" + " : " + new String(message.getPayload()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //sensor
                                KamerTemp = (TextView) findViewById(R.id.KamerTemp);
                                KamerTemp.setText("Kamertemperatuur: " + new String(test.getPayload()));

                            //Stopcontact
                            LatestMessage = (TextView) findViewById(R.id.SubMessage);
                            LatestMessage.setText("Latest message: "+ new String(test.getPayload()));
                            ImageView image = (ImageView) findViewById(R.id.imageView);
                            if(new String(test.getPayload()).contains("on"))
                                image.setImageResource(R.mipmap.on);
                            if(new String(test.getPayload()).contains("off"))
                                image.setImageResource(R.mipmap.pixelbulbart);
                            if(new String(test.getPayload()).contains("bel")){
                                mp.start();
                            }

                        }
                    });
                }
            });
        } catch (MqttException ex){
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public void publishMessage(){
        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(Manueelsend.getBytes());
            client.publish(topic, message);
            addToHistory("Message Published");
            if(!client.isConnected()){
                addToHistory(client.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
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

                    if (gesprokenpublish.contains(Places[0])) {topic = "Android/kitchen"; send = true;}
                    else if (gesprokenpublish.contains(Places[1])) {topic = "Android/slaapkamer"; send = true;}
                    else if (gesprokenpublish.contains(Places[2])) {topic = "Android/berging"; send = true;}
                    else if (gesprokenpublish.contains(Places[3])) {topic = "Android/badkamer"; send = true;}
                    else if (gesprokenpublish.contains(Places[4])) {topic = "Android/Deurslot"; send = true;}
                    else{topic ="Not understood"; newmessage="Not understood"; send = false;}

                    if (gesprokenpublish.contains("aan")) {newmessage = "on"; send = true;}
                    else if (gesprokenpublish.contains("uit")) { newmessage = "off"; send = true;}

                    else if (gesprokenpublish.contains("vast")) { newmessage = "vast"; send = true;}
                    else if (gesprokenpublish.contains("los")) { newmessage = "los"; send = true;}

                    else{topic ="Not understood"; newmessage="Not understood"; send = false;}

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
        publishMessage();
    }

    public void Devices(View view) {
        startActivity(new Intent(MainActivity.this, devices.class));

    }
}