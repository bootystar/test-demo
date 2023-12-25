package com.example.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author booty
 * @date 2021/5/21 10:01
 */
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        //打印文件名
        log.info(file.getOriginalFilename());
        //保存文件：在springboot项目中，由于无法找到内置的tomcat
        //所以文件只能存放在固定的某个位置（windows：某盘某文件夹下，Linux：某文件夹下）

        //获取idea

        File resourcesPathFile = getResourcesPathFile();

        File upload = new File(resourcesPathFile,"upload");

        File newFile = new File(upload, file.getOriginalFilename());
        //写入硬盘
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


    private File getResourcesPathFile(){
        //target根目录
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //当前模块目录
        File enjoy=new File(rootPath).getParentFile();
        //src目录
        File src=new File(enjoy,"src");
        //main目录
        File main=new File(src,"main");
        //resources目录
        File resources=new File(main,"resources");

        return resources;
    }
}
