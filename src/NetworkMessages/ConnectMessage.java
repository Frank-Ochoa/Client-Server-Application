package NetworkMessages;

import Databases.IDataBase;
import Utils.Protocol;

public class ConnectMessage implements ServerMessage
{
	private static final long serialVersionUID = 1L;

	public ConnectMessage()
	{
		super();
	}

	@Override public ClientDisplayMessage perform(IDataBase db, Protocol protocol)
	{
		return protocol.genSuccess("Connection Successful");
	}
}
