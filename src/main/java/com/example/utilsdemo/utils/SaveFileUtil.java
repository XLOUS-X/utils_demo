package com.example.utilsdemo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveFileUtil {

    public static String saveFile(MultipartFile file, String username){
        if (file == null) {
            return "请选择表格！";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = df.format(new Date());
        String date1 = df1.format(new Date());
        InputStream ins = null;
        String excelName = null;
        String address = "";
        String picPath = "D://utilsDemo/data/excel/";
        try {
            ins = file.getInputStream();
            String excelFilename = file.getOriginalFilename();
            //兼容ie9传递来的文件名
            if (excelFilename.contains("://")) {
                excelFilename = excelFilename.substring(excelFilename.lastIndexOf("//") + 1);
            }
            //文件夹的路径
            String picsPath = picPath + date;
            File excelFile = new File(picsPath);
            if (!excelFile.exists()) {
                excelFile.mkdirs();
            }
            //文件名称
            String[] picnames = excelFilename.split("\\.");
            excelName = username + date1 + "." + picnames[1];
            address = picsPath + "/" + excelName;
            System.out.println(picPath);
            Path path = Paths.get(address);
            //将文件拷贝到服务器上
            Files.copy(ins, path, StandardCopyOption.REPLACE_EXISTING);
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(address);
        return address;
    }
}
