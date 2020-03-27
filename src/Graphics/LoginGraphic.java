package Graphics;

import Client.ClientInterface;
import NetworkMessages.ServerMessage;
import Utils.PaneSizes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;

public class LoginGraphic
{
	//	private String username;
	//	private String password;

	private Stage window;
	private ServerMessage msg;
	private String currUser = null;

	public Pair<ServerMessage, String> display(ClientInterface clientInterface)
	{
		window = new Stage();
		window.setTitle("Login");

		Label labelUser = new Label("Username");
		labelUser.setAlignment(Pos.CENTER);
		TextField enterUserName = new TextField();
		enterUserName.setPromptText("Please Enter Username");
		enterUserName.setAlignment(Pos.CENTER);

		Label labelPassword = new Label("Password");
		labelPassword.setAlignment(Pos.CENTER);
		PasswordField enterPassword = new PasswordField();
		enterPassword.setPromptText("Please Enter Password");
		enterPassword.setAlignment(Pos.CENTER);

		Button button = new Button("Login");
		button.setOnAction(event -> {
			msg = clientInterface.protocol.genLogin(enterUserName.getText(), enterPassword.getText());
			currUser = enterUserName.getText();
			window.close();
		});

		Button register = new Button("New User Registration");
		register.setOnAction(event -> {
			//change scene, then change it back to login after these click their buttons
			window.setScene(clientInterface.register(window, this));
		});
		register.setWrapText(true);
		register.setTextAlignment(TextAlignment.CENTER);

		Button recoverPass = new Button("Recover Password");
		recoverPass.setOnAction(event -> {
			//change scene, then change it back to login after these click their buttons
			window.setScene(clientInterface.recoverPassword(window, this));
		});
		recoverPass.setWrapText(true);
		recoverPass.setTextAlignment(TextAlignment.CENTER);

		Button disconnectButton = new Button("Disconnect");
		disconnectButton.setOnAction(event -> {
			msg = clientInterface.protocol.genDisconnect();
			window.close();
		});
		disconnectButton.setWrapText(true);
		disconnectButton.setTextAlignment(TextAlignment.CENTER);

		HBox innerButtons = new HBox(20);
		innerButtons.getChildren().addAll(register, recoverPass);
		//inner.setAlignment(Pos.CENTER);
		HBox belowInner = new HBox(20);
		belowInner.getChildren().addAll(disconnectButton);
		belowInner.setAlignment(Pos.CENTER);

		VBox layout = new VBox(20);
		layout.setPadding(new Insets(0, PaneSizes.AVGWIDTH / 4.0, 0, PaneSizes.AVGWIDTH / 4.0));
		layout.getChildren()
				.addAll(labelUser, enterUserName, labelPassword, enterPassword, button, innerButtons, belowInner);
		layout.setAlignment(Pos.CENTER);

		Scene loginScene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);
		window.setScene(loginScene);
		window.showAndWait();

		return new Pair<>(msg, currUser);
	}

	public void setMsg(ServerMessage msg)
	{
		this.msg = msg;
	}
}
