package Server.LocalMessages;

import Server.AdminUI.AdminInterface;

public class QueryFailMessage implements AdminDisplayMessage
{
	private String failEx;

	public QueryFailMessage(String failEx)
	{
		this.failEx = failEx;
	}
	
	public void displayState(AdminInterface adminInterface)
	{
		adminInterface.state(failEx);
	}

}
