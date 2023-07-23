package net.osc.network;

import java.io.Serializable;

public class Packet implements Serializable {
	private static final long serialVersionUID = 1L;
	public String message;
	
	public Packet(String message) {
		this.message = message;
	}
	
}
