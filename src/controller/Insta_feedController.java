package controller;

//import static controller.PostController.path;
import db_conn.Db_Conn;
import db_conn.UserPostData;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class Insta_feedController implements Initializable {

    public static String path = "";
    int column = 0;
    int row = 1;
    int count = 0;
    @FXML
    private GridPane grid;
    Db_Conn db_conn;
    public static Boolean check = false;
    public static String user_id, user_prof_img;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    List<UserPostData> user_feed_url_temp = new ArrayList<>();
    List<UserPostData> user_post_url_temp = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_feed_url_temp.addAll(Db_Conn.user_feed_url);
        user_post_url_temp.addAll(Db_Conn.user_post_url);
//        System.out.println(user_feed_url_temp.size());

        for (UserPostData userPostData : user_feed_url_temp) {
            System.out.print(" " + userPostData.getPost_id());
        }
//        Collections.reverse(user_feed_url_temp);
        Collections.reverse(user_post_url_temp);
        System.out.println("");
        for (UserPostData userPostData : user_feed_url_temp) {
            System.out.print(" " + userPostData.getPost_id());
        }

        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        if (check == false) //
        {
            setPost(grid);
        } else {
            setFeedPost(grid);
        }
        grid.setStyle("-fx-background-color:transparent");
    }

    public static void getData(String user_id, String user_prof_img) {
        Insta_feedController.user_id = user_id;
        Insta_feedController.user_prof_img = user_prof_img;
    }

    private void setFeedPost(GridPane grid) {
        try {
            grid.getChildren().clear();
            int count = 0;
            for (int i = 0; i < user_feed_url_temp.size(); i++) {
                count++;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/post.fxml"));
                Pane pane = fxmlLoader.load();
                PostController post = fxmlLoader.getController();
//                post.setPath(user_feed_url_temp.get(i));
                post.getImg().setImage(new Image(new FileInputStream(user_feed_url_temp.get(i).getUser_post_url()), 280, 420, false, false));
                post.setPath(user_feed_url_temp.get(i).getUser_post_url());
                if (column == 5) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(pane, new Insets(3));
            }
        } catch (IOException e) {

        }
    }

    private void setPost(GridPane grid) {
        grid.getChildren().clear();
        int count = 0;
        for (int i = 0; i < user_post_url_temp.size(); i++) {
            try {
                count++;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/feed_post.fxml"));
                Pane pane = fxmlLoader.load();
                Feed_postController feedpost = fxmlLoader.getController();
                ResultSet rs = new Db_Conn().getUserProfUrl(user_post_url_temp.get(i).getUser_id());
                try {
                    while (rs.next()) {
                        feedpost.getUserprof().setImage(new Image(new FileInputStream(Db_Conn.FileDrive + rs.getString("profileurl")), 48, 48, false, false));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
                }
                feedpost.getUser_id().setText("@" + user_post_url_temp.get(i).getUser_id());
                if (!"".equals(user_post_url_temp.get(i).getPost_desc())) {
                    feedpost.getProf_ico_1().setImage(new Image(new FileInputStream(Db_Conn.FileDrive + "\\supportdata\\userprof\\filename (2).jpg"), 22, 24, false, false));
                    feedpost.getProf_ico_2().setImage(new Image(new FileInputStream(Db_Conn.FileDrive + "\\supportdata\\userprof\\filename (3).jpg"), 48, 48, false, false));
                    feedpost.getProf_ico_3().setImage(new Image(new FileInputStream(Db_Conn.FileDrive + "\\supportdata\\userprof\\filename (4).jpg"), 48, 48, false, false)
                    );
//                    if (user_post_url_temp.get(i).getPost_desc().length() > 10) {
                    feedpost.getPost_desc().setText(user_post_url_temp.get(i).getPost_desc());
//                    } else {
//                        feedpost.getPost_desc().setText(user_post_url_temp.get(i).getPost_desc().substring(0, 5) + "...");
//                    }
                } else {
                    feedpost.getProf_ico_1().setVisible(false);
                    feedpost.getProf_ico_2().setVisible(false);
                    feedpost.getProf_ico_3().setVisible(false);
                }
                feedpost.getImg_url().setImage(new Image(new FileInputStream(user_post_url_temp.get(i).getUser_post_url()), 590, 500, false, false));
                feedpost.setPath(user_post_url_temp.get(i).getUser_post_url());
                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
//             GridPane.setMargin(pane, new Insets(20));
                GridPane.setMargin(pane, new Insets(10, 0, 10, 490));
            } catch (IOException ex) {
                Logger.getLogger(Insta_feedController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
