package com.example.utilsdemo.kedacom;

import com.alibaba.fastjson.JSON;
import com.kedacom.platform2mc.ntv.IPhoenixSDKConstantVal;
import com.kedacom.platform2mc.ntv.IPhoenixSDK_Windows;
import com.kedacom.platform2mc.struct.*;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDeviceInfoThread extends Thread
{
    IPhoenixSDK_Windows sdk;
    ArrayList<DeviceInfo> devlist;
    int[] errorCode = new int[1];


    BufferedOutputStream Buff = null;

    GetDeviceInfoThread(String name, IPhoenixSDK_Windows sdktmp)
    {
        super(name);//调用父类带参数的构造方法
        sdk = sdktmp;

    }

    public void SetDeviceList(ArrayList<DeviceInfo> devicelist)
    {
        devlist = devicelist;
    }

    public void doSubscriptNotify(int useless[], DeviceID mDeviceID, DeviceStatus mDeviceStatus)
    {
        System.out.println("------");






        System.out.println(mDeviceID.szID + "是否在线：" + mDeviceStatus.bOnline);


    }


}