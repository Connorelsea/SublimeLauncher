package com.elsea.sublimelauncher;

import java.io.File;

public class Project {
	private String name;
	private File   location;
	
	public Project(String name, String path) {
		this.setName(name);
		this.setLocation(new File(path));
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}