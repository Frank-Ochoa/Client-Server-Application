package Graphics;

import Client.ClientInterface;
import NetworkMessages.PasswordRecoveryMessage;
import Utils.PaneSizes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecoverPasswordScene
{
	public Scene display(Stage window, LoginGraphic loginGraphic, ClientInterface clientInterface)
	{
		Label labelRecover = new Label("Recover Password");
		labelRecover.setAlignment(Pos.CENTER);

		Label labelUserName = new Label("Username");
		labelUserName.setAlignment(Pos.CENTER);
		TextField enterUsername = new TextField();
		enterUsername.setPromptText("Please Enter Username");
		enterUsername.setAlignment(Pos.CENTER);

		Button button = new Button("Send Password Recovery Email");
		button.setOnAction(event -> {
			loginGraphic.setMsg(clientInterface.protocol.genRecoverPassword(enterUsername.getText()));
			//window.setScene(loginGraphic.getLoginScene());
			window.close();
		});

		VBox layout = new VBox(20);
		layout.getChildren().addAll(labelRecover, labelUserName, enterUsername, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);

		return scene;
	}
}
