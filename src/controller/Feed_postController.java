/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import static controller.PostController.path;
import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static model.Main.main_stage;

/**
 * FXML Controller class
 *
 * @author Kunaal
 */
public class Feed_postController implements Initializable {

    public String path;
    boolean like;

    @FXML
    private ImageView likeimgico;
    @FXML
    private ImageView messageico;
    @FXML
    private ImageView sentico;
    @FXML
    private ImageView img_url;
    @FXML
    private ImageView userprof;
    @FXML
    private Label user_id;
    @FXML
    private Label post_desc;
    @FXML
    private ImageView prof_ico_1;
    @FXML
    private ImageView prof_ico_3;
    @FXML
    private ImageView prof_ico_2;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public ImageView getImg_url() {
        return img_url;
    }

    public ImageView getProf_ico_1() {
        return prof_ico_1;
    }

    public ImageView getProf_ico_3() {
        return prof_ico_3;
    }

    public ImageView getProf_ico_2() {
        return prof_ico_2;
    }

    public Label getPost_desc() {
        return post_desc;
    }

    public void setPost_desc(Label post_desc) {
        this.post_desc = post_desc;
    }

    public void setImg_url(ImageView img_url) {
        this.img_url = img_url;
    }

    public String getPath() {
        return path;
    }

    public ImageView getUserprof() {
        return userprof;
    }

    public Label getUser_id() {
        return user_id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @FXML
    private void onLikeClick(MouseEvent event) {
        if (like == false) {
            likeimgico.setImage(new Image(new File("src/img/icons8-heart-50.png").toURI().toString()));
            like = true;
        } else if (like == true) {
            likeimgico.setImage(new Image(new File("src/img/icons8-favorite-50.png").toURI().toString()));
            like = false;
        }
    }

    @FXML
    private void onMessageExited(MouseEvent event) {
        messageico.setImage(new Image(new File("src/img/icons8-topic-50.png").toURI().toString()));
    }

    @FXML
    private void onMessageEntered(MouseEvent event) {
        messageico.setImage(new Image(new File("src/img/icons8-topic-50 (1).png").toURI().toString()));
    }

    @FXML
    private void onSentExited(MouseEvent event) {
        sentico.setImage(new Image(new File("src/img/icons8-sent-50 (2).png").toURI().toString()));
    }

    @FXML
    private void onSentEntered(MouseEvent event) {
        sentico.setImage(new Image(new File("src/img/icons8-sent-50.png").toURI().toString()));
    }

    @FXML
    private void onPostClick(MouseEvent event) {
        try {
            Main_postController.postpath = path;
            Parent root = FXMLLoader.load(getClass().getResource("/view/main_post.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/values/main.css");
            main_stage.setMaximized(true);
            main_stage.setScene(scene);
            main_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Feed_postController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
