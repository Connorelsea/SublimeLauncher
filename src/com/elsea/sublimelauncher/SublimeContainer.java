package com.elsea.sublimelauncher;

import java.io.File;
import java.util.ArrayList;

import com.elsea.stone.groups.Group;

/**
 * SublimeContainer
 * 
 * Finds and contains all instances and versions of Sublime Text
 * on the user's computer.
 */
public class SublimeContainer {
	
	private ArrayList<SublimeLocation> locations;
	private ArrayList<String> possibleLocations;
	
	public Group generateGroup() {
		
		Group g = new Group("sublimes");
		
		for (SublimeLocation location: locations) {
			g.property(location.getName(), location.getFile().getAbsolutePath());
		}
		
		g.end();
		
		return g;
	}
	
	/**
	 * Locates all occurences and versions of the Sublime Text program
	 * on the user's computer and stores them into an Array List.
	 */
	public void findSublime() {
		
		locations = new ArrayList<>();
		possibleLocations = new ArrayList<>();
		
		possibleLocations.add("C:\\Program Files\\Sublime Text 3\\subl.exe");
		possibleLocations.add("C:\\Program Files\\Sublime Text 2\\sublime_text.exe");
		
		for (String loc : possibleLocations) {
			File file = new File(loc);
			if (file.exists()) locations.add(new SublimeLocation(file));
		}
		
	}
	
	public SublimeLocation getLocation(int index) {
		return locations.get(index);
	}
	
	public ArrayList<SublimeLocation> getLocations() {
		return locations;
	}
	
	public void addLocation(File file) {
		locations.add(new SublimeLocation(file));
	}

}