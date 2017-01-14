#include <ESP8266WiFi.h>
#include <PubSubClient.h>


const char *ssid =  "Connectify-BOYD";   // cannot be longer than 32 characters!
const char *pass =  "aphogeschool";

const char *mqtt_server = "m21.cloudmqtt.com";
const int mqtt_port = 12452;
const char *mqtt_user = "xnkcayag";
const char *mqtt_pass = "DtCGtuL2kVfk";
const char *mqtt_client_name = "keuken"; // Client connections cant have the same connection name

String thisDevice = "kitchen"; // Subscribe to this topic and publish with this as context

WiFiClient wclient;
PubSubClient client(wclient, mqtt_server, mqtt_port);

#define BUFFER_SIZE 100

void setup() {

  pinMode(D1, OUTPUT);
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
 //     Serial.println("Connecting to MQTT server");
      if (client.connect(MQTT::Connect("arduino")
                         .set_auth(mqtt_user, mqtt_pass))) {
    //    Serial.println("Connected to MQTT server");
        client.subscribe(thisDevice);
      } else {
        Serial.println("Could not connect to MQTT server");   
      }
    }

    if (client.connected())
      client.loop();
  }


  Recieve();
}

void Send(String data){

    Serial.print("published data");
   client.publish("Android",thisDevice + " " + data );

  }

  void Recieve(){

                      
      //  Serial.println("Recieving");
        client.set_callback(callback);
        
    
      }
    
      
  
  void callback(const MQTT::Publish& pub) {
    String message = (pub.payload_string());
    message.trim();
    Send(message);
    Serial.println(message);

    if (message == "on") {
      digitalWrite(D1, HIGH);
      
    }
    
    else if (message == "off") {
      digitalWrite(D1, LOW);
      
    }

//Change the name of the device
    else if(message.indexOf("cN") >= 0) {
      client.unsubscribe(thisDevice);
      thisDevice = message;
      thisDevice.remove(0, 2);
      Serial.print("changed name");
      client.subscribe(thisDevice);
    }
    
}


