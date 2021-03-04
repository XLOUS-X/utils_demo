package com.example.utilsdemo.utils;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelZipUtil {

    public static void readExcelByName(String path){
        List<Map<String, Object>> readAll = new ArrayList<>();
        List<Map<String, String>> excelList = new ArrayList<>();
        ExcelReader reader = ExcelUtil.getReader(path);
        readAll = reader.readAll();
        //读取标题名
        List<Object> headerList = reader.readRow(0);
        for (int i=0;i<headerList.size();i++){
            System.out.println(headerList.get(i));
        }
        for (int i = 0; i < readAll.size(); i++) {
            Map<String, String> map = new HashMap<>();
            String xh = String.valueOf(readAll.get(i).get("序号"));
            String hphm = String.valueOf(readAll.get(i).get("车辆号牌"));
            String wfdd = String.valueOf(readAll.get(i).get("违法地点"));
            String wfsj = String.valueOf(readAll.get(i).get("违法时间"));
            String wfxw = String.valueOf(readAll.get(i).get("违法行为"));
            String cllx = String.valueOf(readAll.get(i).get("车辆类型"));
            String zptpcx = String.valueOf(readAll.get(i).get("抓拍图片车型"));
            String lhyptxx = String.valueOf(readAll.get(i).get("六合一平台信息"));
            String wfyy = String.valueOf(readAll.get(i).get("无法录入原因"));
            String bz = String.valueOf(readAll.get(i).get("备注"));
            map.put("xh",xh);
            map.put("hphm",hphm);
            map.put("wfdd",wfdd);
            map.put("wfsj",wfsj);
            map.put("wfxw",wfxw);
            map.put("cllx",cllx);
            map.put("zptpcx",zptpcx);
            map.put("lhyptxx",lhyptxx);
            map.put("wfyy",wfyy);
            map.put("bz",bz);
            excelList.add(map);
        }
        for (int i=0;i<excelList.size();i++){
            for (int j=0;j<excelList.get(i).size();j++){
                Iterator<Map.Entry<String, String>> it = excelList.get(i).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
                System.out.println("--------------------");
            }
        }

    }

    public static void readExcelByRow(String path){
        List<Map<String, Object>> readAll = new ArrayList<>();
        List<Map<String, String>> excelList = new ArrayList<>();
        ExcelReader reader = ExcelUtil.getReader(path);
        readAll = reader.readAll();
        List<List<Object>> read = reader.read(readAll.size(), reader.getRowCount());
        for (List<Object> objects : read) {

            System.out.println(objects.get(0));
        }

        for (int i = 0; i < readAll.size(); i++) {
            Map<String, String> map = new HashMap<>();
            String xh = String.valueOf(readAll.get(i).get("序号"));
            String hphm = String.valueOf(readAll.get(i).get("车辆号牌"));
            String wfdd = String.valueOf(readAll.get(i).get("违法地点"));
            String wfsj = String.valueOf(readAll.get(i).get("违法时间"));
            String wfxw = String.valueOf(readAll.get(i).get("违法行为"));
            String cllx = String.valueOf(readAll.get(i).get("车辆类型"));
            String zptpcx = String.valueOf(readAll.get(i).get("抓拍图片车型"));
            String lhyptxx = String.valueOf(readAll.get(i).get("六合一平台信息"));
            String wfyy = String.valueOf(readAll.get(i).get("无法录入原因"));
            String bz = String.valueOf(readAll.get(i).get("备注"));
            map.put("xh",xh);
            map.put("hphm",hphm);
            map.put("wfdd",wfdd);
            map.put("wfsj",wfsj);
            map.put("wfxw",wfxw);
            map.put("cllx",cllx);
            map.put("zptpcx",zptpcx);
            map.put("lhyptxx",lhyptxx);
            map.put("wfyy",wfyy);
            map.put("bz",bz);
            excelList.add(map);
        }
        for (int i=0;i<excelList.size();i++){
            for (int j=0;j<excelList.get(i).size();j++){
                Iterator<Map.Entry<String, String>> it = excelList.get(i).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    //System.out.println(entry.getKey() + ":" + entry.getValue());
                }
                //System.out.println("--------------------");
            }
        }

    }

}
