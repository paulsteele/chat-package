package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
	public void begin(Model model, View view, String host, int port, int polling) throws UnknownHostException, IOException {
		this.model = model;
		this.view = view;
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
		System.out.println(message);
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
	
	public void send() {
		
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

