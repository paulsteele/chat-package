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
	
	public static void main(String args) throws IOException{
		ServerSocket connection = new ServerSocket(8888);
		
		
	}
}

class Client {
	private Socket thisSocket;
	
	public Client(Socket s) {
		thisSocket = s;
	}
}