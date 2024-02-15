#!/usr/bin/env python3
import RPi.GPIO as GPIO
import time
from zad1.config import led1, encoderLeft, encoderRight

# Inicjalizacja GPIO i konfiguracja pinów
GPIO.setmode(GPIO.BCM)  # Ustawienie trybu numeracji pinów na BCM
brightness = 0  # Początkowa jasność diody LED ustawiona na 0%
GPIO.setup(led1, GPIO.OUT)
pwm = GPIO.PWM(led1, 1000)  # Inicjalizacja PWM na pinie LED 1 z częstotliwością 1000Hz, PWM pozwala na kontrolę jasności diody LED poprzez szybkie włączanie i wyłączanie z regulowanym stosunkiem czasu włączenia do całkowitego cyklu.
pwm.start(brightness)  # Rozpoczęcie PWM z jasnością 0%


# Funkcja wywoływana przy aktywacji przerwania przez enkoder
def encoder_callback(channel): # Ta funkcja jest wywoływana, gdy następuje przerwanie spowodowane przez enkodery.
    global brightness  # Użycie zmiennej globalnej brightness
    # Sprawdzenie, czy oba piny enkodera mają tę samą wartość
    if GPIO.input(encoderLeft) == GPIO.input(encoderRight):
        brightness += 5  # Zwiększenie jasności o 5%
    else:
        brightness -= 5  # Zmniejszenie jasności o 5%

    # Ograniczenie wartości jasności do zakresu 0-100%
    brightness = max(0, min(100, brightness))

    # Zmiana wypełnienia impulsu PWM, co wpływa na jasność diody
    pwm.ChangeDutyCycle(brightness)


# Konfiguracja przerwań dla pinów enkodera
GPIO.add_event_detect(encoderLeft, GPIO.RISING, callback=encoder_callback)
GPIO.add_event_detect(encoderRight, GPIO.RISING, callback=encoder_callback)

# Pętla główna programu
try:
    while True:
        time.sleep(0.1)  # Opóźnienie dla stabilności programu
except KeyboardInterrupt:
    print("Program zakończony przez użytkownika")
finally:
    pwm.stop()  # Zatrzymanie PWM
    GPIO.cleanup()  # Wyczyszczenie konfiguracji GPIO
