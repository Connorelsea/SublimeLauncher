package com.elsea.sublimelauncher;

import java.awt.EventQueue;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ViewAddNew extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textLocation;
	private FileSystem fileSystem = FileSystem.getInstance();
	private File file;
	private ViewAddNew view = this;

	public ViewAddNew(ProjectContainer projects) {
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setTitle("Add New Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 224);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProjectName = new JLabel("Project Name");
		lblProjectName.setBounds(10, 18, 126, 14);
		contentPane.add(lblProjectName);
		
		textName = new JTextField();
		textName.setBounds(146, 11, 358, 28);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JLabel lblProjectLocation = new JLabel("Parent Folder");
		lblProjectLocation.setBounds(10, 57, 98, 14);
		contentPane.add(lblProjectLocation);
		
		textLocation = new JTextField();
		textLocation.setColumns(10);
		textLocation.setBounds(146, 50, 242, 28);
		contentPane.add(textLocation);
		
		JButton btnFolder = new JButton("Choose Folder");
		btnFolder.addActionListener(action -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose a Parent Folder");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				textLocation.setText(file.getAbsolutePath());
			}
		});
		btnFolder.setBounds(398, 50, 106, 28);
		contentPane.add(btnFolder);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 133, 494, 2);
		contentPane.add(separator);
		
		JButton btnSave = new JButton("Create and Save");
		btnSave.addActionListener(action -> {
			
			File saveLocation = new File(file.getAbsolutePath() + File.separator + textName.getText());
			
			try {
				Files.createDirectories(Paths.get(saveLocation.getAbsolutePath()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			projects.add(new Project(textName.getText(), saveLocation.getAbsolutePath()));
			fileSystem.save();
			
			view.dispose();
		});
		btnSave.setBounds(348, 146, 156, 28);
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(action -> {
			view.dispose();
		});
		btnCancel.setBounds(10, 146, 106, 28);
		contentPane.add(btnCancel);
		
		JLabel lblProjectTemplate = new JLabel("Project Template");
		lblProjectTemplate.setBounds(10, 96, 98, 14);
		contentPane.add(lblProjectTemplate);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Currently Unsupported"}));
		comboBox.setBounds(146, 89, 358, 28);
		contentPane.add(comboBox);
		
		setLocationRelativeTo(null);
	}
}
