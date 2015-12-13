package com.elsea.sublimelauncher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(new Color(225, 234, 248));
		
		JLabel label = new JLabel(project.getName() + " - " + project.getLocation().getAbsolutePath());
		label.setForeground(new Color(25, 50, 95));
		label.setPreferredSize(new Dimension(300, 30));
		label.setMinimumSize(new Dimension(300, 30));
		label.setMaximumSize(new Dimension(300, 30));
		label.setFont(new Font("Sans Serif", Font.PLAIN, 14));
		
		if (selected) {
			panel.setBackground(new Color(147, 168, 201));
			panel.setBorder(new LineBorder(new Color(75, 98, 133)));
		}
		
		panel.add(Box.createRigidArea(new Dimension(30, 0)));
		panel.add(label);
		
		return panel;
	}

}