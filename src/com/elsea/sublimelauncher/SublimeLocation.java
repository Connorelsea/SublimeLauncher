package com.elsea.sublimelauncher;

import java.io.File;
import java.io.IOException;

/**
 * A container that holds the location of a Sublime Text
 * program on the user's computer. Can  create a process
 * and execute said program.
 */
public class SublimeLocation {
	
	private String name;
	private File file;
	
	/**
	 * Creates a container that holds the location of a Sublime
	 * Text program on the user's computer.
	 * 
	 * @param file The location of the sublime executable
	 */
	public SublimeLocation(File file) {
		this.file = file;
	}
	
	/**
	 * Loads the Sublime Text program that this container holds
	 * and loads a specific folder or file into said program.
	 * 
	 * @param locationToOpen Location of the folder or file to load
	 * @return Whether or not the location was loaded successfully
	 */
	public boolean load(String locationToOpen) {
		
		System.out.println("OPENING: " + locationToOpen);
		
		try {
			new ProcessBuilder(file.getAbsolutePath(), locationToOpen).start();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Loads the Sublime Text program that this container holds
	 * and loads a specific folder or file into said program.
	 * 
	 * @param p The project to load
	 * @return Whether or not the project was loaded successfully
	 */
	public boolean load(Project p) {
		return load(p.getLocation().getAbsolutePath());
	}
	
	public File getFile() {
		return file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}