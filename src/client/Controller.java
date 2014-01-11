package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * 
 * @author Paul Steele
 * Author website : paul-steele.com
 * Project page : https://github.com/paulsteele/chat-package
 * 
 * This file is protected under a Creative Commons Attribution-NonCommercial 3.0 Unported License
 * For further information see paul-steele.com/Pages/license.php
 * 
 * Controller for the client for the chat package.
 *
 */
public class Controller {
	private Model model;
	private View view;
	private MessageListener listener;
	private Socket connection;
	private int polling;
	private int port;
	private PrintWriter out;
	
	//Actually starts the TCP connection
	public void begin(Model model, String host, int port, int polling) throws UnknownHostException, IOException {
		this.model = model;
		this.polling = polling;
		this.port = port;
		this.connection = new Socket(host, this.port);
		this.listener = new MessageListener(this, connection);
		this.out = new PrintWriter(connection.getOutputStream());
		//Send server the user alias and flush the data
		out.println(this.model.getAlias());
		out.flush();
		//Start the message listener once everything else is done
		this.listener.start();
	}
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		Controller master = new Controller();
		master.setView(new View(master));
	}
	
	public void sendMessage(String message) {
		out.println(message);
		out.flush();
	}
	
	public void handleMessage(String message) {
		getView().appendChatlog(message);
	}
	
	public int getPolling() {
		return polling;
	}
	
	public View getView() {
		return view;
	}
	
	public void setView(View v) {
		view = v;
	}

	
	public void connect() {
		view.createConnectionWindow();
	}
	
	public void exit() {
		view.end();
		try {
			end();
		}
		catch (NullPointerException e){
			//Means that the client quit before connection every set up. Suppress error
		}
		
	}
	
	public void connectionEntered() {
		
		try {
			
			//Obtains info from the GUI
			
			String username = view.getConnectionWindow().getUsername();
			if (username == null || username.equals(""))
				throw new InvalidUsernameException();
			
			String hostname = view.getConnectionWindow().getHostname();
			if (hostname == null || hostname.equals(""))
				throw new InvalidHostnameException();
			
			int port;
			
			try {
				port = Integer.parseInt(view.getConnectionWindow().getPort());
			}
			catch (NumberFormatException e){
				throw new InvalidPortException();
			}
			
			if (port < 1 || port > 65535) {
				throw new InvalidPortException();
			}
			
			//DO THE CONNECTING
			Model m = new Model(username);
			this.begin(m, hostname, port, 1000);
			getView().getConnectionWindow().end();
			
		}
		//Gui error catchers
		catch (InvalidUsernameException e) {
			JOptionPane.showMessageDialog(null, "Invalid username entered. Try again.", "Username Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (InvalidHostnameException e) {
			JOptionPane.showMessageDialog(null, "Invalid hostname entered. Try again.", "Hostname Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (InvalidPortException e) {
			JOptionPane.showMessageDialog(null, "Invalid port entered. Try again.", "Port Error", JOptionPane.ERROR_MESSAGE);
		}
		//Connecting Error catches
		catch (UnknownHostException e){
			JOptionPane.showMessageDialog(null, "Could not connect to host. Make sure the hostname is entered correctly.",
					"Error connecting to host", JOptionPane.ERROR_MESSAGE);
		}
		catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Connection refused by host. Make sure a server is running on specified machine", 
					"Connection Refused", JOptionPane.ERROR_MESSAGE);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void send() {
		sendMessage(view.getTextEntry());
		view.setTextEntry("");
	}
	
	public void end() {
		try {
		connection.close();
		}
		catch (IOException e) {
			//Haven't decided what to do here yet
		}
		out.close();
		listener.end();
	}
}

/**
 * Internal listener class for server talk. Separated from server as to avoid having to compile both for just client use
 */
class MessageListener extends Thread {
	private Controller parent;
	private Scanner in;
	private int polling;
	private boolean keepAlive= true;
	
	public MessageListener(Controller parent, Socket connection) throws IOException {
		this.setName("Message Listener");
		this.parent = parent;
		this.in = new Scanner (connection.getInputStream());
		this.polling = parent.getPolling();
		
	}
	
	public void run() {
		while(keepAlive) {
			try {
				if (in.hasNext())
					parent.handleMessage(in.nextLine());
				Thread.sleep(polling);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void end() {
		keepAlive = false;
		in.close();
	}
}

/**
 * Exception classes for conditional messages while connecting
 */
class InvalidUsernameException extends RuntimeException {
	private static final long serialVersionUID = -1284665088338949067L;
	
}

class InvalidHostnameException extends RuntimeException {
	private static final long serialVersionUID = 461853824304305724L;
	
}

class InvalidPortException extends RuntimeException {
	private static final long serialVersionUID = -1648488952976590056L;
	
}
