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
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;

import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewLaunch extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ViewLaunch view = this;
	private JLabel lblIcon;
	
	private JPanel contentPane;
	
	private Dimension mainDim = new Dimension(300, 300);
	private Dimension  button = new Dimension(220, 30);
	private Color  background = new Color(247, 247, 247);
	
	public ViewLaunch(ProjectContainer projects, SublimeContainer sublimes, FileSystem fileSystem) {
	
		long timeViewLaunch = System.currentTimeMillis();
		
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
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		JPanel panelProjects = new JPanel();
		panelProjects.setLayout(new BorderLayout());
		panelProjects.setBackground(Color.WHITE);
		contentPane.add(panelProjects, BorderLayout.WEST);
		
		JList list = new JList(projects.getModel());
		list.setPreferredSize(mainDim);
		list.setMinimumSize(mainDim);
		list.setMaximumSize(mainDim);
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
		scrollPane.setPreferredSize(mainDim);
		scrollPane.setMinimumSize(mainDim);
		scrollPane.setMaximumSize(mainDim);
		scrollPane.setBorder(null);
		scrollPane.setViewportView(list);
		
		panelProjects.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelRightBorder = new JPanel();
		panelRightBorder.setBackground(new Color(236, 236, 236));
		panelRightBorder.setPreferredSize(new Dimension(1, 1));
		panelProjects.add(panelRightBorder, BorderLayout.EAST);
		
		JPanel panelContainer = new JPanel();
		panelContainer.setBackground(background);
		contentPane.add(panelContainer, BorderLayout.CENTER);
		
		JPanel panelMain = new JPanel();
		panelContainer.add(panelMain);
		panelMain.setBackground(background);
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(10, 25));
		panelMain.add(rigidArea_2);
		
		JPanel panelActionContainer = new JPanel();
		panelActionContainer.setBackground(background);
		panelMain.add(panelActionContainer);
		panelActionContainer.setLayout(new BorderLayout(0, 0));
		
		JPanel panelLogo = new JPanel();
		panelLogo.setBackground(background);
		panelActionContainer.add(panelLogo, BorderLayout.NORTH);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(background);
		panelActionContainer.add(panelButtons);
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(10, 10));
		panelButtons.add(rigidArea);
		
		JButton btn_new = new JButton("Create New Project");
		btn_new.addActionListener(action -> {
		
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					ViewAddNew van = new ViewAddNew(projects);
					van.setVisible(true);
				}
			});
			
		});
		panelButtons.add(btn_new);
		btn_new.setPreferredSize(button);
		btn_new.setMinimumSize(button);
		btn_new.setMaximumSize(button);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(10, 10));
		panelButtons.add(rigidArea_1);
		
		JButton btn_existing = new JButton("Add Existing Project");
		btn_existing.addActionListener(action -> {
		
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					ViewAddExisting vae = new ViewAddExisting(projects);
					vae.setVisible(true);
				}
			});
			
		});
		panelButtons.add(btn_existing);
		btn_existing.setPreferredSize(button);
		btn_existing.setMinimumSize(button);
		btn_existing.setMaximumSize(button);
		
		setLocationRelativeTo(null);
		
		lblIcon = new JLabel();
		panelLogo.add(lblIcon);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			
				long imageLoadStart = System.currentTimeMillis();
				
				try {
				
					//ImageIcon image = new ImageIcon(ImageIO.read(ViewLaunch.class.getResource("/sublime.png")));
					ImageIcon image = new ImageIcon(ImageIO.read(fileSystem.getSublimeImageLocation()));
					
					EventQueue.invokeLater(new Runnable() {
					
						public void run() {
							lblIcon.setIcon(image);
							lblIcon.repaint();
							setIconImage(image.getImage());
						}
						
					});
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				long imageLoadEnd = System.currentTimeMillis();
				
				System.out.println("IMAGE LOAD: " + (imageLoadEnd - imageLoadStart) + "ms");
				
			}
		}).start();
		
		long timeAfterViewLaunch = System.currentTimeMillis();
		System.out.println("Took " + (timeAfterViewLaunch - timeViewLaunch) + " for ViewLaunch -> ViewLaunch Done");
	}

}