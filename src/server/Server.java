package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
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
 * Server for the chat package. Run via command line.
 *
 */
public class Server {
	private LinkedList<Client> clients;
	private int port;
	
	public Server(int port) {
		this.port  = port;
		clients = new LinkedList<Client>();
	}
	
	public static void main(String[] args) throws InterruptedException {
		Server serv = new Server(8888);
		ConnectionListener connectListen = new ConnectionListener(serv,serv.getPort());
		connectListen.start();
	}
	
	public LinkedList<Client> getClients() {
		synchronized (this) {
			return clients;
		}
	}
	
	public int getPort() {
		return port;
	}
	
	public void handleMessage(Client client, String message) {
		System.out.println(client.getAlias() + ": " + message);
	}
}
/**
 * Internal Class for the server to keep track of clients.
 * Contains fields for socket connection and an alias.
 */
class Client {
	private Socket link; //The socket connection to the client
	private String alias; //the username of the client connected
	
	public Client(Socket s, String alias) {
		link = s;
		this.alias = alias;
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
}


/**
 * Internal listener class for old connections
 */
class MessageListener extends Thread {
	private Server parent;
	private Client client;
	private Socket socket;
	private Scanner in;
	public MessageListener(Server parent, Client client, Scanner in) {
		this.parent = parent;
		this.client = client;
		this.socket = client.getSocket();
		this.in = in;
		
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				if (in.hasNext())
					parent.handleMessage(client, in.nextLine());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
	}
}

/**
 * Internal listener class for server to process new connections
 *
 */
class ConnectionListener extends Thread {
	private Server parent;
	private int port;
	
	public ConnectionListener (Server parent, int port) {
		this.parent = parent;
		this.port = port;
	}
	
	public void run() {
		
		try {
			while (true) {
				ServerSocket ss = new ServerSocket(port);
				Socket s = ss.accept();
				Scanner in = new Scanner(s.getInputStream());
				Thread.sleep(100);
				String name = in.nextLine();
				Client c = new Client (s, name);
				synchronized (parent) {
					parent.getClients().add(c);
				}
				MessageListener m = new MessageListener(parent, c, in);
				m.start();
				ss.close();
			}
			
		}
		catch (IOException e){
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	public Server getParent() {
		return parent;
	}
	
	public int getPort() {
		return port;
	}
}
