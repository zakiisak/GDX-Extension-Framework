package game.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class NetServer extends Listener {
	
	public NetServer() {}

	public Server server;
	
	public boolean start(int tcpPort, int udpPort) {
		this.server = new Server(Network.PACKET_BUFFER_SIZE, Network.PACKET_BUFFER_SIZE);
		Network.register(this.server.getKryo());
		try {
			this.server.bind(tcpPort, udpPort);
		} catch (Exception e) {
			System.out.println(
					"Unable to start server on ports: [tcpPort=" + tcpPort
							+ ";udpPort=" + udpPort + "] - \n" + e.getMessage());
			return false;
		}
		this.server.start();
		this.server.addListener(this);
		return true;
	}
	
	
	public void connected(Connection c) {
		System.out.println("client connected: " + c.getID());
	}

	public void received(Connection c, Object o) {
		System.out.println("received packet: " + o);
	}

	public void disconnected(Connection c) {
		System.out.println("client disconnected: " + c.getID());
	}
	
	public Server getCommunication() {
		return server;
	}

	public void stop() {
		if(server != null) 
		{
			server.stop();
			server = null;
		}
	}
	
}
