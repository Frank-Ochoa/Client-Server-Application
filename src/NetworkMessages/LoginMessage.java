package NetworkMessages;

import Databases.IDataBase;
import Utils.Protocol;
import Utils.Return;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class LoginMessage implements ServerMessage
{
	private static final long serialVersionUID = 2L;
	private String username;
	private String password;

	public LoginMessage()
	{
		super();
	}

	public LoginMessage(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override public ClientDisplayMessage perform(IDataBase db, Protocol protocol)
	{
		ResultSet rs = db
				.query("SELECT LOCKED FROM USERS WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password
						+ "'");

		if (!username.equals("") && !password.equals(""))
		{
			try
			{
				// If a username and password were matched
				if (rs.next())
				{
					// Username, and password matched, check counter
					if (rs.getInt(1) < 3)
					{
						// Switch Logged in to 1
						Return state = db.update("UPDATE USERS set LOGGEDIN = 1 WHERE USERNAME = '" + username + "'");
						Return state2 = db.update("UPDATE USERS set LOCKED = 0 WHERE USERNAME = '" + username + "'");

						if (state == Return.SUCCESS)
						{
							return protocol.genSuccess("Login Successful");
						}
						else
						{
							System.err.println("LOGGED IN BUT NOT SWITCHED IN DB");
							return protocol.genSuccess("LOGGED IN BUT NOT SWITCHED IN DB");
						}
					}
					else
					{
						return protocol.genFail("Account Locked. Please Recover Password");
					}
				}
				// Else a username and password were not matched
				else
				{
					// Increment locked
					Return state = db.update("UPDATE USERS set LOCKED = LOCKED + 1 WHERE USERNAME = '" + username + "'");

					ResultSet locked = db.query("SELECT LOCKED FROM USERS WHERE USERNAME = '" + username + "'");

					if (locked.next())
					{
						if (locked.getInt(1) >= 3)
						{
							return protocol.genFail(
									"Login Attempts Exceeded. Account is now Locked. Please Recover Password.");
						}
					}

					if (state == Return.SUCCESS)
					{
						return protocol.genFail("Please Try Again. Locked Count Increased.");
					}
					else
					{
						System.err.println("INCREMENT LOCKED FAILED IN DB");
						return protocol.genFail("Login Failed. Please try again");
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
				return protocol.genFail("SQL ERROR");
			}
		}
		else
		{
			return protocol.genFail("Please Fill Forms");
		}

	}

}
