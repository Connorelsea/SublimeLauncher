package com.elsea.sublimelauncher;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

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
		project.processUIState(selected);
		return project.getPanel();
	}

}