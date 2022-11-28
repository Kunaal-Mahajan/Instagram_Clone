package controller;

//import code.support.SupportClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db_conn.Db_Conn;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import static model.Main.main_stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class LoginController implements Initializable {
    
    @FXML
    private AnchorPane registerpane;
    @FXML
    private AnchorPane loginpane;
    @FXML
    private Label titlelabel;
    @FXML
    private JFXButton mainbutton;
    @FXML
    private JFXTextField usernameregi;
    @FXML
    private JFXTextField useridregi;
    @FXML
    private JFXPasswordField userpassregi;
    @FXML
    private JFXTextField userpasscomregi;
    Db_Conn db_conn = new Db_Conn();
    @FXML
    private AnchorPane loginpane1;
    @FXML
    private JFXTextField useridtextfield;
    @FXML
    private JFXPasswordField passwordtextfield;
    @FXML
    private JFXButton registrationButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLogin();
        passwordtextfield.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (db_conn.checkLogins(useridtextfield.getText(), passwordtextfield.getText())) {
                    TrayNotification tray = new TrayNotification();
                    AnimationType type = AnimationType.POPUP;
                    
                    tray.setAnimationType(type);
                    tray.setTitle("Notified...!");
                    tray.setMessage("Login Sucessfull...");
                    tray.setNotificationType(NotificationType.SUCCESS);
                    tray.showAndDismiss(Duration.millis(3000));
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/instagram_main.fxml"));
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add("/values/main.css");
//                main_stage.setMaximized(true);
                        main_stage.setScene(scene);
                        main_stage.initStyle(StageStyle.TRANSPARENT);
                        main_stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    TrayNotification tray = new TrayNotification();
                    AnimationType type = AnimationType.POPUP;
                    
                    tray.setAnimationType(type);
                    tray.setTitle("Alert...!");
                    tray.setMessage("Wrong Creadentials...");
                    tray.setNotificationType(NotificationType.ERROR);
                    tray.showAndDismiss(Duration.millis(3000));
                }
            }
        });
    }
    
    private void setLogin() {
        clearData();
        loginpane.setVisible(true);
        registerpane.setVisible(false);
        titlelabel.setText("Instagram Login");
        mainbutton.setText("Login");
    }
    
    private void setRegistation() {
        loginpane.setVisible(false);
        registerpane.setVisible(true);
        titlelabel.setText("New User Registation");
        mainbutton.setText("Register");
    }
    
    @FXML
    private void onRegistationClick(MouseEvent event) {
        setRegistation();
    }
    
    @FXML
    private void onBackClicked(MouseEvent event) {
        setLogin();
    }
    
    @FXML
    private void onClicked(MouseEvent event) {
        System.exit(0);
        
    }
    
    @FXML
    private void onLoginClick(MouseEvent event) {
        if (db_conn.checkLogins(useridtextfield.getText(), passwordtextfield.getText())) {
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            
            tray.setAnimationType(type);
            tray.setTitle("Notified...!");
            tray.setMessage("Login Sucessfull...");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(3000));
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/instagram_main.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/values/main.css");
//                main_stage.setMaximized(true);
                main_stage.setScene(scene);
                main_stage.initStyle(StageStyle.TRANSPARENT);
                main_stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            
            tray.setAnimationType(type);
            tray.setTitle("Alert...!");
            tray.setMessage("Wrong Creadentials...");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(3000));
        }
        
    }
    
    private void clearData() {
        useridregi.setText("");
        userpassregi.setText("");
        usernameregi.setText("");
        userpasscomregi.setText("");
        passwordtextfield.setText("");
        useridtextfield.setText("");
    }
    
    @FXML
    private void onRegistrationClick(MouseEvent event) {
        if (db_conn.setRegistation(useridregi.getText(), usernameregi.getText(), userpasscomregi.getText()) == true) {
            clearData();
            setLogin();
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            
            tray.setAnimationType(type);
            tray.setTitle("Notified...!");
            tray.setMessage("User Register Sucessfully......");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
    }
    
    @FXML
    private void onForgetClick(MouseEvent event) {
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle("Alert...!");
        tray.setMessage("Contact DataBase Administrator......");
        tray.setNotificationType(NotificationType.ERROR);
        tray.showAndDismiss(Duration.millis(3000));
    }
}
