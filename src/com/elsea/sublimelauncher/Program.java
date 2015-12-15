package com.elsea.sublimelauncher;

import java.awt.EventQueue;

public class Program {
	
	private ProjectContainer projects;
	private SublimeContainer sublimes;
	private FileSystem fileSystem;
	
	public static void main(String[] args) {
		new Program();
	}
	
	public Program() {
		
		long timeStart = System.currentTimeMillis();
		
		System.out.println("PROGRAM: Running program");
		
		System.out.println("PROGRAM: Creating sublime container");
		
		sublimes = new SublimeContainer();
		sublimes.findSublime();
		
		System.out.println("PROGRAM: Creating project container");
		
		projects = new ProjectContainer();
		
		long timeFileSystem = System.currentTimeMillis();
		
		System.out.println("PROGRAM: Loading file system");
		
		fileSystem = FileSystem.createInstance(sublimes, projects);
		
		if (!fileSystem.load()) {
			System.err.println("Unable to load Sublime Launcher");
		}
		
		System.out.println("File System done");
		
		long timeViewLaunch = System.currentTimeMillis();
		
		System.out.println("PROGRAM: Creating new ViewLaunch");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ViewLaunch view = new ViewLaunch(projects, sublimes, fileSystem);
				view.setVisible(true);
			}
		});
		
		System.out.println("Took " + (timeFileSystem - timeStart) + " for Program -> Program Done");
		System.out.println("Took " + (timeViewLaunch - timeFileSystem) + " for FileSystem -> FileSystem Done");
		
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