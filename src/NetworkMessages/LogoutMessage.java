package NetworkMessages;

import Databases.IDataBase;
import Utils.Protocol;
import Utils.Return;

public class LogoutMessage implements ServerMessage
{
	private static final long serialVersionUID = 5L;

	public LogoutMessage()
	{
		super();
	}

	private String username;

	public LogoutMessage(String username)
	{
		this.username = username;
	}

	@Override public ClientDisplayMessage perform(IDataBase db, Protocol protocol)
	{
		Return result = db.update("UPDATE USERS set LOGGEDIN = 0 WHERE USERNAME = '" + username + "'");

		if (result == Return.SUCCESS)
		{
			return protocol.genSuccess("Successfully Logged Out");
		}
		else
		{
			return protocol.genFail("UNABLE TO LOGOUT, TRY AGAIN");
		}
	}
}
