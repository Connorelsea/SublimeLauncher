package com.elsea.sublimelauncher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import com.elsea.stone.groups.Element;
import com.elsea.stone.groups.Group;
import com.elsea.stone.groups.Groups;

public class FileSystem {
	
	private File programLocation;
	private File stoneFile;
	private File templateLocation;
	private File sublimeImage;
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
			sublimeImage     = new File(programLocation.getAbsolutePath() + File.separator + "sublime.png");
			
		} else if (os.contains("osx") || os.contains("mac")) {
			
			programLocation  = new File("/Library/Elsea/SublimeLauncher");
			templateLocation = new File(programLocation.getAbsolutePath() + "/Templates");
			stoneFile        = new File(programLocation.getAbsolutePath() + "/program.stone");
			sublimeImage     = new File(programLocation.getAbsolutePath() + File.separator + "sublime.png");
			
		} else {
			
			programLocation  = new File(System.getProperty("user.dir") + "/Elsea/SublimeLauncher");
			templateLocation = new File(programLocation.getAbsolutePath() + "/Templates");
			stoneFile        = new File(programLocation.getAbsolutePath() + "/program.stone");
			sublimeImage     = new File(programLocation.getAbsolutePath() + File.separator + "sublime.png");
		}
	}
	
	public void checkFixPath() {
		
		if (!programLocation.exists()) {
			
			try {
				
				Files.createDirectories(Paths.get(programLocation.getAbsolutePath()));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public boolean save() {
		
		System.out.println("Saving Stone data file. Writing to disk.");
		
		Group g = new Group();
		
		g.addChild(sublimes.generateGroup());
		g.addChild(projects.generateGroup());
		
		Groups.get().write(g).to(stoneFile);
		
		return true;
	}
	
	public boolean load() {
		
		long loadStart = System.currentTimeMillis();
		System.out.println("Loading program file system");
		
		determinePath();
		checkFixPath();
		
		// Dump image resource from inside jar to outside jar
		// Loading resource on render takes way too long. Better
		// to have long file load (from this) on first run than
		// long load during render every time.
		
		BufferedImage bi;
		
		if (!sublimeImage.exists()) {
			
			try {
				bi = ImageIO.read(ViewLaunch.class.getResource("/sublime.png"));
				ImageIO.write(bi, "png", new File(programLocation.getAbsolutePath() + File.separator + "sublime.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
		// Load projects
		
		if (stoneFile.exists()) {
			
			Group g = Groups.get().read(stoneFile);
			
			List<Element> elements = g.search().group("projects").getChildren();

			
			for (Element e : elements) {
				
				long newProjStart = System.currentTimeMillis();
				
				String   name  = e.getName();
				String[] props = e.getCurrentValue().split("#<#>#");
				String   path  = props[0];
				String   icon  = props[1];
				
				projects.add(new Project(name, path, icon));
				
				long newProjEnd = System.currentTimeMillis();
				
				System.out.println("PROJECT: \"" +  name + "\" took " + (newProjEnd - newProjStart) + "ms");
			}
			
			List<Element> sbls = g.search().group("sublimes").getChildren();
			
			for (Element e : sbls) {
				sublimes.addLocation(new File(e.getCurrentValue()));
			}
			
			long loadEnd = System.currentTimeMillis();
			System.out.println("Took " + (loadEnd - loadStart) + " for fileSystem.load()");
			
		}
		
		return true;
	}
	
	public File getTemplateLocation() {
		return templateLocation;
	}
	
	public File getSublimeImageLocation() {
		return sublimeImage;
	}

}