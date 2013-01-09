package com.application.handle;

public class WindowHandler {
	
	public native int getHwnd(String title);
	static {
		System.loadLibrary("WindowHandler");
	}

}
