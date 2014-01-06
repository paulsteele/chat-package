package client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
	
	//Variabes needed as references to outside classes
	private Model model;
	private Controller controller;
	private EventListener eventListener;
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
	//External Windows for obtaining information
	private ConnectionWindow connectionWindow;

	
	
	public View(Controller controller) {
		this.controller = controller;
		eventListener = new EventListener(this);
		//set up Window
		window = new JFrame("chat-package client");
		//set up Menubar
		menu = new JMenuBar();
		//File
		file = new JMenu("File");
		connect = new JMenuItem("Connect");
		connect.setActionCommand("connect");
		connect.addActionListener(eventListener);
		file.add(connect);
		exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(eventListener);
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
		send.setActionCommand("send");
		send.addActionListener(eventListener);
		
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
	
	public Model getModel() {
		return model;
	}
	
	public void setModel(Model model) {
		this.model = model;
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
	
	public Controller getController () {
		return controller;
	}
	
	public void end() {
		window.dispose();
	}
	
	public void createConnectionWindow(){
		connectionWindow = new ConnectionWindow();
		connectionWindow.setActionListener(eventListener);
		
	}
	
	public ConnectionWindow getConnectionWindow() {
		return connectionWindow;
	}
	
	public boolean checkAlive() {
		return window.isShowing();
	}
}
/**
 * Universal EventListener for GUI events, uses switch and compares to ActionEvent.getActionCommand()
 *
 */
class EventListener implements ActionListener {
	private View view;
	
	EventListener(View view){
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()){
		case "connect" :
			view.getController().connect();
			break;
		case "connectionEntered" :
			view.getController().connectionEntered();
			break;
		case "exit" : 
			view.getController().exit();
			break;
		case "send" :
			view.getController().send();
			break;
		default: 
		}
		
	}
	
}

/**
 * Connection window
 */

class ConnectionWindow {
	//Main window
	private JFrame window;
	private JPanel mainPanel;
	//username
	private JPanel usernamePanel;
	private JLabel usernameLabel;
	private JTextField username;
	//hostname
	private JPanel hostnamePanel;
	private JLabel hostnameLabel;
	private JTextField hostname;
	//port
	private JPanel portPanel;
	private JLabel portLabel;
	private JTextField port;
	//Buttons
	private JButton confirm;
	
	public ConnectionWindow() {
		//Button setup
		confirm = new JButton("Confirm");
		//username setup
		usernamePanel = new JPanel();
		usernameLabel = new JLabel("Username: ");
		username = new JTextField();
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(username);
		//hostname setup
		hostnamePanel = new JPanel();
		hostnameLabel = new JLabel("Hostname: ");
		hostname = new JTextField();
		hostnamePanel.setLayout(new BoxLayout(hostnamePanel, BoxLayout.X_AXIS));
		hostnamePanel.add(hostnameLabel);
		hostnamePanel.add(hostname);
		//port setup
		portPanel = new JPanel(); 
		portLabel = new JLabel("Port: ");
		port = new JTextField();
		portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
		portPanel.add(portLabel);
		portPanel.add(port);
		portPanel.add(confirm);
		//Main panel setup
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(usernamePanel);
		mainPanel.add(hostnamePanel);
		mainPanel.add(portPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		window = new JFrame("Connect");
		window.setMinimumSize(new Dimension(300,130));
		window.setResizable(false);
		window.add(mainPanel);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setVisible(true);
		
	
	}
	
	public String getUsername() {
		return username.getText();
	}

	public String getHostname() {
		return hostname.getText();
	}
	
	public String getPort() {
		return port.getText();
	}
	
	public void setActionListener(ActionListener al){
		confirm.setActionCommand("connectionEntered");
		confirm.addActionListener(al);
	}
	
	public void end() {
		window.dispose();
	}
	
}
