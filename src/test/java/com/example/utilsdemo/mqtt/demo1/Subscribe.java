package com.example.utilsdemo.mqtt.demo1;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class Subscribe {
    private MqttClient client;
    private MqttConnectOptions options;
    private static String[] myTopics = {"bridge/messages/73/YZT3905A/device|yuoLK5vqeUj|DF8712D6C9"};
    private static int[] myQos = {0};

    public static void main(String[] args) {
        System.out.println("client start...");
        MyMqtt myMqtt = new MyMqtt("client");
        myMqtt.subscribe(myTopics, myQos);
    }
}
