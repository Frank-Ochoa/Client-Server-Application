package NetworkMessages;

import Client.ClientInterface;
import NetworkMessages.IMessage;

public interface ClientDisplayMessage extends IMessage
{
	void displayState(ClientInterface clientInterface);
}
