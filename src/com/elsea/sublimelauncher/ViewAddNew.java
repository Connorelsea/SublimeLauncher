package com.elsea.sublimelauncher;

import java.awt.EventQueue;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;

public class ViewAddNew extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textLocation;
	private JComboBox comboBox;
	private FileSystem fileSystem = FileSystem.getInstance();
	private File file;
	private ViewAddNew view = this;
	private JTextField textIcon;

	public ViewAddNew(ProjectContainer projects) {
		
		// Load template information and create the
		// JComboBox model
		
		File templates = fileSystem.getTemplateLocation();
		
		ArrayList<String> children = new ArrayList<>();
		
		// If the user has templates, catalog them
		if (templates.list() != null) {
			
			for (String s : templates.list()) {
				children.add(s);
			}
			
		}
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		ArrayList<Template> temps = new ArrayList<>();
		
		model.addElement("No Template / Blank Project");
		
		// Create template menu
		
		for (String c: children) {
			File child = new File(templates.getAbsolutePath() + File.separator + c);
			temps.add(new Template(child.getName(), child.getAbsolutePath()));
			model.addElement(child.getName() + " - " + child.getAbsolutePath());
		}
		
		// Set to default system UI
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setTitle("Add New Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 255);
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
		separator.setBounds(10, 168, 494, 2);
		contentPane.add(separator);
		
		JButton btnSave = new JButton("Create and Save");
		btnSave.addActionListener(action -> {
			
			int selected = comboBox.getSelectedIndex();
			
			File saveLocation = new File(file.getAbsolutePath() + File.separator + textName.getText());
			
			// If the selection is zero, they chose no template.
			if (selected != 0) {
				
				// Template storage is always one less than the index
				// because the first option in the JComboBox model is
				// an option for none.
				Template template = temps.get(selected - 1);
				
				try {
					
					FileUtilities.copyDirectory(template.getLocation(), saveLocation);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				
				try {
					Files.createDirectory(Paths.get(saveLocation.getAbsolutePath()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			boolean exists = new File(textIcon.getText()).exists();
			
			if (textIcon.getText().trim().equals("") || !exists) {
				textIcon.setText("None");
			}
			
			String location = textLocation.getText() + File.separator + textName.getText();
			
			System.out.println("CREATING...");
			System.out.println("name: " + textName.getText());
			System.out.println("location: " + location);
			System.out.println("icon: " + textIcon.getText());
			
			projects.add(new Project(textName.getText(), location, textIcon.getText()));
			fileSystem.save();
			
			view.dispose();
		});
		btnSave.setBounds(348, 181, 156, 28);
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(action -> {
			view.dispose();
		});
		btnCancel.setBounds(10, 181, 106, 28);
		contentPane.add(btnCancel);
		
		JLabel lblProjectTemplate = new JLabel("Project Template");
		lblProjectTemplate.setBounds(10, 96, 98, 14);
		contentPane.add(lblProjectTemplate);
		
		comboBox = new JComboBox(model);
		comboBox.setBounds(146, 89, 358, 28);
		contentPane.add(comboBox);
		
		textIcon = new JTextField();
		textIcon.setColumns(10);
		textIcon.setBounds(146, 129, 242, 28);
		contentPane.add(textIcon);
		
		JLabel lblProjectIconpx = new JLabel("Project Icon (50px)");
		lblProjectIconpx.setBounds(10, 136, 98, 14);
		contentPane.add(lblProjectIconpx);
		
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
		btnChooseIcon.setBounds(398, 129, 106, 28);
		contentPane.add(btnChooseIcon);
		
		setLocationRelativeTo(null);
	}
}
