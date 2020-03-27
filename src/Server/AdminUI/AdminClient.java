package Server.AdminUI;

import Databases.IDataBase;
import Databases.UserDatabase;
import Utils.Protocol;
import javafx.application.Application;
import javafx.stage.Stage;

public class AdminClient extends Application
{
	private AdminInterface adminInterface = new AdminInterface();

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override public void start(Stage primaryStage)
	{
		IDataBase db = new UserDatabase();
		Protocol protocol = new Protocol();

		adminInterface.queryWindow(db, protocol);

	}
}
