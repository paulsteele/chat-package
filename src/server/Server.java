package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

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
	}
	
	public static void main(String[] args) {
		Server serv = new Server(8888);
		ConnectionListener connectListen = new ConnectionListener(serv,serv.getPort());
		connectListen.start();
	}
	
	public LinkedList<Client> getClients() {
		return clients;
	}
	
	public int getPort() {
		return port;
	}
}
/**
 * Internal Class for the server to keep track of clients.
 * Contains fields for socket connection and an alias.
 */
class Client {
	private Socket link; //The socket connection to the client
	private String alias; //the username of the client connected
	
	public Client(Socket s) {
		link = s;
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
			ServerSocket ss = new ServerSocket(port);
			while (true) {
				System.out.print("ConnecitonListener waiting...");
				Socket s = ss.accept();
				synchronized (parent) {
				parent.getClients().add(new Client (s));
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
