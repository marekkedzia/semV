#!/usr/bin/env python3

from config import *
import board
import busio
import adafruit_bme280.advanced as adafruit_bme280
import lib.oled.SSD1331 as SSD1331
from PIL import Image, ImageDraw, ImageFont


def configureBME280():
    i2c = busio.I2C(board.SCL, board.SDA)
    bme280Sensor = adafruit_bme280.Adafruit_BME280_I2C(i2c, 0x76)
    bme280Sensor.sea_level_pressure = 1013.25
    bme280Sensor.standby_period = adafruit_bme280.STANDBY_TC_500
    bme280Sensor.iir_filter = adafruit_bme280.IIR_FILTER_X16
    return bme280Sensor


def readBME280(bme):
    bme.overscan_pressure = adafruit_bme280.OVERSCAN_X16
    bme.overscan_humidity = adafruit_bme280.OVERSCAN_X1
    bme.overscan_temperature = adafruit_bme280.OVERSCAN_X2
    print({"temperature": bme.temperature, "humidity": bme.humidity, "pressure": bme.pressure})
    return {"temperature": bme.temperature, "humidity": bme.humidity, "pressure": bme.pressure}


def configureDisplay(readings):
    image = Image.new("RGB", (disp.width, disp.height), "WHITE")
    draw = ImageDraw.Draw(image)
    font = ImageFont.truetype('./lib/oled/Font.ttf', 13)


    temp_icon = Image.open('../thermometer.png')
    draw.bitmap((5, 5), temp_icon, fill="RED")
    draw.text((25, 5), f"{readings['temperature']:.1f} C", font=font, fill="BLACK")

    humidity_icon = Image.open('../humidity.png')
    draw.bitmap((5, 25), humidity_icon, fill="BLUE")
    draw.text((25, 25), f"{readings['humidity']:.1f} %", font=font, fill="BLACK")

    pressure_icon = Image.open('../pressure.png')
    draw.bitmap((5, 45), pressure_icon, fill="GREEN")
    draw.text((25, 45), f"{readings['pressure']:.1f} hPa", font=font, fill="BLACK")

    disp.ShowImage(image, 0, 0)


if __name__ == "__main__":
    bme280Sensor = configureBME280()
    disp = SSD1331.SSD1331()
    disp.Init()
    disp.clear()

    while True:
        sensorReadings = readBME280(bme280Sensor)
        configureDisplay(sensorReadings)
