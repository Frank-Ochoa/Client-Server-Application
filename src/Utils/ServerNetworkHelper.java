package Utils;

import NetworkMessages.IMessage;
import NetworkMessages.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerNetworkHelper implements INetworkHelper
{
	@Override public void sendMessage(ObjectOutputStream dataOut, IMessage message)
	{
		try
		{
			dataOut.writeObject(message);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override public ServerMessage getMessage(ObjectInputStream dataIn)
	{
		try
		{
			return (ServerMessage) dataIn.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
