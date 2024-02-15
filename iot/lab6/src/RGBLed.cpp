#include "Arduino.h"
#include "RGBLed.h"

RGBLed::RGBLed(int pinRed, int pinGreen, int pinBlue) {
  _pinRed = pinRed;
  _pinGreen = pinGreen;
  _pinBlue = pinBlue;
  pinMode(_pinRed, OUTPUT);
  pinMode(_pinGreen, OUTPUT);
  pinMode(_pinBlue, OUTPUT);
}

void RGBLed::setColor(int red, int green, int blue) {
  _applyColor(red, green, blue);
}

void RGBLed::setColorByName(String colorName) {
  if (colorName == "RED") {
    _applyColor(255, 0, 0);
  } else if (colorName == "GREEN") {
    _applyColor(0, 255, 0);
  } else if (colorName == "BLUE") {
    _applyColor(0, 0, 255);
  } else if (colorName == "YELLOW") {
    _applyColor(255, 255, 0);
  } else if (colorName == "CYAN") {
    _applyColor(0, 255, 255);
  } else if (colorName == "MAGENTA") {
    _applyColor(255, 0, 255);
  } else if (colorName == "BLACK") {
    _applyColor(0, 0, 0);
  } else if (colorName == "WHITE") {
    _applyColor(255, 255, 255);
  }
}


void RGBLed::_applyColor(int red, int green, int blue) {
  analogWrite(_pinRed, red);
  analogWrite(_pinGreen, green);
  analogWrite(_pinBlue, blue);
}
