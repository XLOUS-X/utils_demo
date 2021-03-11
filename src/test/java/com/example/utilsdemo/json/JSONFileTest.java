package com.example.utilsdemo.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

@Slf4j
public class JSONFileTest {

    public static void main(String[] args) {
        String json = "{\"deepLprModelPath\":\"C:\\\\develops\\\\models\\\\Simiris\\\\LPR\\\\model\",\"detectClassIds\":[0,1,2,3,4,5,11,12,13],\"ipAddress\":\"222.67.206.228:51554\",\"maxObjectAreaInPercent\":0.6,\"minObjectAreaInPixel\":1000,\"minTimeToConsiderAsParking\":5,\"missedTimeAllowed\":1,\"numberOfVideoRequired\":3,\"parkingViolationTime\":60,\"password\":\"pdjj1567\",\"poses\":[{\"inside\":true,\"p\":150.0,\"t\":15.0,\"z\":2.5}],\"potralPoseDuration\":600,\"retryOpportunies\":1,\"username\":\"admin\"}";
        String fileName = "D:\\jsonString/config.json";
        boolean success = createJSONFile(json, fileName);
        if (success){
            log.info("ok");
        }else {
            log.info("error");
        }
    }

    //封装创建json文件的方法
    public static boolean createJSONFile(String jsonString, String fullPath){
        boolean flag = true;
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();//创建新文件
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            log.info("json文件内容：" + jsonString);
            write.write(jsonString);
            log.info("文件创建成功！");
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}
