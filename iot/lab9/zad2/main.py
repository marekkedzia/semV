#!/usr/bin/env python3
import time
import board
import neopixel
from w1thermsensor import W1ThermSensor
import adafruit_bme280

NUM_PIXELS = 4
PIXEL_PIN = board.D8
pixels = neopixel.NeoPixel(PIXEL_PIN, NUM_PIXELS)

sensor_ds18b20 = W1ThermSensor()

i2c = board.I2C()
sensor_bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c)


def read_sensors():
    temperature = sensor_ds18b20.get_temperature()
    humidity = sensor_bme280.humidity
    return temperature, humidity


def update_leds(temperature, humidity):
    if temperature < 20:
        color_temp = (0, 0, 255)  # Niebieski
    elif temperature > 30:
        color_temp = (255, 0, 0)  # Czerwony
    else:
        color_temp = (0, 255, 0)  # Zielony

    if humidity < 30:
        color_hum = (0, 125, 0)  # Zielony, mniej intensywny
    elif humidity > 70:
        color_hum = (0, 255, 0)  # Zielony, bardziej intensywny
    else:
        color_hum = (0, 175, 0)  # Średnio intensywny zielony

    pixels[0] = color_temp
    pixels[1] = color_temp
    pixels[2] = color_hum
    pixels[3] = color_hum


while True:
    temp, hum = read_sensors()
    update_leds(temp, hum)
    time.sleep(1)
