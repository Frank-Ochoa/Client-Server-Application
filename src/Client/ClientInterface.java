package Client;

import Graphics.ChangePasswordScene;
import Graphics.ConnectGraphic;
import Graphics.LoginGraphic;
import Graphics.MainScreenGraphic;
import Graphics.RecoverPasswordScene;
import Graphics.RegisterScene;
import Graphics.StateGraphic;
import NetworkMessages.ServerMessage;
import Utils.Protocol;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ClientInterface
{
	public Protocol protocol;

	public ClientInterface(Protocol protocol)
	{
		this.protocol = protocol;
	}

	public Pair<String, Boolean> connect()
	{
		return new ConnectGraphic().getIP();
	}

	public Pair<ServerMessage, String> tryLogin()
	{
		return new LoginGraphic().display(this);
	}

	public void state(String stateEx)
	{
		new StateGraphic().display(stateEx);
	}

	public Scene register(Stage window, LoginGraphic loginGraphic)
	{
		return new RegisterScene().display(window, loginGraphic, this);
	}

	public Scene recoverPassword(Stage window, LoginGraphic loginGraphic)
	{
		return new RecoverPasswordScene().display(window, loginGraphic, this);
	}

	public Scene changePassword(String currUser, Stage window, MainScreenGraphic mainScreen)
	{
		return new ChangePasswordScene().display(currUser, window, mainScreen, this);
	}

	public ServerMessage mainScreen(String currUser)
	{
		return new MainScreenGraphic().display(currUser, this);
	}

}
