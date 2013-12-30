package client;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;

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
	private JWindow window; 
	private JPanel MainPane;
	//Components for Menu
	private JMenuBar menu;
	//File Menu
	private JMenu File;
	private JMenuItem exit;
	//About Menu
	private JMenu About;
	//Components for Text entry
	private JTextArea textentry;
	private JButton send;
	//Components for Text viewing
	private JTextArea chatlog;
	

	
	
	public View(Model model) {
		this.model = model;
		
		
	}
}
