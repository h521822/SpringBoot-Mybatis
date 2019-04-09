package com.example.demo.test;

import com.example.demo.untils.FileCopyUntils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Scanner;

/**
 * @ClassName FileCopyTests
 * @Author FileCopyTests
 * @Date 2019/2/24 21:51
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileCopyTests {

    @Autowired
    private FileCopyUntils fileCopyUntils;

    @Test
    public static void copyFileUsingFileStreams(String[] args) throws IOException {
        File source = new File("C:\\Users\\He\\Desktop\\7aec54e736d12f2e2ac37c3047c2d56284356898.jpg");
        File dest = new File("C:\\Users\\He\\Desktop\\117aec54e736d12f2e2ac37c3047c2d56284356898.jpg");

        // fileCopyUntils.copyFileUsingFileStreams(source, dest);
        copyFileUsingFileStreams(source, dest);

        // copyFile("C:\\Users\\He\\Desktop\\7aec54e736d12f2e2ac37c3047c2d56284356898.jpg", "C:\\Users\\He\\Desktop\\117aec54e736d12f2e2ac37c3047c2d56284356898.jpg");
    }

    /**
     * @param source 原文件
     * @param dest   目标文件
     * @return void
     * @author He
     * @description 文件拷贝
     * @date 2019/2/24 21:49
     **/
    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        input = new FileInputStream(source);
        output = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buf)) != -1) {
            output.write(buf, 0, bytesRead);
        }
        input.close();
        output.close();
    }


    public static void copyFile(String oldPath, String newPath) throws IOException {

        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);
        ;

        byte[] buffer = new byte[1024];
        int readByte = 0;
        while ((readByte = in.read(buffer)) != -1) {
            out.write(buffer, 0, readByte);
        }

        in.close();
        out.close();
    }

    // @Test
    // public static void main(String[] args) throws IOException {
    //     // Scanner sc = new Scanner(System.in);
    //     // System.out.println("请输入源目录：");
    //     // String sourcePath = sc.nextLine();
    //     // System.out.println("请输入新目录：");
    //     // String path = sc.nextLine();
    //
    //     //String sourcePath = "D://aa";
    //     //String path = "D://bb";
    //
    //     // copyFile("C:\\Users\\He\\Desktop\\7aec54e736d12f2e2ac37c3047c2d56284356898.jpg", "C:\\Users\\He\\Desktop\\117aec54e736d12f2e2ac37c3047c2d56284356898.jpg");
    //
    //
    //     File source = new File("C:\\Users\\He\\Desktop\\无标题.txt");
    //     File dest = new File("C:\\Users\\He\\Desktop\\无标题1.txt");
    //
    //     // fileCopyUntils.copyFileUsingFileStreams(source, dest);
    //     copyFileUsingFileStreams(source, dest);
    // }

    // public static void copyDir(String sourcePath, String newPath) throws IOException {
    //     File file = new File(sourcePath);
    //     String[] filePath = file.list();
    //
    //     if (!(new File(newPath)).exists()) {
    //         (new File(newPath)).mkdir();
    //     }
    //
    //     for (int i = 0; i < filePath.length; i++) {
    //         if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
    //             copyDir(sourcePath  + file.separator  + filePath[i], path  + file.separator + filePath[i]);
    //         }
    //
    //         if (new File(sourcePath  + file.separator + filePath[i]).isFile()) {
    //             copyFile(sourcePath + file.separator + filePath[i], path + file.separator + filePath[i]);
    //         }
    //
    //     }
    // }



}
