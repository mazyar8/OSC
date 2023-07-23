package net.osc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import net.osc.network.Packet;

public class Client implements Runnable {
	private String HOST = "localhost";
	private int PORT = 4455;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public Client() {
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			do {
				update();
			} while (true);
		} catch (Exception e) {
			String m = e.getMessage();
			System.out.println(m);
		}
	}
	
	public void update() throws IOException, ClassNotFoundException {
		System.out.println(">");
		String msg = getMessage();
		connect(HOST, PORT);
		writeObject(new Packet(msg));
		System.out.println(((Packet) readObject()).message);
		close();
	}
	
	public void connect(String arg0, int arg1) throws IOException {
		socket = new Socket(arg0, arg1);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	public void close() throws IOException {
		socket.close();
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
	
	public String getMessage() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String msg = "";
		
		while ((msg = br.readLine()) != null) {
			return msg;
		}
		
		return msg;
	}

	public static void main(String[] args) {
		new Client();
	}
	
}
