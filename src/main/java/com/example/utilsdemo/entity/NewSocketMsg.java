package com.example.utilsdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewSocketMsg {

    private String timestamp;
    private String ytip;
    private String yzw;

    private String username;
    private String password;
    private String rtspurl;

    private double p;
    private double t;
    private double z;
}
