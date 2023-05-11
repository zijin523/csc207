package transitview;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transitcontroller.AdminUserController;
import transitcontroller.CardHolderController;
import transitcontroller.SystemController;
import transitcontroller.TripViewController;
import transitmodel.CardHolder;
import transitutility.SaveData;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides a view for the card holders , which includes functions that
 * can only be handled by card holder user. The view has multiple buttons.
 */
public class CardHolderView extends Application {
    public static Button sw, regis, change, get, cost, trip, amount1, amount2, amount3, suspend;
    public static Label nameLabel, emailLabel, cardLabel;
    public static List<Button> operationControl = new ArrayList<>();
    public static CardHolderController user = new CardHolderController();
    public static boolean status = true;
    private GridPane grid;
    private Label name, email;
    private Stage stage;
    // The EventHandler for switching the stage to the System view.
    EventHandler<ActionEvent> exit = e -> {
        ArrayList<CardHolder> dailyUsers = AdminUserController.getCardHolders();
        for (int i = 0; i < dailyUsers.size(); i++) {
            if (dailyUsers.get(i).getEmail().equals(CardHolderController.getCardHolder().getEmail())) {
                dailyUsers.remove(i);
            }
        }
        dailyUsers.add(CardHolderController.getCardHolder());
        CardHolderController.setCardHolders(null);
        CardHolderController.setCard(null);
        SystemView systemView = new SystemView();
        try {
            systemView.start(new Stage());

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        stage.close();
    };

    // The EventHandler for switching the stage to the Trip view.
    EventHandler<ActionEvent> t = e -> {
        TripView tripView = new TripView();
        TripViewController.setCard(CardHolderController.getCard());
        try {
            tripView.start(new Stage());
        } catch (Exception e2) {
            e2.printStackTrace();
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
                if (SystemController.getCardHolders() != null) {
                    SaveData.save(SystemController.getCardHolders());
                    System.exit(0);
                }
            }
        });
        // The welcoming sentence will be printed each time a card holder user is logged in.
        System.out.println("Welcome " + CardHolderController.getCardHolder().getName() + "," + CardHolderController.getCardHolder().getEmail());
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

        // Creates labels for the current user who logs in.
        name = new Label("User Name:");
        grid.add(name, 0, 0, 4, 1);
        nameLabel = new Label(CardHolderController.getCardHolder().getName());
        grid.add(nameLabel, 4, 0, 8, 1);
        email = new Label("User Email:");
        grid.add(email, 0, 1, 4, 1);
        emailLabel = new Label(CardHolderController.getCardHolder().getEmail());
        grid.add(emailLabel, 4, 1, 8, 1);
        grid.add(new Separator(), 0, 2, 20, 1);

        // Creates the labels and buttons for card usage.
        Label title = new Label("Card Usage:");
        grid.add(title, 0, 3, 4, 1);
        suspend = new Button("Suspend");
        suspend.setOnAction(user);
        suspend.setDisable(true);
        grid.add(suspend, 4, 3, 3, 1);
        sw = new Button("Use / Change Another");
        sw.setOnAction(user);
        grid.add(sw, 7, 3, 10, 1);

        // Creates the labels and buttons for card charge.
        Label title2 = new Label("Card Charge:");
        grid.add(title2, 0, 4, 4, 1);
        amount1 = new Button("$10");
        amount1.setOnAction(user);
        amount1.setDisable(true);
        grid.add(amount1, 4, 4, 2, 1);
        amount2 = new Button("$20");
        amount2.setOnAction(user);
        amount2.setDisable(true);
        grid.add(amount2, 6, 4, 2, 1);
        amount3 = new Button("$50");
        amount3.setOnAction(user);
        grid.add(amount3, 8, 4, 3, 1);
        amount3.setDisable(true);
        cardLabel = new Label(CardHolderController.getCard() == null ?
                "No card is being used" : "Last card used: " + CardHolderController.getCard().getCardNumber());
        grid.add(cardLabel, 4, 5, 10, 1);
        grid.add(new Separator(), 0, 6, 20, 1);

        // Creates the labels and options that a card holder user can handle.
        Label title3 = new Label("User Options");
        grid.add(title3, 0, 7, 5, 1);
        regis = new Button("Register Card");
        regis.setOnAction(user);
        grid.add(regis, 4, 7, 5, 1);
        change = new Button("Change Name");
        change.setOnAction(user);
        grid.add(change, 8, 7, 8, 1);
        get = new Button("Recent Trips");
        get.setOnAction(user);
        grid.add(get, 4, 8, 5, 1);
        trip = new Button("Trip >");
        trip.setOnAction(t);
        trip.setDisable(CardHolderController.getCard() == null);
        grid.add(trip, 8, 8, 5, 1);
        cost = new Button("Monthly Cost");
        cost.setOnAction(user);

        operationControl.add(sw);
        operationControl.add(regis);
        operationControl.add(change);
        operationControl.add(get);
        operationControl.add(cost);
        operationControl.forEach(item -> item.setDisable(status));
        grid.add(cost, 4, 9, 5, 1);


        grid.add(new Separator(), 0, 10, 20, 1);

        // Creates the button exit to return to the main system interface.
        Button chExit = new Button("< Exit");
        chExit.setOnAction(exit);
        grid.add(chExit, 19, 11, 1, 1);

        Scene scene = new Scene(grid, 580, 380);

        stage.setTitle("CARDHOLDER");
        stage.setScene(scene);
        stage.show();
    }
}