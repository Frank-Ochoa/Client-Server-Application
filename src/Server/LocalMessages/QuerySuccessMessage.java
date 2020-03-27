package Server.LocalMessages;

import Server.AdminUI.AdminInterface;

import java.util.List;

public class QuerySuccessMessage implements AdminDisplayMessage
{
	public static long serialVersionUID = 18L;
	private String successEx;
	private List<String> queryResults;
	public QuerySuccessMessage()
	{
		super();
	}

	public QuerySuccessMessage(String successEx)
	{
		this.successEx = successEx;
	}

	public QuerySuccessMessage(String successEx, List<String> queryResults)
	{
		this.successEx = successEx;
		this.queryResults = queryResults;
	}

	public void displayState(AdminInterface clientInterface)
	{
		if (queryResults != null && !queryResults.isEmpty())
		{
			clientInterface.querySuccess(successEx, queryResults);
		}
		else
		{
			clientInterface.state(successEx);
		}
	}
}
