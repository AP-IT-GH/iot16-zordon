int switchPin = D1;
boolean lastButton = LOW;
boolean currentButton = LOW;

#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Thermistor.h>

const char *ssid =  "Connectify-BOYD";   // cannot be longer than 32 characters!
const char *pass =  "aphogeschool";

const char *mqtt_server = "m21.cloudmqtt.com";
const int mqtt_port = 12452;
const char *mqtt_user = "xnkcayag";
const char *mqtt_pass = "DtCGtuL2kVfk";
const char *mqtt_client_name = "Weemo_deurbel"; // Client connections cant have the same connection name

String Deurbel = "Android/Deurbel"; // Subscribe to this topic and publish with this as context
String TemperatuurSensor = "Android/kamertemperatuur"; // Subscribe to this topic and publish with this as context
String verwarming = "Android/verwarming";

long previousMillis = 0;    
long interval = 10000; 

WiFiClient wclient;
PubSubClient client(wclient, mqtt_server, mqtt_port);

#define BUFFER_SIZE 100

Thermistor temp(0);

void setup()
{
  pinMode(switchPin, INPUT);
  pinMode(A0, OUTPUT);
  pinMode(D2, OUTPUT);
  Serial.begin(9600);
}

boolean debounce(boolean last)
{
  boolean current = digitalRead(switchPin);
  if (last != current)
  {
     delay(5);
    current = digitalRead(switchPin);
  }
  return current;
}

void loop(){
    unsigned long currentMillis = millis();
    int temperature = temp.getTemp();
    if (WiFi.status() != WL_CONNECTED) {
    Serial.print("Connecting to ");
    Serial.print(ssid);
    Serial.println("...");
    WiFi.begin(ssid, pass);

    if (WiFi.waitForConnectResult() != WL_CONNECTED)
      return;
    Serial.println("WiFi connected");
  }

  if (WiFi.status() == WL_CONNECTED) {
    if (!client.connected()) {
      Serial.println("Connecting to MQTT server");
      if (client.connect(MQTT::Connect(mqtt_client_name)
                         .set_auth(mqtt_user, mqtt_pass))) {
        Serial.println("Connected to MQTT server");
        client.subscribe(Deurbel);
        Serial.println("Subscribed to: " + Deurbel);
        client.subscribe(TemperatuurSensor);
        Serial.println("Subscribed to: " + TemperatuurSensor);
        client.subscribe(verwarming);
        Serial.println("Subscribed to: " + verwarming);
      } else {
        Serial.println("Could not connect to MQTT server");   
      }
    }
    
    if (client.connected()){
    currentButton = debounce(lastButton);
    
    if(currentMillis - previousMillis > interval) {
      previousMillis = currentMillis;
      SendTemp(temperature);
    }
    
    if (lastButton == LOW && currentButton == HIGH){
      Serial.println("Button pressed");
      Send("bel");
    }
    lastButton = currentButton;
    client.loop();  
    }
  }
}
  void Send(String data){
     Serial.println("published data");
     client.publish(Deurbel, String(data) );
  }
  
  void SendTemp(int data){
   Serial.println("Sending data");
   client.publish(TemperatuurSensor, String(data) );
  }
  void Receive(){       
      Serial.println("Receiving");
      client.set_callback(callback);       
  }
 
  void callback(const MQTT::Publish& pub) {

           
}
