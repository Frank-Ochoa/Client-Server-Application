package Utils;

import NetworkMessages.ChangePasswordMessage;
import NetworkMessages.ClientDisplayMessage;
import NetworkMessages.ConnectMessage;
import NetworkMessages.DisconnectMessage;
import NetworkMessages.FailMessage;
import NetworkMessages.LoginMessage;
import NetworkMessages.LogoutMessage;
import NetworkMessages.NewRegistrationMessage;
import NetworkMessages.PasswordRecoveryMessage;
import NetworkMessages.ServerMessage;
import NetworkMessages.SuccessMessage;
import Server.LocalMessages.AdminDisplayMessage;
import Server.LocalMessages.QueryAskMessage;
import Server.LocalMessages.QueryFailMessage;
import Server.LocalMessages.QuerySuccessMessage;

import java.util.List;

public class Protocol
{

	public Protocol()
	{

	}

	public ServerMessage genConnect()
	{
		return new ConnectMessage();
	}

	public ServerMessage genDisconnect()
	{
		return new DisconnectMessage();
	}

	public ClientDisplayMessage genSuccess(String successEx)
	{
		return new SuccessMessage(successEx);
	}

	public ClientDisplayMessage genFail(String failEx)
	{
		return new FailMessage(failEx);
	}

	public ServerMessage genLogin(String username, String password)
	{
		return new LoginMessage(username, password);
	}

	public ServerMessage genLogout(String username)
	{
		return new LogoutMessage(username);
	}

	public ServerMessage genRegister(String username, String password, String email)
	{
		return new NewRegistrationMessage(username, password, email);
	}

	public ServerMessage genRecoverPassword(String username)
	{
		return new PasswordRecoveryMessage(username);
	}

	public ServerMessage genChangePassword(String username, String newPw, String reEnterPw)
	{
		return new ChangePasswordMessage(username, newPw, reEnterPw);
	}

	public QueryAskMessage genQueryAsk(String query)
	{
		return QueryAskMessage.queryFactory(query);
	}

	public QueryAskMessage genQueryAsk(DefaultQuery queryType)
	{
		return QueryAskMessage.queryFactory(queryType);
	}

	public AdminDisplayMessage genQuerySuccess(String successEx, List<String> queryResults)
	{
		return new QuerySuccessMessage(successEx, queryResults);
	}

	public AdminDisplayMessage genQuerySuccess(String successEx)
	{
		return new QuerySuccessMessage(successEx);
	}

	public AdminDisplayMessage genQueryFailedMessage(String failEx)
	{
		return new QueryFailMessage(failEx);
	}

}
