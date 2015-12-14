package com.elsea.sublimelauncher;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ViewAddExisting extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textLocation;
	private FileSystem fileSystem = FileSystem.getInstance();
	private File file;
	private ViewAddExisting view = this;
	private JTextField textIcon;

	public ViewAddExisting(ProjectContainer projects) {
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setTitle("Add Existing Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 217);
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
		separator.setBounds(10, 128, 414, 2);
		contentPane.add(separator);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(action -> {
			
			boolean exists = new File(textIcon.getText()).exists();
			
			if (textIcon.getText().trim().equals("") || !exists) {
				textIcon.setText("None");
			}
			
			projects.add(new Project(textName.getText(), textLocation.getText(), textIcon.getText()));
			fileSystem.save();
			view.dispose();
		});
		btnSave.setBounds(318, 141, 106, 28);
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(action -> {
			view.dispose();
		});
		btnCancel.setBounds(10, 141, 106, 28);
		contentPane.add(btnCancel);
		
		JLabel lblProjectIcon = new JLabel("Project Icon (50px)");
		lblProjectIcon.setBounds(10, 96, 98, 14);
		contentPane.add(lblProjectIcon);
		
		textIcon = new JTextField();
		textIcon.setColumns(10);
		textIcon.setBounds(118, 89, 190, 28);
		contentPane.add(textIcon);
		
		JButton btnChooseIcon = new JButton("Choose Icon");
		btnChooseIcon.addActionListener(action -> {
			
			FileFilter imageFilter = new FileNameExtensionFilter(
				    "Image files", ImageIO.getReaderFileSuffixes());
			
			final JFileChooser fc = new JFileChooser();
		    fc.addChoosableFileFilter(imageFilter);
		    fc.setAcceptAllFileFilterUsed(false);
		    int file = fc.showOpenDialog(view);
		    
		    textIcon.setText(fc.getSelectedFile().getAbsolutePath());
			
		});
		btnChooseIcon.setBounds(318, 89, 106, 28);
		contentPane.add(btnChooseIcon);
		
		setLocationRelativeTo(null);
	}
}
