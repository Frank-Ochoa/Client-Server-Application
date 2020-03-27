package Graphics;

import Databases.IDataBase;
import Server.AdminUI.AdminInterface;
import Server.LocalMessages.QueryAskMessage;
import Utils.DefaultQuery;
import Utils.PaneSizes;
import Utils.Protocol;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QueryWindowGraphic
{

	public void display(IDataBase db, Protocol protocol, AdminInterface adminInterface)
	{
		Stage window = new Stage();
		window.setTitle("ADMIN QUERY WINDOW");
		window.initModality(Modality.APPLICATION_MODAL);

		Label contextLabel = new Label("SELECT YOUR QUERY FROM BELOW");
		contextLabel.setWrapText(true);
		contextLabel.setTextAlignment(TextAlignment.CENTER);
		contextLabel.setAlignment(Pos.CENTER);

		Button numRegistered = new Button("NUMBER OF USERS REGISTERED");
		numRegistered.setOnAction(event -> {
			// Should bring back to admin query? Or just while loop? and exit = false on admin UI
			protocol.genQueryAsk(DefaultQuery.NUM_REGISTERED).perform(db, protocol).displayState(adminInterface);
		});

		Button numLoggedIn = new Button("NUMBER OF USERS LOGGED IN");
		numLoggedIn.setOnAction(event -> {
			// Should bring back to admin query? Or just while loop? and exit = false on admin UI
			protocol.genQueryAsk(DefaultQuery.NUM_LOGGED_IN).perform(db, protocol).displayState(adminInterface);
		});

		Button whoLoggedIn = new Button("USERS LOGGED IN");
		whoLoggedIn.setOnAction(event -> {
			// Should bring back to admin query? Or just while loop? and exit = false on admin UI
			protocol.genQueryAsk(DefaultQuery.WHO_LOGGED_IN).perform(db, protocol).displayState(adminInterface);
		});

		Button whoLockedOut = new Button("USERS LOCKED OUT");
		whoLockedOut.setOnAction(event -> {
			// Should bring back to admin query? Or just while loop? and exit = false on admin UI
			protocol.genQueryAsk(DefaultQuery.WHO_LOCKED_OUT).perform(db, protocol).displayState(adminInterface);
		});

		TextField customQuery = new TextField();
		customQuery.setPromptText("Enter Custom MYSQL Query");
		Button executeCustom = new Button("RUN CUSTOM QUERY");
		executeCustom.setOnAction(event -> {
			// Should bring back to admin query? Or just while loop? and exit = false on admin UI
			protocol.genQueryAsk(customQuery.getText()).perform(db, protocol).displayState(adminInterface);
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(contextLabel, numRegistered, numLoggedIn, whoLoggedIn, whoLockedOut, customQuery,
				executeCustom);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, PaneSizes.AVGWIDTH, PaneSizes.AVGHEIGHT);
		window.setScene(scene);
		window.show();
	}
}
