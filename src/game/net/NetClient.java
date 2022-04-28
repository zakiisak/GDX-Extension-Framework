package game.net;

import java.net.InetAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetClient extends Listener {
	private boolean connected = false;
	private Client client;
	
	public NetClient() {}

	public void stop()
	{
		if(client != null) 
		{
			client.stop();
			client = null;
		}
	}
	
	public void connected(Connection c) {
		connected = true;
	}
	

	public void received(Connection c, Object o) {
		System.out.println("received packet from server: " + o);
	}
	
	public void disconnected(Connection c) {
		connected = false;
	}
	
	public boolean connect(String ip, int tcpPort, int udpPort) {
		int maxSize = Network.PACKET_BUFFER_SIZE;
		this.client = new Client(maxSize, maxSize);
		Network.register(this.client.getKryo());
		this.client.addListener(this);
		this.client.start();
		
		try {
			this.client.connect(1000, ip, tcpPort, udpPort);
		}
		catch(Exception e) {
			this.client = null;
			return false;
		}
		return true;
	}
	
	public InetAddress findFirstServer(short udpPort, int waitingResponse)
	{
		if(this.client == null) return null;
		return client.discoverHost(udpPort, waitingResponse);
	}
	
	public List<InetAddress> scanForServers(short udpPort, int waitingResponse)
	{
		if(this.client == null) return null;
		return client.discoverHosts(udpPort, waitingResponse);
	}
	
	public Client getCommunication()
	{
		return client;
	}
	
	public boolean isConnected()
	{
		if(this.client == null) connected = false;
		return connected;
	}

	public int getNetId()
	{
		return client.getID();
	}
	
}
