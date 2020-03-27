package Databases;

public class UserRecord implements IRecord
{
	private String username;
	private String password;
	private String email;
	private int locked;
	private int loggedIn;

	public UserRecord(String username, String password, String email, int locked, int loggedIn)
	{
		this.username = username;
		this.password = password;
		this.email = email;
		this.locked = locked;
		this.loggedIn = loggedIn;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getEmail()
	{
		return email;
	}

	public int getLocked()
	{
		return locked;
	}

	public int getLoggedIn()
	{
		return loggedIn;
	}
}
