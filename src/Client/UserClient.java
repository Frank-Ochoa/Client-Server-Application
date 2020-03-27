package Client;

import NetworkMessages.ClientDisplayMessage;
import NetworkMessages.DisconnectMessage;
import NetworkMessages.LoginMessage;
import NetworkMessages.LogoutMessage;
import NetworkMessages.ServerMessage;
import NetworkMessages.SuccessMessage;
import Utils.ClientNetworkHelper;
import Utils.INetworkHelper;
import Utils.Protocol;
import Utils.StatusFromMain;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserClient extends Application
{
	private static final int PORT = 8000;
	private final INetworkHelper clientNetworkHelper = new ClientNetworkHelper();
	private final Protocol protocol = new Protocol();
	private final ClientInterface clientInterface = new ClientInterface(protocol);
	private String HOST;
	private Socket socket;

	private ObjectInputStream datain;
	private ObjectOutputStream dataout;

	private String currUser;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override public void start(Stage primaryStage) throws Exception
	{
		startGUI();
	}

	private void startGUI()
	{
		// Attempt to connect
		if (connected())
		{
			// Start Login
			startLogin();
		}
	}

	private void startLogin()
	{
		if (loggedIn())
		{
			handleMainFunctionality();
		}
	}

	private void handleMainFunctionality()
	{
		StatusFromMain status = mainFunctionality(currUser);

		while (status == StatusFromMain.KEEP_RUNNING)
		{
			System.out.println("KEEP RUNNING");
			status = mainFunctionality(currUser);
		}

		if (status == StatusFromMain.DISCONNECT)
		{
			startGUI();
		}
		else
		{
			startLogin();
		}

	}

	private StatusFromMain mainFunctionality(String currUser)
	{
		ServerMessage serverMessage = clientInterface.mainScreen(currUser);

		checkIfClientClosed(serverMessage, "Client closed. You will be logged out and disconnected.");

		if (serverMessage instanceof DisconnectMessage)
		{
			clientNetworkHelper.sendMessage(dataout, protocol.genLogout(currUser));
			ClientDisplayMessage returnMessage = (ClientDisplayMessage) clientNetworkHelper.getMessage(datain);
			returnMessage.displayState(clientInterface);
		}

		clientNetworkHelper.sendMessage(dataout, serverMessage);
		ClientDisplayMessage returnMessage = (ClientDisplayMessage) clientNetworkHelper.getMessage(datain);

		returnMessage.displayState(clientInterface);

		if (serverMessage instanceof DisconnectMessage)
		{
			return StatusFromMain.DISCONNECT;
		}
		else if (serverMessage instanceof LogoutMessage && returnMessage instanceof SuccessMessage)
		{
			return StatusFromMain.LOGOUT;
		}
		else
		{
			return StatusFromMain.KEEP_RUNNING;
		}
	}

	private boolean loggedIn()
	{
		while (!tryLogin())
		{

		}

		return true;
	}

	private boolean tryLogin()
	{
		try
		{
			Pair<ServerMessage, String> loginAttemptInfo = clientInterface.tryLogin();
			// Check if they clicked close
			ServerMessage serverMessage = loginAttemptInfo.getKey();
			checkIfClientClosed(serverMessage, "Client closed. You will be disconnected.");

			clientNetworkHelper.sendMessage(dataout, serverMessage);
			ClientDisplayMessage returnMessage = (ClientDisplayMessage) clientNetworkHelper.getMessage(datain);

			returnMessage.displayState(clientInterface);

			if (serverMessage instanceof LoginMessage && returnMessage instanceof SuccessMessage)
			{
				this.currUser = loginAttemptInfo.getValue();
				return true;
			}
			else if (serverMessage instanceof DisconnectMessage)
			{
				startGUI();
				return false;
			}
			else
			{
				return false;
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	private boolean connected()
	{
		Pair<String, Boolean> connectInfo = clientInterface.connect();
		//HOST = clientInterface.connect();
		HOST = connectInfo.getKey();
		System.out.println(HOST);

		try
		{
			if (HOST == null || HOST.equals(" "))
			{
				throw new UnknownHostException();
			}

			socket = new Socket(HOST, PORT);
			datain = new ObjectInputStream(socket.getInputStream());
			dataout = new ObjectOutputStream(socket.getOutputStream());

			clientNetworkHelper.sendMessage(dataout, protocol.genConnect());
			ClientDisplayMessage msg = (ClientDisplayMessage) clientNetworkHelper.getMessage(datain);
			// Displays state, state then closes connect if successful, maybe this returns a boolean?

			// This can only be success, this will call a showSuccess or showFail in the interface
			msg.displayState(clientInterface);
			return true;

		} catch (IOException e)
		{
			// Fail display down here
			if (connectInfo.getValue())
			{
				System.out.println("Host " + HOST + " at port " + PORT + " is unavailable.");
				clientInterface.state("Unable to Connect");
				startGUI();
			}
			else
			{
				System.exit(1);
			}
			return false;
		}
	}

	private void checkIfClientClosed(ServerMessage serverMessage, String ex)
	{
		if (serverMessage == null)
		{
			// Possibly disconnect them
			clientInterface.state(ex);
			if (currUser != null)
			{
				clientNetworkHelper.sendMessage(dataout, new LogoutMessage(currUser));
				clientNetworkHelper.getMessage(datain);
			}

			clientNetworkHelper.sendMessage(dataout, protocol.genDisconnect());
			System.exit(1);
		}
	}

}
