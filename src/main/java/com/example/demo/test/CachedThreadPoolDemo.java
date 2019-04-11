package com.example.demo.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CachedThreadPoolDemo
 * @Author CachedThreadPoolDemo
 * @Date 2019/4/11 23:32
 * @Version 1.0
 **/
public class CachedThreadPoolDemo {
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        CachedThreadPoolDemo.run(() ->{
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        CachedThreadPoolDemo.run(() ->{
            for (int i = 0; i < 100; i++) {
                System.out.println("B" + i++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        CachedThreadPoolDemo.run(() ->{
            for (int i = 0; i < 100; i++) {
                System.out.println("C" + i++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void run(Runnable runnable){
        pool.execute(runnable);
    }

}
