package com.elsea.sublimelauncher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Project {
	
	private String  name;
	private File    location;
	
	private BufferedImage bufferedIcon;
	private File   		  icon;
	private boolean       hasIcon;
	
	private JPanel panel;
	private JPanel panelTitle;
	private JPanel panelLeft;
	private JPanel panelIcon;
	private JPanel panelPadding;
	
	private boolean selected = false;
	
	private Color background = new Color(234, 241, 251);
	private Color selection  = new Color(207, 220, 241);
	
	public Project(String name, String path, String iconPath) {
		
		this.name = name;
		this.location = new File(path);
		
		if (iconPath.equals("None")) {
			hasIcon = false;
		} else {
			hasIcon = true;
			icon = new File(iconPath);
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
	
	public void processUIState(boolean selected) {
		
		if (this.selected != selected) {
			
			if (selected == false) {
				
				panelTitle.setBackground(background);
				if (!hasIcon()) panelPadding.setBackground(background);
				if (hasIcon())  panelIcon.setBackground(background);
				
				this.selected = false;
				
			} else {
				
				panelTitle.setBackground(selection);
				if (!hasIcon()) panelPadding.setBackground(selection);
				if (hasIcon())  panelIcon.setBackground(selection);
				
				this.selected = true;
			}
			
		}
		
	}
	
	public JPanel getPanel() {
		
		if (panel == null) {
			
			Dimension container  = new Dimension(300, 60);
			Dimension padding    = new Dimension(15 , 60);
			Color     foreground = new Color(25, 50, 95);
			Font      large      = new Font("Sans Serif", Font.PLAIN, 17);
			
			// Container panel
			
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(background);
			panel.setPreferredSize(container);
			panel.setMinimumSize(container);
			panel.setMaximumSize(container);
			
			// Panel Title
			
			panelTitle = new JPanel();
			panelTitle.setBackground(background);
			panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.Y_AXIS));
			
			JLabel labelName = new JLabel(getName());
			JLabel labelPath = new JLabel(getLocation().getAbsolutePath());
			
			labelName.setForeground(foreground);
			labelName.setFont(large);
			labelPath.setForeground(foreground);
			
			panelTitle.add(Box.createRigidArea(new Dimension(10, 10)));
			panelTitle.add(labelName);
			panelTitle.add(labelPath);
			
			panel.add(panelTitle, BorderLayout.CENTER);
			
			// Left Panel
			
			panelLeft = new JPanel();
			panelLeft.setLayout(new BorderLayout());
			
			// Icon Panel
			
			if (hasIcon()) {
				
				Dimension dimIcon = new Dimension(60, 60);
				
				panelIcon = new JPanel() {

					public void paint(Graphics g) {
						
						super.paint(g);
						
						int x = (60 / 2) - (50 / 2);
						int y = (60 / 2) - (50 / 2);
						g.drawImage(getBufferedIcon(), x, y, 50, 50, null);
						
					}
					
				};
				
				panelIcon.setBackground(background);
				panelIcon.setPreferredSize(dimIcon);
				panelIcon.setMinimumSize(dimIcon);
				panelIcon.setMaximumSize(dimIcon);
				
				panelLeft.add(panelIcon, BorderLayout.CENTER);
				
			} else {
				
				// Padding Panel
				
				panelPadding = new JPanel();
				panelPadding.setBackground(background);
				panelPadding.setPreferredSize(padding);
				panelPadding.setMinimumSize(padding);
				panelPadding.setMaximumSize(padding);
				
				panelLeft.add(panelPadding, BorderLayout.EAST);	
				
			}
			
			// Adding Panel Left to Panel Container
			
			panel.add(panelLeft, BorderLayout.WEST);
			
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