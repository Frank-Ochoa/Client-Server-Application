package NetworkMessages;

import Client.ClientInterface;

public class FailMessage implements ClientDisplayMessage
{
	private static final long serialVersionUID = 8L;

	private String failEx;

	public FailMessage(String failEx)
	{
		this.failEx = failEx;
	}

	@Override public void displayState(ClientInterface clientInterface)
	{
		clientInterface.state(failEx);

	}

}
