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
	
	public Controller(Model model, View view, String host, int port, int polling) throws UnknownHostException, IOException {
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
		Model m = new Model("Phil");
		Controller c = new Controller(m, null, "localhost", 8050, 1000);
		Thread.sleep(1000);
		c.sendMessage("yolo");
		Thread.sleep(1000);
		c.sendMessage("polo");
		Thread.sleep(1000);
		c.sendMessage("golo");
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
}

/**
 * Internal listener class for server talk. Separated from server as to avoid having to compile both for just client use
 */
class MessageListener extends Thread {
	private Controller parent;
	private Scanner in;
	private int polling;
	
	public MessageListener(Controller parent, Socket connection) throws IOException {
		this.parent = parent;
		this.in = new Scanner (connection.getInputStream());
		this.polling = parent.getPolling();
		
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(polling);
				if (in.hasNext())
					parent.handleMessage(in.nextLine());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
	}
}

