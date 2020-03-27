package Graphics;

import Utils.PaneSizes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StateGraphic
{
	public void display(String successEX)
	{
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		Label stateLabel = new Label(successEX);
		stateLabel.setAlignment(Pos.CENTER);
		stateLabel.setTextAlignment(TextAlignment.CENTER);
		stateLabel.setWrapText(true);

		Button button = new Button("Proceed");
		button.setOnAction(event -> {
			window.close();
		});

		VBox layout = new VBox(20);
		layout.getChildren().addAll(stateLabel, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);
		window.setScene(scene);
		window.showAndWait();
	}
}
