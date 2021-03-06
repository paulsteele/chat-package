package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
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
 * Server for the chat package. Run via command line. Accepts two arguments. The first is the port to listen to, the second is how often
 * in milliseconds that the server checks to see if messages have arrived.
 * Example: 'Sever 8050 1000' will start the server application listening on port 8050 waiting 1 second to process messages. (Both values are
 * default if no numbers are supplied
 *
 */
public class Server {
	private LinkedList<Client> clients;
	private int port;
	private int polling;
	private ConnectionListener connectListen;
	
	public Server(int port, int polling) {
		this.port  = port;
		clients = new LinkedList<Client>();
		this.polling = polling;
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("chat-package Server starting up...");
		
		//get port from command line otherwise defaults to 8050
		int port = 8050;
		if (args.length != 0) {
			try {
				port = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid port number entered, defaulting to port 8050");
			}
		}
		else {
			System.out.println("No port specified, defaulting to port 8050");
		}
		//get polling rate from command line otherwise defaults to 1000
		int polling = 1000;
		if (args.length > 1) {
			try {
				polling = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid polling rate entered, defaulting to 1000ms");
			}
		}
		else {
			System.out.println("No polling rate entered, defaulting to 1000ms");
		}
		
		//These three lines NEED to happen for processes to occur properly
		Server serv = new Server(port, polling);
		serv.setConnectionListener(new ConnectionListener(serv,serv.getPort()));
		serv.getConnectionListener().start();
	}
	
	public LinkedList<Client> getClients() {
		synchronized (this) {
			return clients;
		}
	}
	
	public void removeClient(Client c) {
		synchronized (this) {
			clients.remove(c);
		}
	}
	
	public int getPort() {
		return port;
	}
	
	public int getPolling() {
		return polling;
	}
	
	public void handleMessage(Client client, String message, boolean colon) {
		//Print to console log
		System.out.println(client.getAlias() + (colon ?": ":" ") + message);
		//Send message to all clients
		for (Client c : getClients()) {
			c.getPrintWriter().println(client.getAlias() + (colon ?": ":" ") + message);
			c.getPrintWriter().flush();
		}
	}
	
	public void setConnectionListener(ConnectionListener cl) {
		connectListen = cl;
	}
	
	public ConnectionListener getConnectionListener() {
		return connectListen;
	}
	
	public void end() {
		//end the connectionListener
		connectListen.end();
		//end all MessageListeners
		for (Client c : clients) {
			c.getMessageListener().end();
		}
	}
}
/**
 * Internal Class for the server to keep track of clients.
 * Contains fields for socket connection and an alias.
 */
class Client {
	private Socket link; //The socket connection to the client
	private PrintWriter output; //The PrintWriter for the socket
	private String alias; //the username of the client connected
	private MessageListener listener; //the messagelistener for this particular client
	
	public Client(Socket s, String alias) throws IOException {
		link = s;
		this.alias = alias;
		this.output = new PrintWriter(s.getOutputStream());
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public Socket getSocket() {
		return link;
	}
	
	public PrintWriter getPrintWriter() {
		return output;
	}
	
	public MessageListener getMessageListener() {
		return listener;
	}
	
	public void setMessageListener(MessageListener listener) {
		this.listener = listener;
	}
}


/**
 * Internal listener class for old connections
 */
class MessageListener extends Thread {
	private Server parent;
	private Client client;
	private Scanner in;
	private int polling;
	private boolean keepAlive = true;
	
	public MessageListener(Server parent, Client client, Scanner in) {
		this.setName(client.getAlias() + " listener");
		this.parent = parent;
		this.client = client;
		this.client.setMessageListener(this);
		this.in = in;
		this.polling = parent.getPolling();
		
	}
	
	public void run() {
		while(keepAlive) {
			try {
				parent.handleMessage(client, in.nextLine(), true);
				Thread.sleep(polling);
			} 
			
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			catch (NoSuchElementException e) {
				//Occurs when connection is closed
				parent.handleMessage(this.client, "has left the chatroom.", false);
				parent.removeClient(this.client);
				this.end();
			}
		}
			
	}
	
	public void end() {
		keepAlive = false;
		in.close();
		client.getPrintWriter().close();
	}
}

/**
 * Internal listener class for server to process new connections
 *
 */
class ConnectionListener extends Thread {
	private Server parent;
	private int port;
	private boolean keepAlive = true;
	
	public ConnectionListener (Server parent, int port) {
		this.setName("Connection Listener");
		this.parent = parent;
		this.port = port;
	}
	
	public void run() {
		System.out.println("Waiting for connections on port " + parent.getPort() + " with poll rate of " + parent.getPolling() + "ms");
		try {
			while (keepAlive) {
				ServerSocket ss = new ServerSocket(port);
				Socket s = ss.accept();
				Scanner in = new Scanner(s.getInputStream());
				Thread.sleep(100);
				String name = in.nextLine();
				Client c = new Client (s, name);
				synchronized (parent) {
					parent.getClients().add(c);
				}
				parent.handleMessage(c, "has entered the chatroom", false);
				MessageListener m = new MessageListener(parent, c, in);
				m.start();
				ss.close();
			}
			
		}
		catch (IOException e){
			//retry
			System.out.println("The Server has encountered an error. Retrying...");
			run();
		} 
		catch (InterruptedException e) {
			//retry
			System.out.println("The Server has encountered an error. Retrying...");
			run();
		} 
	}
	
	public Server getParent() {
		return parent;
	}
	
	public int getPort() {
		return port;
	}
	
	public void end() {
		keepAlive = false;
	}
}
