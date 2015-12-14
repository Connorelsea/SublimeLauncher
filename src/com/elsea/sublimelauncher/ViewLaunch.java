package com.elsea.sublimelauncher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.event.ListDataListener;

import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class ViewLaunch extends JFrame {
	
	private ProjectContainer projects;
	private SublimeContainer sublimes;
	private FileSystem       fileSystem;
	private ViewLaunch       view = this;
	
	private JPanel contentPane;
	private DefaultListModel<Project> model;
	
	public ViewLaunch(ProjectContainer projects, SublimeContainer sublimes, FileSystem fileSystem) {
		
		this.projects   = projects;
		this.sublimes   = sublimes;
		this.fileSystem = fileSystem;
		
		model = projects.getModel();
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 433);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelProjects = new JPanel();
		panelProjects.setLayout(new BorderLayout());
		panelProjects.setBackground(Color.WHITE);
		contentPane.add(panelProjects, BorderLayout.WEST);
		
		JList list = new JList(model);
		list.setPreferredSize(new Dimension(300, 300));
		list.setMinimumSize(new Dimension(300, 300));
		list.setMaximumSize(new Dimension(300, 300));
		list.setCellRenderer(new FileListCellRenderer());
		list.setBackground(Color.WHITE);
		
		list.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				@SuppressWarnings("unchecked")
				JList<Project> source = (JList<Project>) e.getSource();
				
				// Launch project on double click
				if (e.getClickCount() == 2) {
					int index = list.locationToIndex(e.getPoint());
					Project p = source.getModel().getElementAt(index);
					sublimes.getLocation(0).load(p);
					view.dispose();
				}
				
			}
			
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(300, 300));
		scrollPane.setMinimumSize(new Dimension(300, 300));
		scrollPane.setMaximumSize(new Dimension(300, 300));
		scrollPane.setBorder(null);
		scrollPane.setViewportView(list);
		
		panelProjects.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelRightBorder = new JPanel();
		panelRightBorder.setBackground(new Color(236, 236, 236));
		panelRightBorder.setPreferredSize(new Dimension(1, 1));
		panelProjects.add(panelRightBorder, BorderLayout.EAST);
		
		JPanel panelContainer = new JPanel();
		panelContainer.setBackground(new Color(247, 247, 247));
		contentPane.add(panelContainer, BorderLayout.CENTER);
		
		JPanel panelMain = new JPanel();
		panelContainer.add(panelMain);
		panelMain.setBackground(new Color(247, 247, 247));
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(10, 25));
		panelMain.add(rigidArea_2);
		
		JPanel panelActionContainer = new JPanel();
		panelActionContainer.setBackground(new Color(247, 247, 247));
		panelMain.add(panelActionContainer);
		panelActionContainer.setLayout(new BorderLayout(0, 0));
		
		JPanel panelLogo = new JPanel();
		panelLogo.setBackground(new Color(247, 247, 247));
		panelActionContainer.add(panelLogo, BorderLayout.NORTH);
		
		BufferedImage bi = null;
		ImageIcon image  = null;
		
		try {
			bi = ImageIO.read(ViewLaunch.class.getResource("/sublime.png"));
			image = new ImageIcon(bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setIconImage(image.getImage());
		
		JLabel lblNewLabel = new JLabel(image);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelLogo.add(lblNewLabel);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(new Color(247, 247, 247));
		panelActionContainer.add(panelButtons);
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(10, 10));
		panelButtons.add(rigidArea);
		
		JButton btn_new = new JButton("Create New Project");
		btn_new.addActionListener(action -> {
			
			ViewAddNew van = new ViewAddNew(projects);
			van.setVisible(true);
			
		});
		panelButtons.add(btn_new);
		btn_new.setPreferredSize(new Dimension(220, 30));
		btn_new.setMinimumSize(new Dimension(220, 30));
		btn_new.setMaximumSize(new Dimension(220, 30));
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(10, 10));
		panelButtons.add(rigidArea_1);
		
		JButton btn_existing = new JButton("Add Existing Project");
		btn_existing.addActionListener(action -> {
			
			ViewAddExisting vae = new ViewAddExisting(projects);
			vae.setVisible(true);
			
		});
		panelButtons.add(btn_existing);
		btn_existing.setPreferredSize(new Dimension(220, 30));
		btn_existing.setMinimumSize(new Dimension(220, 30));
		btn_existing.setMaximumSize(new Dimension(220, 30));
		
		setLocationRelativeTo(null);
	}

}