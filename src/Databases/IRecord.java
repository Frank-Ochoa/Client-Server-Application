package Databases;

public interface IRecord
{
	String getUsername();
	String getPassword();
	String getEmail();
	int getLocked();
	int getLoggedIn();
}
