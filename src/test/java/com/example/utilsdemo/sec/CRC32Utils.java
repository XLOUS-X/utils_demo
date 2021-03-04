package com.example.utilsdemo.sec;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

public class CRC32Utils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String crc32(final String str) {
        return crc32(str, DEFAULT_CHARSET);
    }

    public static String crc32(final String str, String charset) {
        try {
            byte[] bytes = str.getBytes(charset);
            return crc32(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String crc32(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return Long.toHexString(crc32.getValue());
    }

    public static void main(String[] args) {
        System.out.println(crc32("P23LJ7261L00205"));
    }

}
