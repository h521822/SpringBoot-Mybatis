package com.example.demo.test;

import java.io.*;

/**
 * @ClassName FileInfoTest
 * @Author FileInfoTest
 * @Date 2019/4/13 0:14
 * @Version 1.0
 **/
public class FileInfoTest {

    public static void main(String[] args) {
        // 中文乱码
        // fileInputDemo();
        // 格式不对，所有输出的文件合并成一行
        // inputStreamDemo();
        // 完全与原文本一致
        fileInput_charsetDemo();
    }



    public static void fileInputDemo() {
        File file = new File("C:\\Users\\He\\Desktop\\git.txt");
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            int length;
            String content = "";
            byte[] bytes = new byte[1024];
            while ((length = fileInputStream.read(bytes)) != -1) {
                content += new String(bytes, 0, length);
            }
            System.out.println(content);
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileInput_charsetDemo() {
        File file = new File("C:\\Users\\He\\Desktop\\git.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int length;
            String content = "";
            // byte[] bytes = new byte[1024];
            // 创建一个和文本一样大小的缓存区
            byte[] bytes = new byte[fileInputStream.available()];

            while ((length = fileInputStream.read(bytes)) != -1) {
                content += new String(bytes, 0, length, "GBK");
            }
            System.out.println(content);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inputStreamDemo() {
        File file = new File("C:\\Users\\He\\Desktop\\git.txt");
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");//最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            String content = "";
            while ((line = bufferedReader.readLine()) != null) {
                content += line;
            }
            System.out.println(content);
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
