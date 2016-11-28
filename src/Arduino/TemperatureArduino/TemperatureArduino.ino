#include <Thermistor.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>


const char *ssid =  "Connectify-BOYD";   // cannot be longer than 32 characters!
const char *pass =  "aphogeschool";

const char *mqtt_server = "m21.cloudmqtt.com";
const int mqtt_port = 12452;
const char *mqtt_user = "xnkcayag";
const char *mqtt_pass = "DtCGtuL2kVfk";
const char *mqtt_client_name = "Weemo"; // Client connections cant have the same connection name

String thisDevice = "Android/kamertemperatuur"; // Subscribe to this topic and publish with this as context
String keuken = "Android/kitchen";

WiFiClient wclient;
PubSubClient client(wclient, mqtt_server, mqtt_port);

#define BUFFER_SIZE 100
Thermistor temp(0);


void setup() {
  pinMode(A0, OUTPUT);
  // Setup console
  Serial.begin(115200);
  delay(10);
  Serial.println();
  Serial.println();
}
void loop() {
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
      if (client.connect(MQTT::Connect("arduino")
                         .set_auth(mqtt_user, mqtt_pass))) {
        Serial.println("Connected to MQTT server");
        client.subscribe(thisDevice);
      } else {
        Serial.println("Could not connect to MQTT server");   
      }
    }

    if (client.connected()){
      int temperature = temp.getTemp(); 
      Receive();
      Send(temperature);
      delay(10000);
      client.loop();  

    }
  }

}
void Send(int data){

    Serial.println("published data");
   client.publish(thisDevice, String(data) );

  }


  void Receive(){

                      
      Serial.println("Receiving");
        client.set_callback(callback);
        
    
      }
    
      
  
  void callback(const MQTT::Publish& pub) {
    String tmp = (pub.payload_string());
    int temperature = temp.getTemp(); 

    if (tmp.toInt() >= 24 || temperature >=24) {
      client.publish(keuken, "on" );
      
    }
    
    else if (tmp.toInt() < 24 || temperature < 24) {
    client.publish(keuken, "off" );
      
    }   
}
  
