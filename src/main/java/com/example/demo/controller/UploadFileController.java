package com.example.demo.controller;

import com.example.demo.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @ClassName UploadFileController
 * @Author UploadFileController
 * @Date 2019/4/14 14:45
 * @Version 1.0
 **/
@Controller
@RequestMapping("/uploadFile")
public class UploadFileController {

    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping("/index")
    public String uploadFile() {
        return "uploadFile";
    }

    @ResponseBody
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        return  uploadFileService.uploadFile(file,request);
    }

    @PostMapping("/downloadFile")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return uploadFileService.downloadFile(request,response);
    }

    /*
     * 跳转到文件上传画面
     */
    @RequestMapping("/file")
    public String file(){
        return "file";
    }


    /**
     * 实现文件上传
     * */
    @ResponseBody
    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("fileName") MultipartFile file){
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "C:/Users/He/Desktop/upload" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }

    /*
     * 跳转多文件上传画面
     */
    @RequestMapping("/multifile")
    public String multifile(){
        return "multifile";
    }

    /**
     * 实现多文件上传
     * Spring Boot默认文件上传大小为2M，多文档上传中总是出现文件大小超出限度
     * */
    @ResponseBody
    @RequestMapping(value="/multifileUpload",method=RequestMethod.POST)
    public String multifileUpload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");
        if(files.isEmpty()){
            return "false";
        }
        // 文件保存路径
        String path = "C:/Users/He/Desktop/upload/" ;

        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return "false";
            }else{
                File dest = new File(path + "/" + fileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }

    @RequestMapping("/download")
    public String downLoad(HttpServletResponse response){
        // 被下载文件的名称
        String filename="9-50.jpg";
        // 被下载的文件在服务器中的路径
        String filePath = "C:/Users/He/Desktop/upload" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download"); // 设置强制下载不打开
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
