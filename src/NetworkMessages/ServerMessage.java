package NetworkMessages;

import NetworkMessages.ClientDisplayMessage;
import Databases.IDataBase;
import Utils.Protocol;

public interface ServerMessage extends IMessage
{
	ClientDisplayMessage perform(IDataBase db, Protocol protocol);
}
