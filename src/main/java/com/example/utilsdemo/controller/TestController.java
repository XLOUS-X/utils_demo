package com.example.utilsdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.utilsdemo.entity.NewSocketMsg;
import com.example.utilsdemo.utils.HttpClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public void test() {
        NewSocketMsg json = HttpClientUtil.initNewSocketMsg();
        String[] sentGPGHttpClient2 = HttpClientUtil.sentGPGHttpClient2("/camera/test", "");
        for (int i = 0; i < sentGPGHttpClient2.length; i++) {
            System.out.println(sentGPGHttpClient2[i]);
        }
    }
}
