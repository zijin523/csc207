package transitview;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transitcontroller.AdminUserController;
import transitcontroller.CardHolderController;
import transitcontroller.SystemController;
import transitmodel.AdminUser;
import transitmodel.CardHolder;
import transitutility.*;

import java.util.ArrayList;

/**
 * A class that provides the main view of the entire application. It contains two text fields
 * for users to fill in their user name and user email, and can either sign in or sign out.
 * The view also contains guidance marks for successful or failed info.
 */
public class SystemView extends Application {
    public static String username, useremail;
    static String r = "";
    private Button registerButton;
    private Button loginButton;
    private TextField name;
    private TextField email;
    private Stage stage;
    private Label check, nameCheck, emailCheck;
    // The EventHandler that checks the correctness of the username and useremail
    // while registering as a new user, and the result will pass to the relevant labels.
    EventHandler<ActionEvent> register = e -> {
        username = name.getText();
        useremail = email.getText();
        boolean vaildName = CheckName.check(username);
        boolean vaildEmail = CheckEmail.check(useremail);
        if (!vaildName || !vaildEmail) {
            nameCheck.setText(vaildName ? "√" : "×");
            emailCheck.setText(vaildEmail ? "√" : "×");
        } else {
            nameCheck.setText("√");
            emailCheck.setText("√");
            if (Login.check(username, useremail).equals("")) {
                Register.save(username, useremail, "user");
                check.setText("Sign up Success");
            } else {
                check.setText("User Exists");
            }
        }
    };
    // The EventHandler that checks the user information is the same as the ones
    // stored in the user info file, which is the login process of the system. Login
    // status and info will pass to relevant labels.
    EventHandler<ActionEvent> login = e -> {
        username = name.getText();
        useremail = email.getText();
        String loginCheck = Login.check(username, useremail);
        ArrayList<CardHolder> s = SystemController.getCardHolders();
        if (s == null) {
            s = ReadData.read();
        }
        if (loginCheck.equals("user")) {
            CardHolder cur = null;
            for (CardHolder user : s) {
                if (user.getName().equals(username) && user.getEmail().equals(useremail)) {
                    cur = user;
                    break;
                }
            }
            if (cur != null) {
                System.out.println("Previous Records Found");
                CardHolderController.setCardHolders(cur);
            } else {
                System.out.println("No Records Found");
                CardHolder cardHolder = new CardHolder(username, useremail);
                if (SystemController.getCardHolders() != null) {
                    SystemController.getCardHolders().add(cardHolder);
                }
                CardHolderController.setCardHolders(cardHolder);
            }
            CardHolderView cardHolderView = new CardHolderView();

            try {
                cardHolderView.start(new Stage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            stage.close();
        } else if (loginCheck.equals("admin")) {
            AdminUserView adminUsersView = new AdminUserView();
            AdminUserController.setAdminUsers(new AdminUser(username, useremail, AdminUserController.getCardHolders()));
            try {
                adminUsersView.start(new Stage());
            } catch (Exception e2) {
                e2.printStackTrace();
                check.setText("User Registered");
            }
            stage.close();
        } else {
            check.setText("User Account Invalid");
        }
    };
    private GridPane grid;

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
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        // Creates labels and buttons for typing in the username, useremail
        // and carries out signin or signup movements.
        Label text2 = new Label("     Sign in to TRANSIT");
        text2.setFont(new Font("Arial", 14));
        grid.add(text2, 3, 1);
        Label text4 = new Label("User Name:");
        grid.add(text4, 3, 3);
        name = new TextField();
        grid.add(name, 3, 4);
        nameCheck = new Label();
        grid.add(nameCheck, 4, 4);
        Label text5 = new Label("User Email:");
        grid.add(text5, 3, 5);
        email = new TextField();
        grid.add(email, 3, 6);
        emailCheck = new Label();
        grid.add(emailCheck, 4, 6);
        registerButton = new Button("                     Sign up                    ");
        registerButton.setOnAction(register);
        grid.add(registerButton, 3, 8);
        loginButton = new Button("                      Sign in                     ");
        loginButton.setOnAction(login);
        grid.add(loginButton, 3, 9);
        check = new Label();
        grid.add(check, 3, 10);
        Scene scene = new Scene(grid, 300, 280);

        stage.setTitle("TRANSIT SYSTEM");
        stage.setScene(scene);
        stage.show();
    }
}
