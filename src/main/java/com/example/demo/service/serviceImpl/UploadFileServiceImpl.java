package com.example.demo.service.serviceImpl;

import com.example.demo.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @ClassName UploadFileServiceImpl
 * @Author UploadFileServiceImpl
 * @Date 2019/4/14 15:11
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UploadFileServiceImpl implements UploadFileService {

    /**
     * 文件上传
     * @param file 上传文件
     * @param request 请求
     * @return string
     */
    @Override
    public String uploadFile(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            // String contentType = file.getContentType();
            // 文件名称
            String saveFileName = file.getOriginalFilename();
            // 保存路径
            // File saveFile = new File(request.getSession().getServletContext().getRealPath("/upload/") + saveFileName);
            File saveFile = new File("C:/Users/He/Desktop/upload/" + saveFileName);
            // 判断父路径是否存在
            if (!saveFile.getParentFile().exists()) {
                // 创建文件夹
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return " 上传成功";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败";
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败";
            }
        } else {
            return "上传失败，因为文件为空.";
        }
    }



    /**
     * 文件下载
     *
     * @param request 请求
     * @return string
     * @throws Exception 异常
     */
    @Override
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置文件名，根据业务需要替换成要下载的文件名
        String fileName = "aim_test.txt";
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://aim//";
            File file = new File(realPath, fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }
}
