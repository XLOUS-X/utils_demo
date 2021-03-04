package com.example.utilsdemo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/")
@Slf4j
public class DoNetCoreHttpTestController {

    @RequestMapping(value = "/httpclient", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    public String getOverFlow(@RequestBody JSONObject json) {
        log.info("C#httpRequest:{}" , JSON.toJSONString(json, new PascalNameFilter()));
        return "success";
    }
}
