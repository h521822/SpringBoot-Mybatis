package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UploadFileService
 * @Author UploadFileService
 * @Date 2019/4/14 15:10
 * @Version 1.0
 **/
public interface UploadFileService {

    /**
     * 文件上传
     * @param file 上传文件
     * @param request 请求
     * @return string
     */
    String uploadFile(MultipartFile file, HttpServletRequest request);


    /**
     * 文件下载
     * @param request 请求
     * @param response 响应
     * @return string
     * @throws Exception 异常
     */
    String downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
