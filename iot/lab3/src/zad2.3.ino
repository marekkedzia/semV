// #include <Arduino.h>

// #define LED_RED 6
// #define LED_GREEN 5
// #define LED_BLUE 3

// #define RED_BUTTON 2
// #define GREEN_BUTTON 4

// #define ENCODER1 A2
// #define ENCODER2 A3

// #define POTENTIOMETER A0

// int brightness = 0;
// int maxBrightness = 200;
// int minBrightness = 0;

// void setupRGB()
// {
//     pinMode(LED_RED, OUTPUT);
//     digitalWrite(LED_RED, LOW);

//     pinMode(LED_GREEN, OUTPUT);
//     digitalWrite(LED_GREEN, LOW);

//     pinMode(LED_BLUE, OUTPUT);
//     digitalWrite(LED_BLUE, LOW);
// }

// void setupButtons()
// {
//     pinMode(RED_BUTTON, INPUT_PULLUP);
//     pinMode(GREEN_BUTTON, INPUT_PULLUP);
// }

// void setup()
// {
//     setupRGB();
//     setupButtons();
// }

// void changeBrightness()
// {
//     if (digitalRead(RED_BUTTON) == LOW && brightness < maxBrightness)
//         brightness++;
//     if (digitalRead(GREEN_BUTTON) == LOW && brightness > minBrightness)
//         brightness--;

//     delay(10);
// }

// int calculateBrightnessAnalogValue(int colorBrightness)
// {
//     return map(colorBrightness, 0, 100, 0, 255);
// }

// void loop()
// {
//     changeBrightness();

//     int halfBrightness = maxBrightness / 2;
//     if (brightness < halfBrightness)
//     {
//         analogWrite(LED_BLUE, calculateBrightnessAnalogValue(brightness));
//         analogWrite(LED_GREEN, calculateBrightnessAnalogValue(halfBrightness - brightness));
//     }
//     else
//     {
//         analogWrite(LED_GREEN, calculateBrightnessAnalogValue(minBrightness));
//         analogWrite(LED_BLUE, calculateBrightnessAnalogValue(maxBrightness - brightness));
//         analogWrite(LED_RED, calculateBrightnessAnalogValue(brightness - halfBrightness));
//     }
// }