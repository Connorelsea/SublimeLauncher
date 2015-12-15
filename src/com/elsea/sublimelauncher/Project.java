package com.elsea.sublimelauncher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Project {
	
	private String  name;
	private File    location;
	
	private BufferedImage bufferedIcon;
	private File   		  icon;
	private boolean       hasIcon;
	
	private JPanel panel;
	
	public Project(String name, String path, String iconPath) {
		
		this.setName(name);
		this.setLocation(new File(path));
		
		if (iconPath.equals("None")) {
			hasIcon = false;
		} else {
			hasIcon = true;
			this.setIcon(new File(iconPath));
			loadIcon();
		}
		
	}
	
	public void loadIcon() {
		
		BufferedImage bi = null;
		
		try {
			bi = ImageIO.read(getIcon());
			bufferedIcon = bi;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public JPanel getPanel() {
		
		if (panel == null) {
			
			Dimension container  = new Dimension(300, 60);
			Color     background = new Color(234, 241, 251);
			Color     foreground = new Color(25, 50, 95);
			
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(background);
			panel.setPreferredSize(container);
			panel.setMinimumSize(container);
			panel.setMaximumSize(container);
			
			JPanel panelTitle = new JPanel();
			panelTitle.setBackground(background);
			panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.Y_AXIS));
			
			JLabel labelName = new JLabel(getName());
			JLabel labelPath = new JLabel(getLocation().getAbsolutePath());
			
			labelName.setForeground(foreground);
			labelPath.setForeground(foreground);
			
			panelTitle.add(labelName);
			panelTitle.add(labelPath);
			
			panel.add(panelTitle, BorderLayout.CENTER);
			
			if (hasIcon()) {
				
				Dimension dimIcon = new Dimension(60, 60);
				
				JPanel panelIcon = new JPanel() {
					
					public void paint(Graphics g) {
						Graphics2D g2d = (Graphics2D) g;
						
						int x = (60 / 2) - (50 / 2);
						int y = (60 / 2) - (50 / 2);
						g.drawImage(getBufferedIcon(), x, y, 50, 50, null);
					}
					
				};
				
				panelIcon.setBackground(background);
				panelIcon.setPreferredSize(dimIcon);
				panelIcon.setMinimumSize(dimIcon);
				panelIcon.setMaximumSize(dimIcon);
				
				panel.add(panelIcon, BorderLayout.WEST);
			}
			
		}
		
		return panel;
	}
	
	public BufferedImage getBufferedIcon() {
		return bufferedIcon;
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