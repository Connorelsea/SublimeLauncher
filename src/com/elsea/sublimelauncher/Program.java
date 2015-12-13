package com.elsea.sublimelauncher;

import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;

public class Program {
	
	private ProjectContainer projects;
	private SublimeContainer sublimes;
	private FileSystem fileSystem;
	
	public static void main(String[] args) {
		new Program();
	}
	
	public Program() {
		
		sublimes = new SublimeContainer();
		sublimes.findSublime();
		
		projects = new ProjectContainer();
		
		fileSystem = new FileSystem(sublimes, projects);
		
		if (!fileSystem.load()) {
			System.err.println("Unable to load Sublime Launcher");
		}
		
		new ViewLaunch(projects, sublimes).setVisible(true);
		
//		for (Project project : projects) {
//			System.out.println(project.getLocation().toString());
//		}
//		
//		for (SublimeLocation sl : sublimes.getLocations()) {
//			System.out.println(sl.getFile().toString());
//		}
		
		//sublimes.getLocation(0).load("C:\\Users\\connorelsea\\OneDrive\\Programming");
	}

}