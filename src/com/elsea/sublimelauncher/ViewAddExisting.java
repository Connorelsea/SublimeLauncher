package com.elsea.sublimelauncher;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ViewAddExisting extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textLocation;
	private FileSystem fileSystem = FileSystem.getInstance();
	private File file;
	private ViewAddExisting view = this;

	public ViewAddExisting(ProjectContainer projects) {
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setTitle("Add Existing Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 180);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProjectName = new JLabel("Project Name");
		lblProjectName.setBounds(10, 18, 98, 14);
		contentPane.add(lblProjectName);
		
		textName = new JTextField();
		textName.setBounds(118, 11, 306, 28);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JLabel lblProjectLocation = new JLabel("Project Location");
		lblProjectLocation.setBounds(10, 57, 98, 14);
		contentPane.add(lblProjectLocation);
		
		textLocation = new JTextField();
		textLocation.setColumns(10);
		textLocation.setBounds(118, 50, 190, 28);
		contentPane.add(textLocation);
		
		JButton btnFolder = new JButton("Choose Folder");
		btnFolder.addActionListener(action -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose a Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				textLocation.setText(file.getAbsolutePath());
			}
		});
		btnFolder.setBounds(318, 50, 106, 28);
		contentPane.add(btnFolder);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 90, 414, 2);
		contentPane.add(separator);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(action -> {
			projects.add(new Project(textName.getText(), textLocation.getText()));
			fileSystem.save();
			view.dispose();
		});
		btnSave.setBounds(318, 103, 106, 28);
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(action -> {
			view.dispose();
		});
		btnCancel.setBounds(10, 103, 106, 28);
		contentPane.add(btnCancel);
		
		setLocationRelativeTo(null);
	}
}
