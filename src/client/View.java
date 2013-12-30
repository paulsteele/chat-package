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
	
	private Model model;
	private JWindow window; 
	private JMenuBar menu;
	private JMenu File;
	private JMenu About;
	private JTextArea textentry;
	private JTextArea chatlog;
	private JButton send;
	private JPanel MainPane;
	
	
	public View(Model model) {
		this.model = model;
		
		
	}
}
