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
	
	public static void main(String[] args) throws IOException{
		ServerSocket connection = new ServerSocket(8888);
		connection.accept();
		
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