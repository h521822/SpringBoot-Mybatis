package com.example.demo.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName FixedThreadPoolDemo
 * @Author FixedThreadPoolDemo
 * @Date 2019/4/12 0:08
 * @Version 1.0
 **/
// 多线程详情参考：https://www.cnblogs.com/zhujiabin/p/5404771.html
public class FixedThreadPoolDemo {
    // 这里的“2”指的是线程的最大并发数
    private static  ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        // 写法一
        FixedThreadPoolDemo.run(() ->{
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // 写法二
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("B" + i++);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("C" + i++);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void run(Runnable runnable){
        fixedThreadPool.execute(runnable);
    }

}
