package com.elsea.sublimelauncher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import com.elsea.stone.groups.Element;
import com.elsea.stone.groups.Group;
import com.elsea.stone.groups.Groups;

public class FileSystem {
	
	private File programLocation;
	private File stoneFile;
	private File templateLocation;
	private SublimeContainer sublimes;
	private ProjectContainer projects;
	
	private static FileSystem instance;
	
	public static FileSystem createInstance(SublimeContainer sublimes, ProjectContainer projects) {
		return instance = new FileSystem(sublimes, projects);
	}
	public static FileSystem getInstance() {
		return instance;
	}
	
	private FileSystem(SublimeContainer sublimes, ProjectContainer projects) {
		this.sublimes = sublimes;
		this.projects = projects;
	}
	
	public void determinePath() {
		String os   = System.getProperty("os.name").toLowerCase();
		String user = System.getProperty("user.name");
		
		if (os.contains("win")) {
			
			programLocation  = new File("C:\\Users\\" + user + "\\AppData\\Roaming\\Elsea\\SublimeLauncher");
			templateLocation = new File(programLocation.getAbsolutePath() + "\\Templates");
			stoneFile        = new File(programLocation.getAbsolutePath() + "\\program.stone");
			
		} else {
			System.out.println("Running on currently unsupported system.");
		}
	}
	
	public void checkFixPath() {
		
		if (!programLocation.exists() || !stoneFile.exists()) {
			
			try {
				
				Files.createDirectories(Paths.get(programLocation.getAbsolutePath()));
				
				Group g = new Group();
				
				g
					.group("sublimes")
					.end()
					.group("projects")
					.end();
				
				Groups.get().write(g).to(stoneFile);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public boolean save() {
		
		Group g = new Group();
		
		g.addChild(sublimes.generateGroup());
		g.addChild(projects.generateGroup());
		
		Groups.get().write(g).to(stoneFile);
		
		return true;
	}
	
	public boolean load() {
		
		determinePath();
		checkFixPath();
		
		// Load projects
		
		Group g = Groups.get().read(stoneFile);
		
		List<Element> elements = g.search().filter(p -> 
			p.getParent() != null && p.getParent().getName().equals("projects")
		);
		
		
		for (Element e : elements) {
			
			String   name  = e.getName();
			String[] props = e.getCurrentValue().split("#<#>#");
			String   path  = props[0];
			String   icon  = props[1];
			
			projects.add(new Project(name, path, icon));
		}
		
		List<Element> sbls = g.search().filter(p ->
			p.getParent() != null && p.getParent().getName().equals("sublimes")
		);
		
		for (Element e : sbls) {
			sublimes.addLocation(new File(e.getCurrentValue()));
		}
		
		return true;
	}
	
	public File getTemplateLocation() {
		return templateLocation;
	}

}