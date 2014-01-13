package client;

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
	
	public void save() {
		
	}
	
	public void load() {
		
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
