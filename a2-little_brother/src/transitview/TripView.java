package transitview;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transitcontroller.SystemController;
import transitcontroller.TripViewController;
import transitutility.SaveData;

/**
 * A class that provides a view to have trips for card holders. It has three
 * buttons which are start trip, next route, and end trip. Card holders can
 * select their trip destinations and they can go back to the main card holder
 * view.
 */
public class TripView extends Application {

	public static TripViewController tv = new TripViewController();
	private GridPane grid;
	private Stage stage;
	// The EventHandler for switching the stage to the card holder view.
	EventHandler<ActionEvent> exit = e -> {

		CardHolderView cardHoldersView = new CardHolderView();
		try {
			cardHoldersView.start(new Stage());
			CardHolderView.trip.setDisable(true);
			CardHolderView.operationControl.forEach(item -> item.setDisable(false));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		stage.close();
	};

	/**
	 * Launch a standalone application.
	 *
	 * @param args the command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * The main entry point for all JavaFX applications.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (TripViewController.getTrip() != null && !TripViewController.getTrip().is_completed()) {
					TripViewController.getCard().TapOut(TripViewController.getTrip());
					TripViewController.getTrip().completed_trip();
					TripViewController.getCard().charge(TripViewController.getTrip().get_costs(),
							TripViewController.getTrip().get_date());
				}
				SaveData.save(SystemController.getCardHolders());
				System.exit(0);
			}
		});
		this.stage = stage;
		initUI(stage);
	}

	/**
	 * The application initialization method.
	 *
	 * @param stage a stage that has the scene.
	 */
	private void initUI(Stage stage) {
		grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setVgap(10);
		grid.setHgap(10);
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.getColumnConstraints().add(new ColumnConstraints(20));
		grid.setAlignment(Pos.CENTER);

		// Creates three buttons for movements of the trip.
		Button start = new Button("Start Trip");
		start.setOnAction(tv);
		grid.add(start, 0, 0, 3, 1);
		Button next = new Button("Next Station/Stop");
		next.setOnAction(tv);
		grid.add(next, 6, 0, 4, 1);
		Button end = new Button("End Trip");
		end.setOnAction(tv);
		grid.add(end, 18, 0, 3, 1);
		grid.add(new Separator(), 0, 1, 21, 1);
		Button chExit = new Button("< Exit");
		chExit.setOnAction(exit);
		grid.add(chExit, 19, 2, 1, 1);

		Scene scene = new Scene(grid, 500, 120);

		stage.setTitle("TRIP");
		stage.setScene(scene);
		stage.show();
	}
}