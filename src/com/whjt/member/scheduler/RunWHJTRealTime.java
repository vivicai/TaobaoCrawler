package com.whjt.member.scheduler;

/**
 * Created by Kevin.Li on 2017/6/14.
 */
import java.io.File;

public class RunWHJTRealTime {
    public static void main(String[] args) {
        File logFile = new File("/home/whtravel/log/whjt_809.log");
        //File logFile = new File("C:\\威海交通局\\809对接日志\\whjt_809.log");
        System.out.println("realtime start");
        Thread rthread = new Thread(new LogReader(logFile));
        rthread.start();
    }
}
