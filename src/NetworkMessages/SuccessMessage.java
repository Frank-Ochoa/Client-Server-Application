package NetworkMessages;

import Client.ClientInterface;

public class SuccessMessage implements ClientDisplayMessage
{
	private static final long serialVersionUID = 10L;
	private String successEx;

	public SuccessMessage()
	{
		super();
	}

	public SuccessMessage(String successEx)
	{
		this.successEx = successEx;
	}

	@Override public void displayState(ClientInterface clientInterface)
	{

		clientInterface.state(successEx);

	}

}
