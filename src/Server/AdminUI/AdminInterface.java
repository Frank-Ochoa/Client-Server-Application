package Server.AdminUI;


import Databases.IDataBase;
import Graphics.QueryAnsweredGraphic;
import Graphics.QueryWindowGraphic;
import Graphics.StateGraphic;
import Utils.Protocol;

import java.util.List;

public class AdminInterface
{
	public void queryWindow(IDataBase db, Protocol protocol)
	{
		new QueryWindowGraphic().display(db, protocol, this);
	}

	public void state(String state)
	{
		new StateGraphic().display(state);
	}

	public void querySuccess(String successEx, List<String> results)
	{
		new QueryAnsweredGraphic().display(successEx, results);
	}
}
