package Graphics;

import Client.ClientInterface;
import Client.UserClient;
import NetworkMessages.DisconnectMessage;
import NetworkMessages.LoginMessage;
import NetworkMessages.LogoutMessage;
import NetworkMessages.ServerMessage;
import Utils.PaneSizes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainScreenGraphic
{
	private Stage window;
	private ServerMessage msg;

	public ServerMessage display(String currUser, ClientInterface clientInterface)
	{
		window = new Stage();
		window.setTitle("Welcome " + currUser);

		Button disconnectButton = new Button("Disconnect");
		disconnectButton.setOnAction(event -> {
			msg = clientInterface.protocol.genDisconnect();
			window.close();
		});

		Button logoutButton = new Button("Logout");
		logoutButton.setOnAction(event -> {
			// Pass in username
			msg = clientInterface.protocol.genLogout(currUser);
			window.close();
		});
		logoutButton.setWrapText(true);
		logoutButton.setTextAlignment(TextAlignment.CENTER);

		Button changePasswordButton = new Button("Change Password");
		changePasswordButton.setOnAction(event -> {
			//change scene, then change it back to login after these click their buttons
			window.setScene(clientInterface.changePassword(currUser, window, this));
		});
		changePasswordButton.setWrapText(true);
		changePasswordButton.setTextAlignment(TextAlignment.CENTER);


		VBox buttons = new VBox(10);
		buttons.setPadding(new Insets(10, 10, 10,
				10));
		buttons.getChildren().addAll(disconnectButton, logoutButton, changePasswordButton);
		//buttons.setAlignment(Pos.CENTER);

		BorderPane overAll = new BorderPane();
		overAll.setLeft(buttons);

		Scene mainScreen = new Scene(overAll, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);
		window.setScene(mainScreen);
		window.showAndWait();

		return msg;
	}

	public void setMsg(ServerMessage msg)
	{
		this.msg = msg;
	}
}
