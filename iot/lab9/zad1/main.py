#!/usr/bin/env python3
import RPi.GPIO as GPIO
import time
from zad1.config import led1, encoderLeft, encoderRight

GPIO.setmode(GPIO.BCM)
brightness = 0
GPIO.setup(led1, GPIO.OUT)
pwm = GPIO.PWM(led1, 1000)
pwm.start(brightness)


def encoder_callback(channel):
    global brightness
    if GPIO.input(encoderLeft) == GPIO.input(encoderRight):
        brightness += 5
    else:
        brightness -= 5
    brightness = max(0, min(100, brightness))
    pwm.ChangeDutyCycle(brightness)
    print(f"Jasność LED: {brightness}%")


GPIO.add_event_detect(encoderLeft, GPIO.RISING, callback=encoder_callback)
GPIO.add_event_detect(encoderRight, GPIO.RISING, callback=encoder_callback)

try:
    while True:
        time.sleep(0.1)
except KeyboardInterrupt:
    print("Program zakończony przez użytkownika")
finally:
    pwm.stop()
    GPIO.cleanup()
