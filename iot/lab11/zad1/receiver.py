import paho.mqtt.client as mqtt
import time


class RFIDSubscriber:
    def __init__(self, mqtt_broker, mqtt_port, mqtt_topic):
        self.client = mqtt.Client()
        self.mqtt_topic = mqtt_topic
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.client.connect(mqtt_broker, mqtt_port, 60)
        self.client.loop_start()

    def on_connect(self, client, userdata, flags, rc):
        print("Connected with result code " + str(rc))
        client.subscribe(self.mqtt_topic)

    def on_message(self, client, userdata, msg):
        print(f"Received message: {msg.payload.decode()}")

if __name__ == "__main__":
    subscriber = RFIDSubscriber("channel/card", 1883, "rfid/readings")
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        print("Exiting")
