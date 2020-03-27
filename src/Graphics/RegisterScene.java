package Graphics;

import Client.ClientInterface;
import Utils.PaneSizes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class RegisterScene
{
	public Scene display(Stage window, LoginGraphic loginGraphic, ClientInterface clientInterface)
	{
		Label newUserRegister = new Label("New User Registration");
		Label labelUser = new Label("Username");
		labelUser.setAlignment(Pos.CENTER);
		TextField enterUserName = new TextField();
		enterUserName.setPromptText("Please Enter Username");
		enterUserName.setAlignment(Pos.CENTER);

		Label labelPassword = new Label(
				"Password: Must contain at least 1 Letter, Number, and Special Character. "
						+ "\nThe minimum length is 8 characters.");
		labelPassword.setWrapText(true);
		labelPassword.setTextAlignment(TextAlignment.CENTER);
		labelPassword.setAlignment(Pos.CENTER);
		TextField enterPassword = new TextField();
		enterPassword.setPromptText("Please Enter Password");
		enterPassword.setAlignment(Pos.CENTER);

		Label labelEmail = new Label("Email");
		labelEmail.setAlignment(Pos.CENTER);
		TextField enterEmail = new TextField();
		enterEmail.setPromptText("Please Enter Email");
		enterEmail.setAlignment(Pos.CENTER);

		Button button = new Button("Register");
		button.setOnAction(event -> {
			loginGraphic.setMsg(clientInterface.protocol
					.genRegister(enterUserName.getText(), enterPassword.getText(), enterEmail.getText()));
			//window.setScene(loginGraphic.getLoginScene());
			window.close();
		});

		VBox layout = new VBox(10);
		layout.getChildren()
				.addAll(newUserRegister, labelUser, enterUserName, labelPassword, enterPassword, labelEmail, enterEmail,
						button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);

		return scene;
	}
}
