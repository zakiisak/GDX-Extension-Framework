package game.net;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import game.Game;
import game.GameRunnable;
import game.utils.Tag;

public class Network {
	public static final short NetworkPort = 7677;
	public static final int PACKET_BUFFER_SIZE = 8192 * 128;

	public static NetClient client;
	public static NetServer server;

	private static boolean stopClient;
	private static List<Object> packetQueueTCP = new ArrayList<Object>();
	private static List<Object> packetQueueUDP = new ArrayList<Object>();
	static List<GameRunnable> runOnMainThread = new ArrayList<GameRunnable>();

	/***
	 *
	 * @return whether any activity is going at all over the network.
	 */
	public static boolean isNetworkGoing() {
		if (client != null)
			return client.isConnected();
		return server != null;
	}

	public static void flushClientPackets() {
		for (Object tcpPacket : packetQueueTCP) {
			client.getCommunication().sendTCP(tcpPacket);
		}

		for (Object udpPacket : packetQueueUDP) {
			client.getCommunication().sendUDP(udpPacket);
		}
		packetQueueTCP.clear();
		packetQueueUDP.clear();
	}


	public static void register(Kryo kryo) {
		kryo.register(Tag.class);
		kryo.register(Map.class);
		kryo.register(HashMap.class);
		kryo.register(float[].class);
		kryo.register(int[].class);
		kryo.register(long[].class);
		kryo.register(boolean[].class);
		kryo.register(double[].class);
		kryo.register(short[].class);
		kryo.register(byte[].class);
		kryo.register(byte[][].class);

	}

	public static void sendTCPPacket(Object packet) {
		packetQueueTCP.add(packet);
	}

	public static void sendUDPPacket(Object packet) {
		packetQueueUDP.add(packet);
	}

	public static boolean isServerStarted() {
		return server != null;
	}

	public static boolean isClientConnected() {
		return client != null && stopClient == false && client.isConnected();
	}

	public static interface ClientConnectCallback {
		public void status(boolean connected);
	}

	public static void beginClient(final String ip, final ClientConnectCallback callback) {
		Log.set(Log.LEVEL_INFO);
		if (client == null)
			client = new NetClient();
		else
			client.stop();
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean connected = client.connect(ip, NetworkPort, NetworkPort);
				callback.status(connected);
				if (connected == false)
					stopClient = true;
			}
		}).start();
	}

	public static interface ServerStartCallback {
		public void serverStartedStatus(boolean started);
	}

	public static void beginServer(final ServerStartCallback callback) {
		Log.set(Log.LEVEL_INFO);
		if (server == null)
			server = new NetServer();
		else
			server.stop();

		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean started = server.start(NetworkPort, NetworkPort);
				if (callback != null)
					callback.serverStartedStatus(started);
			}
		}).start();
	}

	public static void stopServer() {
		if (server != null) {
			server.stop();
			server = null;
		}
	}

	public static void stopClient() {
		if (client != null) {
			client.stop();
			client = null;
		}
	}

	public static InetAddress scanForFirstServer(short port, int timeoutMillis) {
		return new Client().discoverHost(port, timeoutMillis);
	}

	public static void dispose() {
		if (client != null) {
			client.stop();
			client = null;
		}
		if (server != null) {
			server.stop();
			server = null;
		}
	}
}
