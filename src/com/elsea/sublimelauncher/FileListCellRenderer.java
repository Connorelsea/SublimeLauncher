package com.elsea.sublimelauncher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.Position.Bias;

public class FileListCellRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(
		JList list,
		Object value,
		int index,
		boolean selected,
		boolean focused
	) {
		
		Project project = (Project) value;
		
		// Container Panel
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		
		
		
		// Name Panel
		
		JPanel panel_right = new JPanel();
		panel_right.setLayout(new BoxLayout(panel_right, BoxLayout.Y_AXIS));
		panel_right.setBackground(new Color(234, 241, 251));
		
		JLabel label = new JLabel(project.getName());
		label.setForeground(new Color(25, 50, 95));
		label.setFont(new Font("Sans Serif", Font.PLAIN, 17));
		
		JLabel path = new JLabel(project.getLocation().getAbsolutePath());
		path.setForeground(new Color(25, 50, 95));
		
		panel_right.add(Box.createRigidArea(new Dimension(30, 10)));
		panel_right.add(label);
		panel_right.add(Box.createRigidArea(new Dimension(5, 5)));
		panel_right.add(path);
		panel_right.add(Box.createRigidArea(new Dimension(30, 10)));
		
		container.add(panel_right, BorderLayout.CENTER);
		if (selected) panel_right.setBackground(new Color(207, 220, 241));
		
		// Get Icon
		
		BufferedImage bi = null;
		ImageIcon image  = null;
		
		try {
			System.out.println(project.getIcon().getAbsolutePath());
			bi = ImageIO.read(new File(project.getIcon().getAbsolutePath()));
			//bi = ImageIO.read(project.getIcon());
			image = new ImageIcon(bi);
		} catch (IOException e) {
			project.setHasIcon(false);
			e.printStackTrace();
		}
		
		if (project.hasIcon()) {
			
			final Image img = image.getImage();
			
			// Icon Panel
			
			JPanel panel_left = new JPanel() {
				public void paint(Graphics g) {
					
					super.paint(g);
					
					int x = (60 / 2) - (50 / 2);
					int y = (panel_right.getHeight() / 2) - (50 / 2);
					g.drawImage(img, x, y, 50, 50, null);
					
				}
			};
			
			panel_left.setBackground(new Color(234, 241, 251));
			
			int height = panel_right.getHeight();
			panel_left.setPreferredSize(new Dimension(60, height));
			panel_left.setMinimumSize(new Dimension(60, height));
			panel_left.setMaximumSize(new Dimension(60, height));
			
			container.add(panel_left, BorderLayout.WEST);
			
			if (selected) panel_left.setBackground(new Color(207, 220, 241));
		}
		
		return container;
	}

}