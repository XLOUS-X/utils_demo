package com.example.utilsdemo.question;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {

    private String s;
    public MyCallable(String s) {
        this.s = s;
    }

    @Override
    public Object call() throws Exception {
        return s;
    }
}
