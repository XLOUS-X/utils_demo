package com.example.utilsdemo.mqtt.demo1;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MyMqtt {
    private String host = "tcp://52.82.15.69:8133";
    private String userName = "73";
    private String passWord = "81232e95cd0577540af8fc3461522f3b2fa7a427f22c35d5ee818ccb20c9d2b5";
    private MqttClient client;
    private String id;
    private static MyMqtt instance; // = new MyMqtt();
    private MqttTopic mqttTopic;
    private String myTopic = "bridge/messages/73/YZT3905A/device|yuoLK5vqeUj|DF8712D6C9";
    private MqttMessage message;
    public MyMqtt(String id) {
        this(id, null, false);
    }

    public MyMqtt(String id, MqttCallback callback, boolean cleanSession){
        try {
            //id应该保持唯一性
            client = new MqttClient(host, id, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(cleanSession);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            if (callback == null) {
                client.setCallback(new MqttCallback() {

                    @Override
                    public void connectionLost(Throwable arg0) {
                        // TODO 自动生成的方法存根
                        System.out.println(id + " connectionLost " + arg0);
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken arg0) {
                        // TODO 自动生成的方法存根
                        System.out.println(id + " deliveryComplete " + arg0);
                    }

                    @Override
                    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
                        // TODO 自动生成的方法存根
                        System.out.println(id + " messageArrived: " + arg1.toString());
                    }
                });
            } else {
                client.setCallback(callback);
            }
            client.connect(options);
        } catch (MqttException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        sendMessage(myTopic, msg);
    }

    public void sendMessage(String topic, String msg){
        try {
            message = new MqttMessage();
            message.setQos(1);
            message.setRetained(true);
            message.setPayload(msg.getBytes());
            mqttTopic = client.getTopic(topic);
            MqttDeliveryToken token = mqttTopic.publish(message);//发布主题
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (MqttException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void subscribe(String[] topicFilters, int[] qos) {
        try {
            client.subscribe(topicFilters, qos);
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 订阅主题

    }

}