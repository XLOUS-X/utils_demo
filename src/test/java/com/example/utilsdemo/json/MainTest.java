package com.example.utilsdemo.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainTest {


    /**
     * 初始化User对象
     * @return user
     */
    private static User initUser(){
        User user = new User();
        user.setId("1");
        user.setName("jison");
        user.setPassword("jison");
        return user;
    }

    private static NewSocketMsg initNewSocketMsg(){
        NewSocketMsg newSocketMsg = new NewSocketMsg();
        newSocketMsg.setTimestamp("2021-03-10 11:27:52.692");
        newSocketMsg.setYtip("172.10.20.17");
        newSocketMsg.setYzw("1");
        newSocketMsg.setUsername("admin");
        newSocketMsg.setPassword("admin123");
        newSocketMsg.setP(24000);
        newSocketMsg.setT(1100);
        newSocketMsg.setZ(0);
        return newSocketMsg;
    }

    public static void main(String[] args) throws Exception {
        // fastjson用法
        fastjson2();
        // jackson用法
//        jackson();
        // gson用法
//        gson();
    }

    private static void fastjson(){
        // 将Java对象序列化为Json字符串
        String objectToJson = JSON.toJSONString(initUser());
        System.out.println(objectToJson);
        // 将Json字符串反序列化为Java对象
        User user = JSON.parseObject(objectToJson, User.class);
        System.out.println(user);
    }

    private static void fastjson2(){
        // 将Java对象序列化为Json字符串
        String objectToJson = JSON.toJSONString(initNewSocketMsg());
//        String objectToJson = "{\"timestamp\": 1615266813.3583555, \"work_state\": 0, \"vehicle_state\": 2, \"longitude\": 119.40461805, \"latitude\": 32.47047248, \"altitude\": 24.0, \"enu_x\": 38.37, \"enu_y\": -103.59, \"enu_z\": 0.0, \"yaw\": -69.8, \"roll\": 0.0, \"pitch\": 0.0, \"acceleration\": [0.0, -0.0, 0.0], \"speed\": 0.0, \"location_valid\": 1, \"drive_mode\": 2, \"autodrive_state\": 0, \"goal\": \"0\", \"CTE\": \"0.000\"}";

        System.out.println(objectToJson);
        // 将Json字符串反序列化为Java对象
        NewSocketMsg newSocketMsg = JSON.parseObject(objectToJson, NewSocketMsg.class);
        System.out.println(newSocketMsg);
    }

    private static void jackson() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        // 将Java对象序列化为Json字符串
        String objectToJson = objectMapper.writeValueAsString(initUser());
        System.out.println(objectToJson);
        // 将Json字符串反序列化为Java对象
        User user = objectMapper.readValue(objectToJson, User.class);
        System.out.println(user);
    }

    private static void gson(){
        Gson gson = new GsonBuilder().create();
        // 将Java对象序列化为Json字符串
        String objectToJson = gson.toJson(initUser());
        System.out.println(objectToJson);
        // 将Json字符串反序列化为Java对象
        User user = gson.fromJson(objectToJson, User.class);
        System.out.println(user);
    }
}
