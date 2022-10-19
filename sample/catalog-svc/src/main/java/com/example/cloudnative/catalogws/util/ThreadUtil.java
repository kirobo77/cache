package com.example.cloudnative.catalogws.util;

public class ThreadUtil {
	
	public static void sleep() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
