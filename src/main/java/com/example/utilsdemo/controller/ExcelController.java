package com.example.utilsdemo.controller;

import com.example.utilsdemo.utils.ExcelZipUtil;
import com.example.utilsdemo.utils.SaveFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Value("${upload.path}")
    String picPath;

    @GetMapping(value = "uploadByName")
    @ResponseBody
    public String uploadByName(MultipartFile file, String username) {
        String address = SaveFileUtil.saveFile(file, username);
        ExcelZipUtil.readExcelByRow(address);
        return "success";

    }
}
