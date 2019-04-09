package com.example.demo.untils;

import java.io.*;

/**
 * @ClassName FileCopyUntils
 * @Author FileCopyUntils
 * @Date 2019/2/24 21:47
 * @Version 1.0
 * 文件或文件夹拷贝
 **/
public class FileCopyUntils {


    /**
     * @author He
     * @description 文件拷贝
     * @date 2019/2/24 21:49
     * @param source 原文件
     * @param dest 目标文件
     * @return void
     **/
    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }



}


