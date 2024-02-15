import paho.mqtt.client as mqtt
import time
from mfrc522 import MFRC522
from datetime import datetime

class RFIDPublisher:
    def __init__(self, mqtt_broker, mqtt_port, mqtt_topic):
        self.reader = MFRC522()
        self.client = mqtt.Client()
        self.mqtt_topic = mqtt_topic
        self.client.connect(mqtt_broker, mqtt_port, 60)
        self.client.loop_start()

    def read_rfid(self):
        status, TagType = self.reader.MFRC522_Request(self.reader.PICC_REQIDL)
        if status == self.reader.MI_OK:
            status, uid = self.reader.MFRC522_Anticoll()
            if status == self.reader.MI_OK:
                card_id = sum(uid[i] << (i * 8) for i in range(len(uid)))
                return card_id
        return None

    def run(self):
        while True:
            card_id = self.read_rfid()
            if card_id:
                timestamp = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                payload = f"{card_id},{timestamp}"
                self.client.publish(self.mqtt_topic, payload)
            time.sleep(1)

if __name__ == "__main__":
    publisher = RFIDPublisher("channel/card", 1883, "rfid/readings")
    publisher.run()
