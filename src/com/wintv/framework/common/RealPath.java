package com.wintv.framework.common;

public class RealPath {

	public String getRealPath() {
		
		String realPath = this.getClass().getResource("/").toString().substring(6);
		
		realPath = realPath.replace("%20", " ");
		realPath = realPath.replace("/", "\\\\");
		
		return realPath;
	}
}
