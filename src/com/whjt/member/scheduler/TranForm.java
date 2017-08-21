package com.whjt.member.scheduler;

public class TranForm {
	// 服务器地址
	// public static final String DOMAIN_URL ="http://60.212.191.147:8080/weihai";
	// 本地测试地址
	public static final String DOMAIN_URL = "http://localhost:8088/weihai";

	public static void main(String[] args) {
		Thread rthread = new Thread(new TransFormGPS(DOMAIN_URL));
		rthread.start();
	}
}
