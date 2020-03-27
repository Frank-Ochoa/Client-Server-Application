package Databases;

import Utils.Return;

import java.sql.ResultSet;

public interface IDataBase
{
	ResultSet query(String query);
	Return store(IRecord record);
	Return update(String query);
}
