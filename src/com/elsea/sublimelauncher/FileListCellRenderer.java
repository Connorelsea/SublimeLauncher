package com.elsea.sublimelauncher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

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
		panel.setBackground(new Color(225, 234, 248));
		
		JLabel label = new JLabel(project.getName() + " - " + project.getLocation().getAbsolutePath());
		label.setForeground(new Color(25, 50, 95));
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		if (selected) {
			panel.setBackground(new Color(147, 168, 201));
			panel.setBorder(new LineBorder(new Color(75, 98, 133)));
		}
		
		panel.add(label);
		
		return panel;
	}

}