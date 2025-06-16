package com.xuchangan.controller;

import com.xuchangan.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        // 将文件内容存储到服务器本地
        String originalFilename = file.getOriginalFilename();
        // 保证文件名字唯一
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("D:\\CurriculumDesign\\java_db\\files\\"+filename));
        String url = "http://localhost:8080/upload/" + filename;
        return Result.success(url);
    }

}
