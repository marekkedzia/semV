#include <Arduino.h>

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

#define ENCODER1 A2
#define ENCODER2 A3

#define POTENTIOMETER A0

int flashlightMode = 0;

void setupRGB()
{
  pinMode(LED_RED, OUTPUT);
  digitalWrite(LED_RED, LOW);

  pinMode(LED_GREEN, OUTPUT);
  digitalWrite(LED_GREEN, LOW);

  pinMode(LED_BLUE, OUTPUT);
  digitalWrite(LED_BLUE, LOW);
}

void setupButtons()
{
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

void setup()
{
  setupRGB();
  setupButtons();
}

void changeFlashLightMode()
{
  if (flashlightMode == 3)
  {
    flashlightMode = 0;
  }
  else
  {
    flashlightMode++;
  }
}

void execMode0()
{
  digitalWrite(LED_RED, LOW);
  digitalWrite(LED_BLUE, LOW);
}

void execMode1()
{
  digitalWrite(LED_BLUE, LOW);
  digitalWrite(LED_RED, HIGH);
}

void execMode2()
{
  digitalWrite(LED_RED, LOW);
  digitalWrite(LED_BLUE, HIGH);
}

void execMode3()
{
  delay(100);
  digitalWrite(LED_BLUE, LOW);
  digitalWrite(LED_RED, HIGH);
  delay(100);
  digitalWrite(LED_RED, LOW);
  digitalWrite(LED_BLUE, HIGH);
}

void loop()
{
  if (digitalRead(RED_BUTTON) == LOW)
  {
    changeFlashLightMode();
    delay(200);
  }

  if (digitalRead(GREEN_BUTTON) == LOW)
  {
    flashlightMode = 0;
    delay(200);
  }

  if (flashlightMode == 0)
    execMode0();
  else if (flashlightMode == 1)
    execMode1();
  else if (flashlightMode == 2)
    execMode2();
  else if (flashlightMode == 3)
    execMode3();
}