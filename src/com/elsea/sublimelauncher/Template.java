package com.elsea.sublimelauncher;

import java.io.File;

public class Template {
	
	private String name;
	private File   location;
	
	public Template(String name, String path) {
		this.name     = name;
		this.location = new File(path);
	}

	public File getLocation() {
		return location;
	}

}