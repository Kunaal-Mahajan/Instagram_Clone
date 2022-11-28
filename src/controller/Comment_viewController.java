
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Comment_viewController implements Initializable {

    @FXML
    private Label usercommentlabel;

    @FXML
    private Label user_id;
    @FXML
    private ImageView userprof;

    public static Label comment;
    public static Label id;
    public static ImageView proImg;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comment = usercommentlabel;
        id = user_id;
        proImg = userprof;
    }

}
