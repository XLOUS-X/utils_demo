package com.example.utilsdemo.codec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecodeUtil {

    public static void main(String[] args) {
        byte[] bytes = {49, 50, 51, 52, 53, 54, 55, 32, 18, 33, 19, 64, 56, 0, 1, 0};
        System.out.println(parseStringFromBytes(bytes,0,16));
        System.out.println(parseBcdStringFromBytes(bytes,7,6));
    }


    private static String parseStringFromBytes(byte[] data, int startIndex, int lenth) {
        try {
            byte[] tmp = new byte[lenth];
            System.arraycopy(data, startIndex, tmp, 0, lenth);
            return new String(tmp, "UTF-8");
        } catch (Exception e) {
            log.error("解析字符串出错:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth) {
        try {
            byte[] tmp = new byte[lenth];
            System.arraycopy(data, startIndex, tmp, 0, lenth);
            return BCD8421Operater.bcd2String(tmp);
        } catch (Exception e) {
            log.error("解析BCD(8421码)出错:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
