from datetime import datetime
import time
import RPi.GPIO as GPIO
from config import *
from mfrc522 import MFRC522


def find_current_time():
    return datetime.now()


def identify_rfid():
    RFIDReader = MFRC522()
    if datetime.timestamp(datetime.now()):
        (status, TagType) = RFIDReader.MFRC522_Request(RFIDReader.PICC_REQIDL)
        if status == RFIDReader.MI_OK:
            (status, card_uid) = RFIDReader.MFRC522_Anticoll()
            if status == RFIDReader.MI_OK:
                card_num = 0
                for i in range(0, len(card_uid)):
                    card_num += card_uid[i] << (i * 8)
                print(f"Card read UID: {card_num}")


def handle_rfid_event():
    print(find_current_time())
    print(identify_rfid())


def confirm_rfid_scan():
    def light_led():
        GPIO.output(ledIndicator, GPIO.HIGH)
        time.sleep(1)
        GPIO.output(ledIndicator, GPIO.LOW)

    def play_sound():
        GPIO.output(buzzerOutput, true)
        time.sleep(0.5)
        GPIO.output(buzzerOutput, false)

    light_led()
    play_sound()


def loop():
    while True:
        handle_rfid_event()
        time.sleep(3)


if __name__ == "__main__":
    loop()
