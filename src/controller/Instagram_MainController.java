package controller;

import com.gn.GNAvatarView;
import static controller.Main_postController.node;
import db_conn.Db_Conn;
import db_conn.HashTags;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import static model.Main.main_stage;
import org.controlsfx.control.PopOver;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class Instagram_MainController implements Initializable {

    @FXML
    private Label feedlabel;
    @FXML
    private Label explorelable;
    @FXML
    private ImageView feedicon;
    @FXML
    private ImageView exploreicon;
    @FXML
    private ImageView notificationicon;
    @FXML
    private ImageView messageicon;
    @FXML
    private ImageView dirrecticon;
    @FXML
    private ImageView settingicon;
    @FXML
    private ImageView logouticon;
    @FXML
    private ImageView user_prof_img;
    @FXML
    private Label user_name_label;
    @FXML
    private Label user_id_lable;
    @FXML
    private Label post_count_label;
    @FXML
    private Label user_following_label;
    @FXML
    private Label user_follower_label;
    @FXML
    private VBox box;
    @FXML
    private ImageView message;
    @FXML
    private ImageView notification;
    @FXML
    private Label mainLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Collections.reverse(Db_Conn.user_feed_url);
//        Collections.reverse(Db_Conn.user_post_url);
        nodeChange("insta_feed", false);
        user_prof_img.setImage(new Image(new File(Db_Conn.FileDrive + Db_Conn.PROFILE_URL).toURI().toString()));
        user_follower_label.setText(Db_Conn.FOLLOWER);
        user_following_label.setText(Db_Conn.FOLLOWING);
        user_id_lable.setText("@" + Db_Conn.USER_ID);
        user_name_label.setText(Db_Conn.USER_NAME);
    }

    @FXML
    private void onExitClick(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void onFeedExit(MouseEvent event) {
//        feedicon.setImage(new Image(new File("src/img/grid-black.png").toURI().toString()));
    }

    @FXML
    private void onFeedEntered(MouseEvent event) {
//        feedicon.setImage(new Image(new File("src/img/grid-pink.png").toURI().toString()));
    }

    @FXML
    private void onsearchExit(MouseEvent event) {

//        exploreicon.setImage(new Image(new File("src/img/icons8-search-50 (2).png").toURI().toString()));
    }

    @FXML
    private void onSearchEntered(MouseEvent event) {
//        exploreicon.setImage(new Image(new File("src/img/icons8-search-50 (3).png").toURI().toString()));
    }

    @FXML
    private void onNotificationExited(MouseEvent event) {
//        notificationicon.setImage(new Image(new File("src/img/icons8-notification-50.png").toURI().toString()));
    }

    @FXML
    private void onNotificationEntered(MouseEvent event) {
//        notificationicon.setImage(new Image(new File("src/img/icons8-notification-50 (1).png").toURI().toString()));
    }

    @FXML
    private void onMessageExited(MouseEvent event) {
//        messageicon.setImage(new Image(new File("src/img/icons8-message-50.png").toURI().toString()));
    }

    @FXML
    private void onMessageEntered(MouseEvent event) {
//        messageicon.setImage(new Image(new File("src/img/icons8-message-64.png").toURI().toString()));
    }

    @FXML
    private void onSettingExited(MouseEvent event) {
//        settingicon.setImage(new Image(new File("src/img/icons8-predictive-chart-64.png").toURI().toString()));
    }

    @FXML
    private void onSettingEntered(MouseEvent event) {
//        settingicon.setImage(new Image(new File("src/img/icons8-predictive-chart-64 (1).png").toURI().toString()));
    }

    @FXML
    private void onLogoutExited(MouseEvent event) {
//        logouticon.setImage(new Image(new File("src/img/icons8-logout-64.png").toURI().toString()));
    }

    @FXML
    private void onSentExited(MouseEvent event) {
//        dirrecticon.setImage(new Image(new File("src/img/icons8-hashtag-large-48.png").toURI().toString()));
    }

    @FXML
    private void onSentEntered(MouseEvent event) {
//        dirrecticon.setImage(new Image(new File("src/img/icons8-hashtag-large-48 (1).png").toURI().toString()));
    }

    @FXML
    private void onlogoutEntered(MouseEvent event) {
//        logouticon.setImage(new Image(new File("src/img/icons8-logout-64 (1).png").toURI().toString()));
    }

    @FXML
    private void onlogoutClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/values/main.css");
//        scene.setFill(Color.TRANSPARENT);
            main_stage.setMaximized(true);
            main_stage.setScene(scene);
            main_stage.initStyle(StageStyle.TRANSPARENT);
            main_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Instagram_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nodeChange(String filleName, Boolean check) {
        try {
            box.getChildren().clear();
            Insta_feedController.check = check;
            node = (Node) FXMLLoader.load(getClass().getResource("/view/" + filleName + ".fxml"));
            box.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(Instagram_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onPhotoAdd(ActionEvent event) {
        try {
            box.getChildren().clear();
            node = (Node) FXMLLoader.load(getClass().getResource("/view/" + "photosUpload" + ".fxml"));
            box.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(Instagram_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onFeedClick(MouseEvent event) {
        mainLabel.setText("Feeds");
        nodeChange("insta_feed", true);
    }

    @FXML
    private void onExploreClick(MouseEvent event) {
        mainLabel.setText("Posts");
        nodeChange("insta_feed", false);
    }

    @FXML
    private void onHitsClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/hitsalgo.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/values/main.css");
//        scene.setFill(Color.TRANSPARENT);
            main_stage.setMaximized(true);
            main_stage.setScene(scene);
            main_stage.initStyle(StageStyle.TRANSPARENT);
            main_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Instagram_MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private PopOver pop = new PopOver();

    @FXML
    private void onNotificationClick(MouseEvent event) {
        if (!pop.isShowing()) {
            GNAvatarView avatar1 = new GNAvatarView();
            GNAvatarView avatar2 = new GNAvatarView();
            GNAvatarView avatar3 = new GNAvatarView();
            GNAvatarView avatar4 = new GNAvatarView();

            avatar1.setImage(new Image(getClass().getResource("/img/filename (2).jpg").toExternalForm()));
            avatar2.setImage(new Image(getClass().getResource("/img/filename (4).jpg").toExternalForm()));
            avatar3.setImage(new Image(getClass().getResource("/img/user.jpg").toExternalForm()));
            avatar4.setImage(new Image(getClass().getResource("/img/filename (3).jpg").toExternalForm()));

            ObservableList<AlertCell> list = FXCollections.observableArrayList(
                    new AlertCell(avatar1, "sham patil", "Sham Patil Uploaded Post", "24 minutes ago"),
                    new AlertCell(avatar2, "ram jain", "Ram Jain Liked Your Post", "today"),
                    new AlertCell(avatar3, "Admin", "Admin Liked Sham Post", "3 seconds ago"),
                    new AlertCell(avatar4, "roshani patel", "Roshani Patel Uploaded Post", "3 seconds ago")
            );

            Separator top = new Separator();
            Separator bottom = new Separator();

            Label message = new Label("Notification");
            Label count = new Label("4 News");
            count.getStyleClass().add("text-success");
            GridPane title = new GridPane();
            title.setMinHeight(50D);

            title.setAlignment(Pos.CENTER);
            title.add(message, 0, 0);
            title.add(count, 1, 0);
            GridPane.setHalignment(count, HPos.RIGHT);

            ListView<AlertCell> listView = new ListView<>();

            listView.getItems().addAll(list);
            listView.getStyleClass().add("border-0");

            Button btn = new Button("Read all messages");
            btn.getStyleClass().add("btn-flat");

            VBox root = new VBox(title, top, listView, bottom, btn);
            root.setAlignment(Pos.CENTER);
            root.setPrefSize(300, 300);
            title.setPrefWidth(root.getPrefWidth());
            count.setPrefWidth(root.getPrefWidth());
            message.setPrefWidth(root.getPrefWidth());
            count.setAlignment(Pos.CENTER_RIGHT);
            title.setPadding(new Insets(0, 25, 0, 25));
            btn.setPrefWidth(root.getPrefWidth());

//        pop.getRoot().getStylesheets().add(getClass().getResource("/com/gn/theme/css/poplight.css").toExternalForm());
            pop.setContentNode(root);
            pop.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
            pop.setArrowIndent(0);
            pop.setArrowSize(0);
            pop.setCloseButtonEnabled(false);
            pop.setHeaderAlwaysVisible(false);
            pop.setCornerRadius(0);
            pop.setAutoFix(true);
            pop.show(this.notification);
        } else {
            pop.hide();
        }
    }

    @FXML
    private void onMessageClick(MouseEvent event) {
        if (!pop.isShowing()) {
            GNAvatarView avatar1 = new GNAvatarView();
            GNAvatarView avatar2 = new GNAvatarView();
            GNAvatarView avatar3 = new GNAvatarView();
            GNAvatarView avatar4 = new GNAvatarView();

            avatar1.setImage(new Image(getClass().getResource("/img/filename (2).jpg").toExternalForm()));
            avatar2.setImage(new Image(getClass().getResource("/img/filename (4).jpg").toExternalForm()));
            avatar3.setImage(new Image(getClass().getResource("/img/user.jpg").toExternalForm()));
            avatar4.setImage(new Image(getClass().getResource("/img/filename (3).jpg").toExternalForm()));

            ObservableList<AlertCell> list = FXCollections.observableArrayList(
                    new AlertCell(avatar2, "ram jain", "Hii", "today"),
                    new AlertCell(avatar1, "sham patil", "Nice Look!!", "24 minutes ago"),
                    new AlertCell(avatar4, "roshani patel", "Whre Are You !!", "3 seconds ago"),
                    new AlertCell(avatar3, "Admin", "Good...", "43 seconds ago")
            );

            Separator top = new Separator();
            Separator bottom = new Separator();

            Label message = new Label("Messages");
            Label count = new Label("4 Message");
            count.getStyleClass().add("text-success");
            GridPane title = new GridPane();
            title.setMinHeight(40D);

            title.setAlignment(Pos.CENTER);
            title.add(message, 0, 0);
            title.add(count, 1, 0);
            GridPane.setHalignment(count, HPos.RIGHT);

            ListView<AlertCell> listView = new ListView<>();

            listView.getItems().addAll(list);
            listView.getStyleClass().add("border-0");

            Button btn = new Button("Read all messages");
            btn.getStyleClass().add("btn-flat");

            VBox root = new VBox(title, top, listView, bottom, btn);
            root.setAlignment(Pos.CENTER);
            root.setPrefSize(300, 300);
            title.setPrefWidth(root.getPrefWidth());
            count.setPrefWidth(root.getPrefWidth());
            message.setPrefWidth(root.getPrefWidth());
//            message.setPadding(new Insets(10, 0, 0, 0));
            count.setAlignment(Pos.CENTER_RIGHT);
//            count.setPadding(new Insets(10, 0, 10, 0));
            title.setPadding(new Insets(0, 25, 0, 25));
            btn.setPrefWidth(root.getPrefWidth());

//        pop.getRoot().getStylesheets().add(getClass().getResource("/com/gn/theme/css/poplight.css").toExternalForm());
            pop.setContentNode(root);
            pop.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
            pop.setArrowIndent(0);
            pop.setArrowSize(0);
            pop.setCloseButtonEnabled(false);
            pop.setHeaderAlwaysVisible(false);
            pop.setCornerRadius(0);
            pop.setAutoFix(true);
            pop.show(this.message);
        } else {
            pop.hide();
        }
    }

    @FXML
    private void onHashTagsClick(MouseEvent event) {
        HashTags hashtags = new HashTags();
        hashtags.getHashTags();
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        tray.setAnimationType(type);
        tray.setTitle("Alert..!");
        tray.setMessage("Hash Tags Collected Successfully...");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }

    @FXML
    private void onmessageClick(MouseEvent event) {
//        String line = "";
//        String splitBy = ",";
//        try {
//            int count = 0, tag = 145;
//            //parsing a CSV file into BufferedReader class constructor  
//            BufferedReader br = new BufferedReader(new FileReader("src\\controller\\data_alex.csv"));
//            while ((line = br.readLine()) != null) //returns a Boolean value  
//            {
//                if (count > 20) {
//                    break;
//                }
//                if (tag < 165) {
//                    String[] employee = line.split(splitBy);    // use comma as separator  
//                    new Db_Conn().setpostData("rk@546","\\\\supportdata\\\\post\\\\till-daling-G778GRNQjRI-unsplash.jpg", "", employee[5]);
//                    count++;
//                }
//                tag++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
