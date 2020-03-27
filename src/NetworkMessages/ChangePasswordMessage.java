package NetworkMessages;

import Databases.IDataBase;
import Utils.Protocol;
import Utils.Return;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordMessage implements ServerMessage
{
	private static final long serialVersionUID = 6L;
	private String username;
	private String newPassword;
	private String reEnterPW;
	public ChangePasswordMessage()
	{
		super();
	}

	private static String passwordregex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static Pattern passwordpattern = Pattern.compile(passwordregex);

	public ChangePasswordMessage(String username, String newPassword, String reEnterPW)
	{
		this.username = username;
		this.newPassword = newPassword;
		this.reEnterPW = reEnterPW;
	}

	@Override public ClientDisplayMessage perform(IDataBase db, Protocol protocol)
	{

		if (validPassword())
		{
			Return result = db.update("UPDATE USERS SET PASSWORD = '" + newPassword + "' WHERE USERNAME = '" + username + "'");

			if (result == Return.SUCCESS)
			{
				return protocol.genSuccess("Password Successfully Changed");
			}
			else
			{
				return protocol.genFail("Try Again, Password not Changed");
			}
		}
		else
		{
			return protocol.genFail("Invalid Password. Password not Changed");
		}
	}

	public boolean validPassword()
	{
		Matcher matcher = passwordpattern.matcher(newPassword);
		return matcher.find() && newPassword.equals(reEnterPW) && !newPassword.equals("");
	}
}
