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
import transitcontroller.SystemController;
import transitutility.SaveData;

/**
 * A class that provides a view for administrative users, which includes functions that
 * can only be handled by administrative user. The view has multiple buttons.
 */
public class AdminUserView extends Application {
    public static Label nameLabel, emailLabel;
    static AdminUserController admin = new AdminUserController();
    private GridPane grid;
    private Label name, email;
    private Button exitButton;
    private Button closeButton;
    private Button openButton;
    private Stage stage;
    // The EventHandler for switching the stage to the SystemView.
    EventHandler<ActionEvent> exit = e -> {

        SystemView systemView = new SystemView();
        try {
            systemView.start(new Stage());
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
                if (SystemController.getCardHolders() != null) {
                    SaveData.save(SystemController.getCardHolders());
                    System.exit(0);
                }
            }
        });
        // The welcoming sentence will be printed each time an admin user is logged in.
        System.out.println("Welcome " + AdminUserController.getAdminUsers().getName() + "," + AdminUserController.getAdminUsers().getEmail());
        SystemController.setAdmin(admin);
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
        grid.setAlignment(Pos.CENTER);

        // Creates labels for the current user who logs in.
        name = new Label("User Name:");
        grid.add(name, 0, 0, 4, 1);
        nameLabel = new Label(AdminUserController.getAdminUsers().getName());
        grid.add(nameLabel, 4, 0, 10, 1);
        email = new Label("User Email:");
        grid.add(email, 0, 1, 4, 1);
        emailLabel = new Label(AdminUserController.getAdminUsers().getEmail());
        grid.add(emailLabel, 4, 1, 10, 1);

        grid.add(new Separator(), 0, 3, 20, 1);

        // Creates buttons for opening and closing the system.
        Label title2 = new Label("System Status:");
        grid.add(title2, 0, 4, 4, 1);
        openButton = new Button("Open");
        openButton.setOnAction(admin);
        grid.add(openButton, 4, 4, 3, 1);
        closeButton = new Button("Close");
        closeButton.setOnAction(admin);
        grid.add(closeButton, 7, 4, 3, 1);
        grid.add(new Separator(), 0, 5, 20, 1);

        // Creates the button of daily report.
        Label title3 = new Label("System Info:");
        grid.add(title3, 0, 6, 5, 1);
        Button report = new Button("Daily Report");
        report.setOnAction(admin);
        grid.add(report, 4, 6, 5, 1);

        grid.add(new Separator(), 0, 7, 20, 1);

        // Creates the button exit to return to the main system interface.
        exitButton = new Button("< Exit");
        exitButton.setOnAction(exit);
        grid.add(exitButton, 19, 8, 1, 1);

        Scene scene = new Scene(grid, 550, 270);
        stage.setTitle("ADMIN");
        stage.setScene(scene);
        stage.show();
    }
}
