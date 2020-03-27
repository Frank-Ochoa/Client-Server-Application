package NetworkMessages;

import Databases.IDataBase;
import Databases.UserRecord;
import Utils.Protocol;
import Utils.Return;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewRegistrationMessage implements ServerMessage
{
	private static final long serialVersionUID = 4L;

	public NewRegistrationMessage()
	{
		super();
	}

	private String username;
	private String password;
	private String email;

	private static String emailregex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern emailpattern = Pattern.compile(emailregex);

	private static String passwordregex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
	private static Pattern passwordpattern = Pattern.compile(passwordregex);

	public NewRegistrationMessage(String username, String password, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Override public ClientDisplayMessage perform(IDataBase db, Protocol protocol)
	{
		boolean validEmail = validEmailAddress();
		boolean validPassword = validPassword();

		if (validEmail && validPassword)
		{
			Return result = db.store(new UserRecord(username, password, email, 0, 0));

			if (result == Return.SUCCESS)
			{
				return protocol.genSuccess("Registration Successful");
			}
			else
			{
				return protocol.genFail("UNABLE TO REGISTER, TRY AGAIN");
			}
		}
		else
		{
			if (!validEmail && !validPassword)
			{
				return protocol.genFail("Email AND Password invalid. Please try again.");
			}
			else if (!validEmail)
			{
				return protocol.genFail("Email invalid. Please try again.");
			}
			else
			{
				return protocol.genFail("Password invalid. Please try again.");
			}
		}


	}

	public boolean validPassword()
	{
		Matcher matcher = passwordpattern.matcher(password);
		return matcher.find();
	}

	public boolean validEmailAddress ()
	{
		Matcher matcher = emailpattern.matcher(email);
		return matcher.find();
	}


}
