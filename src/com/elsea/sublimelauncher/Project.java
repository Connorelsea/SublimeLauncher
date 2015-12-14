package com.elsea.sublimelauncher;

import java.io.File;

public class Project {
	private String  name;
	private File    location;
	private File    icon;
	private boolean hasIcon;
	
	public Project(String name, String path, String iconPath) {
		
		this.setName(name);
		this.setLocation(new File(path));
		
		if (iconPath.equals("none")) {
			hasIcon = false;
		} else {
			hasIcon = true;
			this.setIcon(new File(iconPath));
		}
		
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

	public File getIcon() {
		return icon;
	}

	public void setIcon(File icon) {
		this.icon = icon;
	}

	public boolean hasIcon() {
		return hasIcon;
	}
	
	public void setHasIcon(boolean value) {
		this.hasIcon = value;
	}
}