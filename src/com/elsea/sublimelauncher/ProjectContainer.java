package com.elsea.sublimelauncher;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.elsea.stone.groups.Group;

public class ProjectContainer {
	
	private ArrayList<Project> projects;
	private DefaultListModel<Project> model;
	
	public ProjectContainer() {
		projects = new ArrayList<>();
		model    = new DefaultListModel<>();
	}
	
	public Group generateGroup() {
		
		Group g = new Group("projects");
		
		for (Project project : projects) {
			g.property(project.getName(), project.getLocation().getAbsolutePath());
		}
		
		g.end();
		
		return g;
	}
	
	public void add(Project p) {
		projects.add(p);
		model.addElement(p);
	}
	
	public void remove(Project p) {
		projects.remove(p);
		model.removeElement(p);
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	public Project getProject(int index) {
		return projects.get(index);
	}
	
	public DefaultListModel<Project> getModel() {
		return model;
	}

}