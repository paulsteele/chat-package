package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost",8888);
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.print("Bilbo");
			out.flush();
			out.print("hello");
			out.flush();
			while(true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
