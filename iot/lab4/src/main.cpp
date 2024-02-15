#include <Arduino.h>
#define POTENTIOMETER_PIN A0

void setup()
{
    Serial.begin(9600);
    while (!Serial)
    {
    }
}

void loop()
{
    int sensorValue = analogRead(POTENTIOMETER_PIN);

    Serial.println(sensorValue);

    delay(10);
}
