package Utils;

import NetworkMessages.IMessage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface INetworkHelper
{
	void sendMessage(ObjectOutputStream dataOut, IMessage message);
	IMessage getMessage(ObjectInputStream dataIn);
}
