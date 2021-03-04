package com.example.utilsdemo.kedacom;

import com.kedacom.platform2mc.ntv.IPhoenixSDKCallback;
import com.kedacom.platform2mc.struct.DeviceID;
import com.kedacom.platform2mc.struct.DeviceStatus;

public class IPhoenixSDKCallbackEx extends IPhoenixSDKCallback {
    GetDeviceInfoThread getmsgthread;

    public void SetGetDeviceInfoThread(GetDeviceInfoThread getmsgthreadtmp)
    {
        getmsgthread = getmsgthreadtmp;
    }

    public void myCallbackFunc(int useless[], DeviceID mDeviceID, DeviceStatus mDeviceStatus)
    {
        getmsgthread.doSubscriptNotify(useless, mDeviceID, mDeviceStatus);
    }
}



