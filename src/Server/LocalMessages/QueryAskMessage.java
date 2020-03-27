package Server.LocalMessages;

import Databases.IDataBase;
import Utils.DefaultQuery;
import Utils.Protocol;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryAskMessage
{
	private String query;

	private QueryAskMessage(String query)
	{
		this.query = query;
	}

	public static QueryAskMessage queryFactory(DefaultQuery queryType)
	{
		switch (queryType)
		{
			case NUM_REGISTERED:
				return new QueryAskMessage("SELECT count(*) FROM USERS");
			case NUM_LOGGED_IN:
				return new QueryAskMessage("SELECT count(*) FROM USERS WHERE LOGGEDIN = 1");
			case WHO_LOGGED_IN:
				return new QueryAskMessage("SELECT USERNAME FROM USERS WHERE LOGGEDIN = 1");
			case WHO_LOCKED_OUT:
				return new QueryAskMessage("SELECT USERNAME FROM USERS WHERE LOCKED >= 3");
			default:
				throw new IllegalStateException("Query Type Unsupported " + queryType);
		}
	}

	public static QueryAskMessage queryFactory(String customQuery)
	{
		return new QueryAskMessage(customQuery);
	}

	public AdminDisplayMessage perform(IDataBase db, Protocol protocol)
	{


		try
		{
			ResultSet rs = db.query(query);
			List<String> queryResults = new ArrayList<>();

			if (rs != null)
			{
				if (rs.isBeforeFirst())
				{
					int colCount = rs.getMetaData().getColumnCount();
					while (rs.next())
					{
						for (int i = 1; i <= colCount; i++)
						{
							queryResults.add(rs.getString(i));
						}
					}

					return protocol.genQuerySuccess("QUERY EXECUTED SUCCESSFULLY", queryResults);
				}
				else
				{
					return protocol.genQuerySuccess("QUERY EXECUTED SUCCESSFULLY BUT RETURNED NO RESULTS");
				}
			}
			else
			{
				return protocol.genQueryFailedMessage("QUERY NOT SUPPORTED BY MYSQL DATABASE");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return protocol.genQueryFailedMessage("QUERY UNABLE TO BE EXECUTED");
		}

	}

}
