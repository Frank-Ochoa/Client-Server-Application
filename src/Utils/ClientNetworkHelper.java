package Utils;

import NetworkMessages.ClientDisplayMessage;
import NetworkMessages.IMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientNetworkHelper implements INetworkHelper
{

	public ClientNetworkHelper()
	{
	}

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

	@Override public ClientDisplayMessage getMessage(ObjectInputStream dataIn)
	{
		try
		{
			return (ClientDisplayMessage) dataIn.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}	}
}
