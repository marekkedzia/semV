#include <Arduino.h>

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

#define DEBOUNCE_DELAY 50

int RED_LED_DURATION = 5000;
int GREEN_LED_DURATION = 1000;
int BLUE_LED_DURATION = 10000;

enum LedColor
{
    RED,
    GREEN,
    BLUE
};
LedColor currentLED = RED;
int lastSwitchTime = 0;

void setup()
{
    pinMode(LED_RED, OUTPUT);
    pinMode(LED_GREEN, OUTPUT);
    pinMode(LED_BLUE, OUTPUT);

    pinMode(RED_BUTTON, INPUT_PULLUP);
    pinMode(GREEN_BUTTON, INPUT_PULLUP);

    digitalWrite(LED_RED, HIGH);
}

void loop()
{
    switchLED();
}

void switchLED()
{
    int currentTime = millis();
    switch (currentLED)
    {
    case RED:
        if (currentTime - lastSwitchTime > RED_LED_DURATION)
        {
            digitalWrite(LED_RED, LOW);
            lastSwitchTime = currentTime;
            digitalWrite(LED_GREEN, HIGH);
            currentLED = GREEN;
        }
        break;
    case GREEN:
        if (currentTime - lastSwitchTime > GREEN_LED_DURATION)
        {
            digitalWrite(LED_GREEN, LOW);
            lastSwitchTime = currentTime;
            digitalWrite(LED_BLUE, HIGH);
            currentLED = BLUE;
        }
        break;
    case BLUE:
        if (currentTime - lastSwitchTime > BLUE_LED_DURATION)
        {
            digitalWrite(LED_BLUE, LOW);
            lastSwitchTime = currentTime;
            digitalWrite(LED_RED, HIGH);
            currentLED = RED;
        }
        break;
    }
}
