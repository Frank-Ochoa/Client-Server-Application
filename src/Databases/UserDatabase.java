package Databases;

import Utils.Return;

import java.sql.*;

public class UserDatabase implements IDataBase
{
	private Connection conn;
	private Statement stmt;
	private final String dbURL = "jdbc:mysql://127.0.0.1:3306/userdatabase";
	private final String DBAuser = "root";
	private final String DBApasssword = "Admin098";

	public UserDatabase()
	{
		try
		{
			this.conn = DriverManager.getConnection(dbURL, DBAuser, DBApasssword);
			stmt = conn.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override public ResultSet query(String query)
	{
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e)
		{
			//e.printStackTrace();
			return rs;
		}

		return rs;
	}

	@Override public Return store(IRecord record)
	{
		int success = 0;
		try
		{
			success = stmt.executeUpdate("INSERT INTO users "
					+ "values ('" + record.getUsername() + "', '" + record.getPassword() + "', '"
					+ record.getEmail() + "', " + record.getLocked() + ", " + record.getLoggedIn() + ")");

		} catch (SQLException ex)
		{
			//ex.printStackTrace();
			return Return.FAIL;
		}

		if (success > 0)
		{
			return Return.SUCCESS;
		}
		else
		{
			//TODO: look into fail cases more
			return Return.FAIL;
		}
	}

	@Override public Return update(String query)
	{
		int success = 0;
		try
		{
			success = stmt.executeUpdate(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return Return.FAIL;
		}

		if (success > 0)
		{
			return Return.SUCCESS;
		}
		else
		{
			//TODO: look into fail cases more
			return Return.FAIL;
		}
	}
}
