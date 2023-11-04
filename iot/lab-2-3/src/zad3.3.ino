// #include <Arduino.h>
// #include <LiquidCrystal_I2C.h>

// LiquidCrystal_I2C lcd(0x27, 16, 2);

// #define LED_RED 6
// #define LED_GREEN 5
// #define LED_BLUE 3
// #define RED_BUTTON 2
// #define GREEN_BUTTON 4

// #define DEBOUNCE_DELAY 150

// unsigned long timeStart;
// bool isTimerRunning = false;
// bool isTimerStopped = false;
// unsigned long timePaused;
// String startMessage = "Start timer!";
// unsigned long lastButtonPress = 0;

// void setupRGB()
// {
//     pinMode(LED_RED, OUTPUT);
//     pinMode(LED_GREEN, OUTPUT);
//     pinMode(LED_BLUE, OUTPUT);
//     digitalWrite(LED_RED, LOW);
//     digitalWrite(LED_GREEN, LOW);
//     digitalWrite(LED_BLUE, LOW);
// }

// void setupButtons()
// {
//     pinMode(RED_BUTTON, INPUT_PULLUP);
//     pinMode(GREEN_BUTTON, INPUT_PULLUP);
// }

// void displayDigit(int elapsedTime, int cursorPosition)
// {
//     lcd.setCursor(cursorPosition, 0);
//     int digit = elapsedTime % 10;
//     lcd.print(digit);
//     if (elapsedTime >= 10)
//     {
//         displayDigit(elapsedTime / 10, cursorPosition - 1);
//     }
// }

// void displayCurrentTime()
// {
//     int elapsedTime = (millis() - timeStart) / 1000;
//     displayDigit(elapsedTime, 14);
// }

// void setup()
// {
//     setupRGB();
//     setupButtons();
//     lcd.init();
//     lcd.backlight();
//     lcd.setCursor(0, 0);
//     lcd.print(startMessage);
// }

// void displayDigit(int elapsedTime, int divider, int cursorPosition)
// {
//     lcd.setCursor(cursorPosition, 0);
//     if (elapsedTime / divider != 1)
//     {
//         String elapsedTimeString = String(elapsedTime);
//         lcd.print(elapsedTimeString[elapsedTimeString.length() - (15 - cursorPosition)]);
//     }
//     else
//     {
//         displayDigit(elapsedTime, divider * 10, cursorPosition - 1);
//     }
// }

// void loop()
// {
//     if (isTimerRunning)
//         displayCurrentTime();
//     if (digitalRead(RED_BUTTON) == LOW && isTimerRunning)
//     {
//         isTimerRunning = false;
//         isTimerStopped = true;
//         timePaused = millis();
//         delay(1000);
//     }
//     if (digitalRead(RED_BUTTON) == LOW && !isTimerRunning)
//     {
//         timeStart = 0;
//         isTimerStopped = false;
//         lcd.clear();
//         lcd.print(startMessage);
//     }
//     if (digitalRead(GREEN_BUTTON) == LOW && !isTimerRunning)
//     {
//         lcd.clear();

//         if (!isTimerStopped)
//         {
//             timeStart = millis();
//         }

//         isTimerRunning = true;
//         isTimerStopped = false;
//         lcd.print("Time:");
//     }
// }