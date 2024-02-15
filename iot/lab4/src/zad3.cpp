// #include <LiquidCrystal_I2C.h>

// LiquidCrystal_I2C lcd(0x27, 16, 2);
// #define POTENTIOMETER A0

// void setup()
// {
//     lcd.begin(16, 2);

//     pinMode(POTENTIOMETER, INPUT);
// }

// void loop()
// {
//     int sensorValue = analogRead(POTENTIOMETER);
//     float voltage = sensorValue * (5.0 / 1023.0);

//     lcd.setCursor(0, 0);
//     lcd.print("ADC: ");
//     lcd.print(sensorValue);

//     lcd.setCursor(0, 1);
//     lcd.print("Volt: ");
//     lcd.print(voltage);

//     delay(100);
// }
