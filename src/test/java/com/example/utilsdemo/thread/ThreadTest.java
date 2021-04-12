package com.example.utilsdemo.thread;

public class ThreadTest {

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i=0;i<10000;i++)
            System.out.println("1");
        }).start();
        System.out.println("hello");

    }
}
