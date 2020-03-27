package Server;

import Databases.IDataBase;
import Databases.UserDatabase;
import Server.AdminUI.AdminClient;
import Utils.ClientHandler;
import Utils.INetworkHelper;
import Utils.ServerNetworkHelper;
import javafx.application.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server
{

	// -- the port number used for client communication
	private static final int PORT = 8000;
	// -- assign each client connection an ID. Just increment for now
	private int nextId = 0;
	// -- the socket that waits for client connections
	private ServerSocket serversocket;
	// -- list of active client threads by ID number
	private Map<Integer, ClientHandler> clientConnections;
	private final INetworkHelper networkHelper;
	private final IDataBase userDB;
	//private final IDataBase systemDB;
	private Thread listenThread;
	public Server()
	{

		// -- construct the list of active client threads
		clientConnections = new ConcurrentHashMap();
		networkHelper = new ServerNetworkHelper();
		userDB = new UserDatabase();
	}

	// NetworkHandler listens on each clients ports for messages coming in

	public static void main(String[] args)
	{
		// -- instantiate the server anonymously
		//    no need to keep a reference to the object since it will run in its own thread
		new Server().startListening();

		Application.launch(AdminClient.class, args);

	}

	public void startListening()
	{
		try
		{
			serversocket = new ServerSocket(PORT);
			System.out.println("SERVER SOCKET MADE");
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		listenThread = new Thread(() -> {
			// -- server runs until we manually shut it down
			try
			{

				while (true)
				{
					// -- block until a client comes along
					try
					{
						System.out.println("Attempting to accept");
						Socket socket = serversocket.accept();
						System.out.println("Client accepted");
						// -- connection accepted, create a peer-to-peer socket
						//    between the server (thread) and client
						peerConnection(socket);
					} catch (IOException e)
					{
						e.printStackTrace();
					}

					Thread.sleep(0);
				}

			}catch (InterruptedException ex)
			{
				return;
			}
		});

		listenThread.start();
	}

	public int getPort()
	{
		return PORT;
	}

	public void peerConnection(Socket socket)
	{
		System.out.println("PEER CONNECTION CALLED");

		try
		{
			// -- when a client arrives, create a thread for their communication
			ClientHandler connection = new ClientHandler(nextId, socket, this, userDB, networkHelper);

			System.out.println("AFTER MAKING CONNECTION");

			// -- add the thread to the active client threads map
			clientConnections.put(nextId, connection);

			System.out.println("CLIENT CONNECTION ADDED");
			// -- start the thread
			new Thread(connection).start();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
		}

		System.out.println("STARTED PEER");

		// -- place some text in the area to let the server operator know
		//    what is going on
		System.out.println("SERVER: connection received for id " + nextId + "\n");
		nextId++;
	}

	// -- called by a ServerThread when a client is terminated
	public void removeID(int id)
	{
		// -- find the object belonging to the client thread being terminated

		// -- remove it from the active threads list
		//    the thread will terminate itself
		clientConnections.remove(id);

		// -- place some text in the area to let the server operator know
		//    what is going on
		System.out.println("SERVER: connection closed for client id " + id + "\n");

	}

	/*private void listen()
	{
		try
		{
			// -- open the server socket
			serversocket = new ServerSocket(getPort());

			// -- server runs until we manually shut it down
			while (true)
			{
				// -- block until a client comes along
				Socket socket = serversocket.accept();

				// -- connection accepted, create a peer-to-peer socket
				//    between the server (thread) and client
				peerConnection(socket);

			}
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);

		}
	}*/

}
