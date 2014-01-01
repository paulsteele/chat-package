package client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 * @author Paul Steele
 * Author website : paul-steele.com
 * Project page : https://github.com/paulsteele/chat-package
 * 
 * This file is protected under a Creative Commons Attribution-NonCommercial 3.0 Unported License
 * For further information see paul-steele.com/Pages/license.php
 * 
 * View for the client for the chat package.
 *
 */
public class View {
	
	//Variabes needed as refrences to outside classes
	private Model model;
	//Components for Main Window
	private JFrame window; 
	private JPanel pane;
	private GridBagConstraints paneConstraints;
	//Components for Menu
	private JMenuBar menu;
	//File Menu
	private JMenu file;
	private JMenuItem connect;
	private JMenuItem exit;
	//About Menu
	private JMenu about;
	//Components for Text entry
	private JScrollPane textEntryPane;
	private JTextArea textEntry;
	private JButton send;
	//Components for Text viewing
	private JScrollPane chatlogPane;
	private JTextArea chatlog;
	

	
	
	public View(Model model) {
		this.model = model;
		//set up Window
		window = new JFrame("chat-package client");
		//set up Menubar
		menu = new JMenuBar();
		//File
		file = new JMenu("File");
		connect = new JMenuItem("Connect");
		exit = new JMenuItem("Exit");
		file.add(exit);
		//About
		about = new JMenu("About");
		//Add menus to bar
		menu.add(file);
		menu.add(about);
		//set up GridBagLayout
		pane = new JPanel(new GridBagLayout());
		paneConstraints = new GridBagConstraints();
		paneConstraints.gridheight = 2;
		paneConstraints.gridwidth = 2;
		//set up chatlog
		chatlog = new JTextArea();
		chatlog.setLineWrap(true);
		chatlog.setEditable(false);
		chatlogPane = new JScrollPane();
		chatlogPane.setViewportView(chatlog);
		//set up textEntry
		textEntry = new JTextArea();
		textEntryPane = new JScrollPane();
		textEntryPane.setViewportView(textEntry);
		//set up send button
		send = new JButton("Send");
		
		//add components to pane setting up GridBagConsraints as needed
		paneConstraints.gridx = 0;
		paneConstraints.gridy = 0;
		paneConstraints.gridheight = 1;
		paneConstraints.gridwidth = GridBagConstraints.REMAINDER;
		paneConstraints.weightx = .5;
		paneConstraints.weighty = .9;
		paneConstraints.fill = GridBagConstraints.BOTH;
		pane.add(chatlogPane, paneConstraints);
		paneConstraints.gridy = 1;
		paneConstraints.gridwidth = 1;
		paneConstraints.weighty = .1;
		paneConstraints.weightx = 1;
		pane.add(textEntryPane, paneConstraints);
		paneConstraints.weightx = 0;
		paneConstraints.gridx = 2;
		paneConstraints.fill = GridBagConstraints.VERTICAL;
		paneConstraints.anchor = GridBagConstraints.EAST;
		pane.add(send, paneConstraints);
		
		//add menu bar to window
		window.setJMenuBar(menu);
		//add pane to window and display window
		window.add(pane);
		window.setMinimumSize(new Dimension(500, 500));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setVisible(true);
	}
	
	public String getTextEntry() {
		return textEntry.getText();
	}
	
	public void setTextEntry(String s) {
		textEntry.setText(s);
	}
	
	public void appendChatlog(String s) {
		chatlog.append(s);
	}
	
	public void clearChatlog() {
		chatlog.setText("");
	}
}
