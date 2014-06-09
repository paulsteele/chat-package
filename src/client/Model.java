package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
 * Model for the client for the chat package.
 *
 */
public class Model implements Serializable {
	private static final long serialVersionUID = -7792303764708273767L;
	private String alias;
	private LinkedList<String> friends;
	private String lastHost;
	private int lastPort;
	
	public Model(String alias) {
		this.alias = alias;
	}
	
	public int save() {
		try {
			FileOutputStream fileStream = new FileOutputStream ("User.dat");
			ObjectOutputStream objectStream = new ObjectOutputStream (fileStream);
			objectStream.writeObject(this);
			objectStream.close();
			fileStream.close();
			return 1;
		
		
		} catch (FileNotFoundException e) {
			return 0;
		} catch (IOException e) {
			return 0;
		}
	}
	
	public int load() {
		try {
			FileInputStream fileStream = new FileInputStream ("User.dat");
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			Model m = (Model) objectStream.readObject();
			alias = m.getAlias();
			setLastHost(m.getLastHost());
			setLastPort(m.getLastPort());
			objectStream.close();
			fileStream.close();
			return 1;
			
		} catch (FileNotFoundException e) {
			return 0;
		} catch (IOException e) {
			return 0;
		} catch (ClassNotFoundException e) {
			return 0;
		}
	}
	
	public String getAlias() {
		return alias;
	}
	
	public LinkedList<String> getFriends() {
		return friends;
	}
	
	public String getLastHost() {
		return lastHost;
	}
	
	public int getLastPort() {
		return lastPort;
	}
	
	public void setLastHost(String host) {
		lastHost = host;
	}
	
	public void setLastPort(int port) {
		lastPort = port;
	}
}
