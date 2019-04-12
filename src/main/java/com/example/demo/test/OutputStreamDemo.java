package com.example.demo.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName OutputStreamDemo
 * @Author OutputStreamDemo
 * @Date 2019/4/13 2:28
 * @Version 1.0
 * 提交文件（将字符串提交到指定文件）
 **/
public class OutputStreamDemo {

    public static void main(String[] args) {
        outputStream();
    }

    public static void outputStream(){
        String content = ReaderFileDemo.fileInput_charsetDemo();
        File file = new File("C:\\Users\\He\\Desktop\\test.txt");
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                // 先得到文件的上级目录，并创建上级目录，在创建文件
                file.getParentFile().mkdir(); // 创建文件夹
                file.createNewFile();  // 创建文件
            }
            // 创建文件输出流
            fileOutputStream = new FileOutputStream(file);
            // 将字符串转化为字节
            byte[] byteArr = content.getBytes();
            fileOutputStream.write(byteArr);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
