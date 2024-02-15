#ifndef RGBLed_h
#define RGBLed_h

#include "Arduino.h"

class RGBLed {
  public:
    RGBLed(int pinRed, int pinGreen, int pinBlue);
    void setColor(int red, int green, int blue);
    void setColorByName(String colorName);

  private:
    int _pinRed, _pinGreen, _pinBlue;
    void _applyColor(int red, int green, int blue);
};

#endif
