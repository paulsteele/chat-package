package client;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
	
	Model model;
	JWindow window; 
	JMenuBar menu;
	JMenu File;
	JMenu About;
	JTextArea textbar;
	JTextArea chatlog;
	JButton send;
	JPanel MainPane;
	
	
	public View(Model model) {
		this.model = model;
		
		
	}
}
