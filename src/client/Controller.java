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
	private MessageListener listener;
	
	
	public static void main(String[] args) throws InterruptedException {
		try {
			Controller c = new Controller();
			Socket s = new Socket("localhost",8050);
			c.listener = new MessageListener(c, s);
			c.listener.start();
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.println("Bilbo");
			out.flush();
			Thread.sleep(1000);
			out.println("hello");
			out.flush();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void handleMessage(String message) {
		
	}
}

/**
 * Internal listener class for server talk. Separated from server as to avoid having to compile both for just client use
 */
class MessageListener extends Thread {
	private Controller parent;
	private Scanner in;
	
	public MessageListener(Controller parent, Socket connection) throws IOException {
		this.parent = parent;
		this.in = new Scanner (connection.getInputStream());
		
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				if (in.hasNext())
					parent.handleMessage(in.nextLine());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
	}
}

