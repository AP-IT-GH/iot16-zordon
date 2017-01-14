#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include "HX711.h"

HX711 scale;
float weight;


const char *ssid =  "Connectify-BOYD";   // cannot be longer than 32 characters!
const char *pass =  "aphogeschool";

const char *mqtt_server = "m21.cloudmqtt.com";
const int mqtt_port = 12452;
const char *mqtt_user = "xnkcayag";
const char *mqtt_pass = "DtCGtuL2kVfk";
const char *mqtt_client_name = "scale"; // Client connections cant have the same connection name

String thisDevice = "scale"; // Subscribe to this topic and publish with this as context
bool manual;

WiFiClient wclient;
PubSubClient client(wclient, mqtt_server, mqtt_port);

#define BUFFER_SIZE 100

void setup() {
  scale.begin(D2, D3);

  scale.set_scale(-2081995.00/105);                      // this value is obtained by calibrating the scale with known weights; see the README for details
 

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
  if (!manual) {
weight = scale.get_units(1);
  if (weight > 40) {
    Send("heavy");
    float testWeight = scale.get_units(30);
    while (weight > testWeight -0.01 && weight < testWeight +0.01){
      
      Serial.print("Please stand still ");
      Serial.println(weight);
      Serial.print("  ");
      Serial.println( testWeight); 
      weight =  testWeight;
       float testWeight = scale.get_units(1);
    }
    weight = scale.get_units(10);
    char buffer[50];
      String sendWeight = dtostrf(weight,2,2,buffer);
    Send("dit weegt: " + sendWeight);
    int i = 60000;
    while (weight > 40) {
      weight = scale.get_units(1);
      i++;
      if (i > 60000) {
      Send("get off");
      i=0;
      }
      
    }
    Send("light");
  }

  }


  Recieve();
}

void Send(String data){

    Serial.print("published data");
   client.publish("Android/" + thisDevice, data );

  }

  void Recieve(){

                      
      //  Serial.println("Recieving");
        client.set_callback(callback);
        
    
      }
    
      
  
  void callback(const MQTT::Publish& pub) {
    String message = (pub.payload_string());
    message.trim();
  //  Send(message);
    Serial.println(message);

    if (message == "measure") {

      weight = scale.get_units(1);
      char buffer[50];
      String sendWeight = dtostrf(weight,2,2,buffer);

      Send(sendWeight);
      
    }
    
    else if (message == "calibrate") {
      delay(5000);
         Send("taring");
      scale.set_scale();
      delay(1000);
      scale.tare();  
      Send("get off and wait");

      delay(15000);

      weight = -scale.get_units(1);
     
      scale.set_scale(weight/105);  
       scale.tare();  
             char buffer[50];
      String sendWeight = dtostrf(weight,2,2,buffer);

      Send("recalibrated at " + sendWeight );
      
      
    }
       else if (message == "tare") {
        scale.tare();  
       }
        else if (message == "manual") {
        manual = true;
        Send("manual");
       }
        else if (message == "auto") {
        manual = false;
        Send("auto");
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


