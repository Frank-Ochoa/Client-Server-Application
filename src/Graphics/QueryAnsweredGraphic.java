package Graphics;

import Utils.PaneSizes;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class QueryAnsweredGraphic
{
	public void display(String successEX, List<String> results)
	{
		Stage window = new Stage();
		window.setTitle("QUERY ANSWERED");
		window.initModality(Modality.APPLICATION_MODAL);

		Label successLabel = new Label(successEX);
		successLabel.setWrapText(true);
		successLabel.setTextAlignment(TextAlignment.CENTER);
		successLabel.setAlignment(Pos.CENTER);

		ListView<String> resultView = new ListView<>();
		resultView.getItems().addAll(results);

		Button button = new Button("Proceed");
		button.setOnAction(event -> {
			// Should bring back to admin query? Or just while loop? and exit = false on admin UI
			window.close();
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(successLabel, resultView, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);
		window.setScene(scene);
		window.showAndWait();
	}
}
