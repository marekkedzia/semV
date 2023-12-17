#!/usr/bin/env python3

# Importowanie niezbędnych bibliotek
import time                 # Dla obsługi czasu (np. opóźnień)
import board                # Dla komunikacji z pinami GPIO Raspberry Pi
import neopixel             # Dla sterowania diodami WS2812
from w1thermsensor import W1ThermSensor  # Dla obsługi czujnika temperatury DS18B20
import adafruit_bme280      # Dla obsługi czujnika BME280

# Konfiguracja diod WS2812
NUM_PIXELS = 4              # Ilość diod WS2812 do sterowania
PIXEL_PIN = board.D8        # Pin GPIO, do którego podłączona jest linijka diod WS2812
pixels = neopixel.NeoPixel(PIXEL_PIN, NUM_PIXELS)  # Inicjalizacja obiektu sterującego diodami

# Konfiguracja czujnika DS18B20
sensor_ds18b20 = W1ThermSensor()  # Inicjalizacja czujnika DS18B20

# Konfiguracja czujnika BME280
i2c = board.I2C()  # Utworzenie instancji komunikacji I2C dla Raspberry Pi
sensor_bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c)  # Inicjalizacja czujnika BME280

# Funkcja odczytująca wartości z czujników
def read_sensors():
    temperature = sensor_ds18b20.get_temperature()  # Odczyt temperatury z DS18B20
    humidity = sensor_bme280.humidity               # Odczyt wilgotności z BME280
    return temperature, humidity                    # Zwracanie odczytanych wartości

# Funkcja ustawiająca kolory diod na podstawie odczytanych wartości
def update_leds(temperature, humidity):
    # Ustawienie kolorów dla temperatury
    if temperature < 20:
        color_temp = (0, 0, 255)  # Niebieski dla temperatur poniżej 20 stopni
    elif temperature > 30:
        color_temp = (255, 0, 0)  # Czerwony dla temperatur powyżej 30 stopni
    else:
        color_temp = (0, 255, 0)  # Zielony dla temperatur pomiędzy 20 a 30 stopni

    # Ustawienie kolorów dla wilgotności
    if humidity < 30:
        color_hum = (0, 125, 0)  # Mniej intensywny zielony dla wilgotności poniżej 30%
    elif humidity > 70:
        color_hum = (0, 255, 0)  # Bardziej intensywny zielony dla wilgotności powyżej 70%
    else:
        color_hum = (0, 175, 0)  # Średnio intensywny zielony dla wilgotności między 30% a 70%

    # Ustawienie kolorów diod
    pixels[0] = color_temp    # Ustawienie koloru pierwszej i drugiej diody na podstawie temperatury
    pixels[1] = color_temp
    pixels[2] = color_hum     # Ustawienie koloru trzeciej i czwartej diody na podstawie wilgotności
    pixels[3] = color_hum

# Główna pętla programu
while True:
    temp, hum = read_sensors()  # Odczyt danych z czujników
    update_leds(temp, hum)      # Aktualizacja kolorów diod
    time.sleep(1)               # Krótka przerwa (1 sekunda) przed kolejnym odczytem
