package controller;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import static model.Main.main_stage;

public class PostController implements Initializable {

//    public static String path = "";
    public String path;
    @FXML
    private ImageView img;
    @FXML
    private AnchorPane pane;
    public String POSTID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        pathtemp = PostController.path;
    }

    public ImageView getImg() {
        return img;
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
            main_stage.initStyle(StageStyle.TRANSPARENT);
            main_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
