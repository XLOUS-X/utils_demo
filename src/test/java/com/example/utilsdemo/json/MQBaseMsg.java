package com.example.utilsdemo.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MQBaseMsg implements Serializable {

    private double timestamp;
    private int work_state;
    private int vehicle_state;
    private double longitude;
    private double latitude;
    private int altitude;
    private double enu_x;
    private double enu_y;
    private int enu_z;
    private double yaw;
    private int roll;
    private int pitch;
    private double[] acceleration;
    private int speed;
    private int location_valid;
    private int drive_mode;
    private int autodrive_state;
    private String goal;
    private String CTE;

}