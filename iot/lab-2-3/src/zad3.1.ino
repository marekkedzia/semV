#include <Arduino.h>

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

#define DEBOUNCE_DELAY 50

bool lastRedButtonState = LOW;
bool lastGreenButtonState = LOW;
unsigned long lastDebounceTime = 0;

enum LedColor
{
    RED,
    GREEN,
    BLUE
};
LedColor currentLED = RED;

void setup()
{
    pinMode(LED_RED, OUTPUT);
    pinMode(LED_GREEN, OUTPUT);
    pinMode(LED_BLUE, OUTPUT);

    pinMode(RED_BUTTON, INPUT_PULLUP);
    pinMode(GREEN_BUTTON, INPUT_PULLUP);

    digitalWrite(LED_RED, HIGH);
}

bool isGreenButtonPressed()
{
    static int debouncedButtonState = HIGH;
    static int previousReading = HIGH;
    static unsigned long lastChangeTime = 0UL;
    bool isPressed = false;

    int currentReading = digitalRead(GREEN_BUTTON);

    if (previousReading != currentReading)
    {
        lastChangeTime = millis();
    }

    if (millis() - lastChangeTime > DEBOUNCE_DELAY)
    {
        if (currentReading != debouncedButtonState)
        {
            if (debouncedButtonState == HIGH && currentReading == LOW)
            {
                isPressed = true;
            }
            debouncedButtonState = currentReading;
        }
    }

    previousReading = currentReading;

    return isPressed;
}

void loop()
{
    if (isGreenButtonPressed())
    {
        switchLED();
    }
}

void switchLED()
{
    digitalWrite(LED_RED, LOW);
    digitalWrite(LED_GREEN, LOW);
    digitalWrite(LED_BLUE, LOW);

    switch (currentLED)
    {
    case RED:
        digitalWrite(LED_GREEN, HIGH);
        currentLED = GREEN;
        break;
    case GREEN:
        digitalWrite(LED_BLUE, HIGH);
        currentLED = BLUE;
        break;
    case BLUE:
        digitalWrite(LED_RED, HIGH);
        currentLED = RED;
        break;
    }
}
