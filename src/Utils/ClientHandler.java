package Utils;

import Databases.IDataBase;
import NetworkMessages.ClientDisplayMessage;
import NetworkMessages.DisconnectMessage;
import NetworkMessages.IMessage;
import NetworkMessages.ServerMessage;
import Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	private static final Protocol protocol = new Protocol();
	private String name;
	private int id;

	// -- the main server (port listener) will supply the socket
	//    the thread (this class) will provide the I/O streams
	//    BufferedReader is used because it handles String objects
	//    whereas DataInputStream does not (primitive types only)
	private ObjectInputStream datain;
	private ObjectOutputStream dataout;

	// -- this is a reference to the "parent" Server object
	//    it will be set at time of construction
	private Server server;
	private IDataBase db;
	private INetworkHelper serverNetworkHelper;

	public ClientHandler(int id, Socket socket, Server server, IDataBase db, INetworkHelper serverNetworkHelper)
	{
		this.server = server;
		this.id = id;
		this.name = Integer.toString(id);
		this.db = db;
		this.serverNetworkHelper = serverNetworkHelper;
		try
		{
			System.out.println(socket.getInputStream().available());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// -- create the stream I/O objects on top of the socket
		try
		{
			dataout = new ObjectOutputStream(socket.getOutputStream());
			datain = new ObjectInputStream(socket.getInputStream());
		} catch (Throwable e)
		{
			e.printStackTrace();
			System.exit(1);
		}

	}

	public String toString()
	{
		return name;
	}

	@Override public void run()
	{
		while(true)
		{
			try
			{
				// getMessage on a serverNetworkHandler? or rather, take from a queue that a serverNetworkHandler
				// puts on
				ServerMessage msg = (ServerMessage) serverNetworkHelper.getMessage(datain);

				System.out.println("Got a message");

				if (msg instanceof DisconnectMessage)
				{
					serverNetworkHelper.sendMessage(dataout, protocol.genSuccess("Disconnect Successful"));
					datain.close();
					server.removeID(id);
					return;
				}

				// TODO : MAKE THIS A CLIENTDISPLAY MESSAGE IF GET WORKING
				IMessage returnMsg = msg.perform(db, protocol);
				System.out.println(returnMsg.getClass());
				serverNetworkHelper.sendMessage(dataout, returnMsg);

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

}
