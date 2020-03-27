package Graphics;

import Client.ClientInterface;
import NetworkMessages.ChangePasswordMessage;
import NetworkMessages.PasswordRecoveryMessage;
import Utils.PaneSizes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangePasswordScene
{
	public Scene display(String currUser, Stage window, MainScreenGraphic mainScreenGraphic, ClientInterface clientInterface)
	{
		Label labelChangePassword = new Label("Change Password");
		labelChangePassword.setAlignment(Pos.CENTER);

		Label labelNewPW = new Label("New Password");
		labelNewPW.setAlignment(Pos.CENTER);
		PasswordField enterNewPW = new PasswordField();
		enterNewPW.setPromptText("Please Enter New Password");
		enterNewPW.setAlignment(Pos.CENTER);

		Label labelReNewPW = new Label("Re-Enter New Password");
		labelReNewPW.setAlignment(Pos.CENTER);
		PasswordField enterReNewPW = new PasswordField();
		enterReNewPW.setPromptText("Please Re-Enter New Password");
		enterReNewPW.setAlignment(Pos.CENTER);

		Button button = new Button("Change Password");
		button.setOnAction(event -> {
			mainScreenGraphic.setMsg(clientInterface.protocol.genChangePassword(currUser, enterNewPW.getText(), enterReNewPW.getText()));
			window.close();
		});

		VBox layout = new VBox(20);
		layout.getChildren().addAll(labelChangePassword, labelNewPW, enterNewPW, labelReNewPW, enterReNewPW, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);

		return scene;
	}
}
