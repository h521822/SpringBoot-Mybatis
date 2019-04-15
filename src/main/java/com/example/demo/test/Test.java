package com.example.demo.test;/**
 * @Auther: He
 * @Date: 2019/4/15 10:55
 * @Description:
 */

import com.example.demo.config.FrameProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *@ClassName Test
 *@Author Test
 *@Date 2019/4/15 10:55
 *@Version 1.0
 **/
public class Test {
    @Autowired
    private static FrameProperties frameProperties;

    public static void main(String[] args) {
        System.out.println(frameProperties.getFile());
    }
}
