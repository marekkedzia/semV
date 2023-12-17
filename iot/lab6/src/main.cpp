#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <OneWire.h>
#include <DallasTemperature.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);
OneWire oneWire(A1);
DallasTemperature tempSensors(&oneWire);

#define LED_RED 6
#define LED_GREEN 5

#define TEMP_MIN -100
#define TEMP_MAX 100

int highestTemperatures[2] = {TEMP_MIN, TEMP_MIN};
int lowestTemperatures[2] = {TEMP_MAX, TEMP_MAX};

void initRGB() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
}

void getTemperatures(int *temperatures) {
  tempSensors.requestTemperatures();
  temperatures[0] = tempSensors.getTempCByIndex(0);
  temperatures[1] = tempSensors.getTempCByIndex(1);
}

void updateTemperatureRecords(int temperatures[]) {
  for (int i = 0; i < 2; i++) {
    if (temperatures[i] > highestTemperatures[i]) {
      highestTemperatures[i] = temperatures[i];
    }
    if (temperatures[i] < lowestTemperatures[i]) {
      lowestTemperatures[i] = temperatures[i];
    }
  }
}

void printTemperature(int temperatures[]) {
  lcd.setCursor(0, 0);
  lcd.print("Temp 1: ");
  lcd.print(temperatures[0]);
  lcd.print(" C");
  lcd.setCursor(0, 1);
  lcd.print("Temp 2: ");
  lcd.print(temperatures[1]);
  lcd.print(" C");
}

void lightRGB(int temperature) {
  if(temperature > 20 && temperature < 23) {
    digitalWrite(LED_GREEN, HIGH);
    digitalWrite(LED_RED, LOW);
  } else {
    digitalWrite(LED_GREEN, LOW);
    digitalWrite(LED_RED, HIGH);
  }
}

void setup() {
  lcd.init();
  lcd.backlight();
  lcd.clear();
  initRGB();
  tempSensors.begin();
}

void loop() {
  int temperatures[2];
  getTemperatures(temperatures);
  updateTemperatureRecords(temperatures);
  printTemperature(temperatures);
  lightRGB(temperatures[0]);
  delay(1000);
}
