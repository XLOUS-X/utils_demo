package com.example.utilsdemo.kedacom;

import com.kedacom.platform2mc.ntv.IPhoenixSDKConstantVal;
import com.kedacom.platform2mc.ntv.IPhoenixSDK_Windows;
import com.kedacom.platform2mc.struct.*;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class StatueDemo {

    public static int PlatBusMode = 2;

    public static String Name = "admin";
    public static String PassWord = "tuli@2020";
    public static String Ip = "192.168.2.236:80";


    static IPhoenixSDK_Windows sdk = new IPhoenixSDK_Windows();
    static IPhoenixSDKCallbackEx callbackex = new IPhoenixSDKCallbackEx();
    static GetDeviceInfoThread getmsgthread = new GetDeviceInfoThread("getdevicethread", sdk);
    static int[] errorCode = new int[1];

    public static void main(String[] args) {
        System.out.println("testMain begin");
        System.out.println(System.getProperty("java.library.path"));
        callbackex.SetGetDeviceInfoThread(getmsgthread);
        getmsgthread.start();
        String IpNoPort = Ip;
        if (-1 != Ip.indexOf(':')) {
            IpNoPort = Ip.substring(0, Ip.indexOf(':'));
        }
        PlatBusMode = sdk.PlatTypeDetect(IpNoPort, errorCode);
        boolean MouSelect = sdk.ModualSelect(PlatBusMode, 3, 1);
        boolean result = sdk.Init();
        if (result == false) {
            System.out.println("sdk.Init() failed");
            return;
        }
        System.out.println("Init() success");
        sdk.SetSaveLogFileV2(IPhoenixSDKConstantVal.SDK_SCREEN_PRINT_LOG_LEVER_ALL, "D:\\mcusdk_winjava_log", "demo", 0, 0, errorCode);
        sdk.SetScreenShowLog(IPhoenixSDKConstantVal.SDK_SCREEN_PRINT_LOG_LEVER_ALL);
        sdk.SetMainCtx();
        sdk.SetCallback(callbackex);
        System.out.println("Login!!!*******************");
        result = sdk.LogIn(Name, PassWord, Ip, "WINDOWS", errorCode);
        if (result == false) {
            System.out.println("sdk.LogIn(" + Name + "," + PassWord + "," + Ip + ") failed, error = " + errorCode[0]);
            return;
        }
        System.out.println("Login success!!!*******************");

        BlockingQueue<GroupID> queueGrpId = new ArrayBlockingQueue<GroupID>(1000);
        ArrayList<DeviceInfo> queueDeviceId = new ArrayList<DeviceInfo>();

        GroupID groupId = new GroupID();
        groupId.szID = "";
        queueGrpId.add(groupId);

        while (!queueGrpId.isEmpty())
        {
            GroupID tmpGrpId = null;
            try {
                tmpGrpId = queueGrpId.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int taskGrpId = sdk.GetGroupByGroup(tmpGrpId, errorCode);
            if (taskGrpId <= 0)
            {
                System.out.println("sdk.GetGroupByGroup("+tmpGrpId+") failed, error = "+errorCode[0]);
                continue;
            }


            result = true;
            while(result)
            {
                DeviceGroupInfo deviceGroupInfo = new DeviceGroupInfo();
                result = sdk.GetGroupNext(taskGrpId, deviceGroupInfo, errorCode);
                if (result == false)
                {
                    if (errorCode[0] != 0)
                    {
                        System.out.println("sdk.GetGroupNext("+taskGrpId+") failed, error = "+errorCode[0]);
                    }
                    continue;
                }
                GroupID tmpSubGrpId = new GroupID();
                tmpSubGrpId.szID = deviceGroupInfo.getGroupID();
                //add sub group
                queueGrpId.add(tmpSubGrpId);

                int taskDevId = sdk.GetDeviceByGroup(tmpSubGrpId, errorCode);
                System.out.println("当前设备分组下设备的数量"+taskDevId);
                if (taskDevId <= 0)
                {
                    System.out.println("sdk.GetDeviceByGroup("+tmpSubGrpId+") failed, error = "+errorCode[0]);
                    continue;
                }
                boolean bTmpRet = true;
                while (bTmpRet)
                {
                    DeviceInfo deviceinfo = new DeviceInfo();
                    bTmpRet = sdk.GetDeviceNext(taskDevId, deviceinfo, errorCode);
                    if(bTmpRet == false)
                    {
                        if (errorCode[0] != 0)
                        {
                            System.out.println("sdk.GetDeviceNext("+taskDevId+") failed, error = "+errorCode[0]);
                        }
                        continue;
                    }
                    ///// deviceinfo
                    System.out.println("device:" + deviceinfo.deviceID + ","+deviceinfo.szDevSrcAlias);

                    DevChn tDevChn = new DevChn();
                    tDevChn.deviceID = deviceinfo.deviceID;
                    tDevChn.domainID = deviceinfo.domainID;
                    tDevChn.nChn = 0;
                    tDevChn.nSrc = 0;
                    DevChn tDevGbID = new DevChn();
                    if (false == sdk.GetDeviceGBID(tDevChn, tDevGbID))
                    {
                        System.out.println("sdk.GetDeviceGBID("+tDevChn+") failed, error = "+errorCode[0]);
                    }
                    else
                    {
                        System.out.println("TOGBID: " + tDevChn.deviceID + " => " + tDevGbID.deviceID);
                    }
                    System.out.println();
                    System.out.println("+++++++++++++++"+ deviceinfo);
                    if ("31011500081327000002".equals(tDevGbID.deviceID)){
                        queueDeviceId.add(deviceinfo);
                    }

                }
            }
        }

        System.out.println(queueDeviceId.size()+"+++++++++++++++++++++++++");

        for (DeviceInfo devInfo: queueDeviceId)
        {
            DeviceID devId = new DeviceID();
            DeviceID[] vctDevID = new DeviceID[1];
            devId.setSzID(devInfo.deviceID);
            vctDevID[0] = devId;
            SubsDevices mSubsDevices = new SubsDevices();
            mSubsDevices.setVctDevID(vctDevID);
            mSubsDevices.setBySubsDevNum((byte) 1);
            if (false == sdk.SetSubscriptDeviceStatus(mSubsDevices,
                    IPhoenixSDKConstantVal.SDK_SUB_SCRIPTION_TYPE_ONLINE,
                    errorCode))
            {
                System.out.println("sdk.SetSubscriptDeviceStatus("+devId+") failed, error = "+errorCode[0]);
            }
            else {
                System.out.println();
            }
        }

        boolean logoutFlag = sdk.Logout();
        if (!logoutFlag){
            return;
        }
        System.out.println("Logout success!!!*******************");
        System.out.println("-----------------------");

    }
}
