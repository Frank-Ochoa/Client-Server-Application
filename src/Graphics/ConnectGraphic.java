package Graphics;

import Utils.PaneSizes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ConnectGraphic
{
	private Stage window;
	private String ip;
	private boolean buttonClicked = false;
	public Pair<String, Boolean> getIP()
	{
		window = new Stage();
		window.setTitle("Connect");
		TextField enterIP = new TextField();
		enterIP.setPromptText("Please Enter IP Address");
		enterIP.setAlignment(Pos.CENTER);

		Label ipLabel = new Label("Please Enter IP Address");
		ipLabel.setAlignment(Pos.CENTER);
		Button button = new Button("Connect");
		button.setOnAction(event -> {
			ip = enterIP.getText();
			buttonClicked = true;
			window.close();
		});

		VBox layout = new VBox(20);
		layout.setPadding(new Insets(0, PaneSizes.AVGWIDTH / 4.0, 0,
				PaneSizes.AVGWIDTH / 4.0));
		layout.getChildren().addAll(ipLabel,enterIP, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);
		window.setScene(scene);
		window.showAndWait();

		return new Pair<>(ip, buttonClicked);
	}

}
