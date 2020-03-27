package NetworkMessages;

import Databases.IDataBase;
import Utils.Protocol;

public class DisconnectMessage implements ServerMessage
{
	private static final long serialVersionUID = 3L;

	public DisconnectMessage()
	{
		super();
	}

	@Override public ClientDisplayMessage perform(IDataBase db, Protocol protocol)
	{
		return protocol.genSuccess("Disconnect Successful");
	}
}
