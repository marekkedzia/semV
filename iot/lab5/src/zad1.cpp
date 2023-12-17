// #include <LiquidCrystal_I2C.h>

// #define LED_RED 6
// #define LED_GREEN 5
// #define LED_BLUE 3

// #define ENCODER1 A2
// #define ENCODER2 A3

// #define DEBOUNCING_PERIOD 100

// LiquidCrystal_I2C lcd(0x27, 16, 2);

// void printResults(int val) {
//   char buffer[40];
//   sprintf(buffer, "Encoder: %3d", val);
//   lcd.setCursor(2, 0);
//   lcd.print(buffer);
// }

// void updateRGBColor(int val) {
//   printResults(val);

//   int redValue, greenValue, blueValue;

//   if (val <= 85) { 
//     redValue = 255 - (val * 3);
//     greenValue = val * 3;
//     blueValue = 0;
//   } else if (val <= 170) { 
//     redValue = 0;
//     greenValue = 255 - ((val - 85) * 3);
//     blueValue = (val - 85) * 3;
//   } else { 
//     redValue = (val - 170) * 3;
//     greenValue = 0;
//     blueValue = 255 - ((val - 170) * 3);
//   }

//   analogWrite(LED_RED, redValue);
//   analogWrite(LED_GREEN, greenValue);
//   analogWrite(LED_BLUE, blueValue);
// }

// void setup() {
//   pinMode(LED_RED, OUTPUT);
//   pinMode(LED_GREEN, OUTPUT);
//   pinMode(LED_BLUE, OUTPUT);
//   pinMode(ENCODER1, INPUT_PULLUP);
//   pinMode(ENCODER2, INPUT_PULLUP);
//   lcd.init();
//   lcd.backlight();
//   updateRGBColor(0);
// }

// int encoderValue = 0;
// int lastEn1 = LOW;
// unsigned long lastChangeTimestamp = 0UL;

// void loop() {
//   int en1 = digitalRead(ENCODER1);
//   int en2 = digitalRead(ENCODER2);

//   unsigned long timestamp = millis();
//   if (en1 == LOW && lastEn1 == HIGH && timestamp > lastChangeTimestamp + DEBOUNCING_PERIOD) {
//     if (en2 == HIGH) {
//       if (encoderValue < 255)
//         encoderValue += 15;
//     } else {
//       if (encoderValue > 0)
//         encoderValue -= 15;
//     }
//     lastChangeTimestamp = timestamp;
//     updateRGBColor(encoderValue);
//   }
//   lastEn1 = en1;
// }
