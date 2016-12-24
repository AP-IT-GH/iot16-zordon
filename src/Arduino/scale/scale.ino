#include "HX711.h"

HX711 scale;
float weight;

void setup() {
  Serial.begin(9600);
  scale.begin(D2, D3);

  scale.set_scale(1535000/106);                      // this value is obtained by calibrating the scale with known weights; see the README for details
  scale.tare();
}

void loop() {
  while (weight < 40) {
    if (weight < -40) {
      scale.tare();
    }
      Serial.print("I'm ready for the next one: ");
      Serial.println(weight);
        weight=-scale.get_units(1);
     delay(5000);
  }
 scale.tare();
float releaseWeight = scale.get_units(1);
  while (releaseWeight < 1) {
      Serial.println("get off");
    releaseWeight=scale.get_units(1);
 Serial.println(releaseWeight);
 delay(1000);

  }
        weight=scale.get_units(15);
        Serial.print("Your current weight is:  ");
        Serial.println(weight);
        scale.tare();
         weight=scale.get_units(1);

  }
