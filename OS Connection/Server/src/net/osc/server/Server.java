package net.osc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import net.osc.network.Packet;

public class Server implements Runnable {
	private ServerSocket ss;
	private int PORT = 4455;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public Server() {
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(PORT);
			System.out.println("server runned on port: " + PORT);
		} catch (IOException e) {
			String m = "its look server is already runned on port: " + PORT;
			System.out.println(m);
			return;
		}
		
		do {
			try {
				update();
			}catch (Exception e) {
				String m = e.getMessage();
				System.out.println(m);
			}
		} while (true);
	}
	
	public void update() throws IOException, ClassNotFoundException {
		String msg = ": )";
		connect(ss.accept());
		System.out.println(((Packet) readObject()).message);
		writeObject(new Packet(msg));
	}
	
	public void connect(Socket socket) throws IOException {
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	public void writeObject(Object o) throws IOException {
		oos.writeObject(o);
	}
	
	public void flush() throws IOException {
		oos.flush();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T readObject() throws IOException, ClassNotFoundException {
		return (T) ois.readObject();
	}
	
	public String getMessage() {
		Scanner s = new Scanner(System.in);
		String msg = "";
		
		if (s.hasNextLine()) {
			msg = s.nextLine();
		}
		
		s.close();
		return msg;
	}

	public static void main(String[] args) {
		new Server();
	}
	
}
