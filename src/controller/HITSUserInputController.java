
package controller;

import com.jfoenix.controls.JFXButton;
import static controller.Main_postController.postpath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javax.swing.JFileChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.Main;
import model.FinalPrediction;
import static model.Main.main_stage;

public class HITSUserInputController implements Initializable {
    @FXML
    private Label mainTitleLabel;
    @FXML
    private JFXButton predictButton;
    @FXML
    private Label chancesPerc_text;
    @FXML
    private Label textMessage;
    @FXML
    private ScrollPane imageView_scrollpane;
    @FXML
    private ImageView algo_test_imageView;

    final DoubleProperty zoomProperty_imgView = new SimpleDoubleProperty(200);
    final DoubleProperty zoomProperty_graphView = new SimpleDoubleProperty(200);
    @FXML
    private ScrollPane graphImageView_scrollpane;
    @FXML
    private ImageView algo_test_imageView1;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customInitializer();
    }    
    private void customInitializer()
    {
//        chancesPerc_text.setVisible(false);
//        textMessage.setVisible(false);

        zoomProperty_imgView.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                algo_test_imageView.setFitWidth(zoomProperty_imgView.get() * 4);
                algo_test_imageView.setFitHeight(zoomProperty_imgView.get() * 3);
            }
            
        });

        imageView_scrollpane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty_imgView.set(zoomProperty_imgView.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty_imgView.set(zoomProperty_imgView.get() / 1.1);
                }
            }
        });
        
        zoomProperty_graphView.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                algo_test_imageView.setFitWidth(zoomProperty_graphView.get() * 4);
                algo_test_imageView.setFitHeight(zoomProperty_graphView.get() * 3);
            }
            
        });

        graphImageView_scrollpane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty_graphView.set(zoomProperty_graphView.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty_graphView.set(zoomProperty_graphView.get() / 1.1);
                }
            }
        });
        try {
            algo_test_imageView.setImage(new Image(new FileInputStream(postpath)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HITSUserInputController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainTitleLabel.setText("HITS Algorithm :");


  
    }
    
    @FXML
    private void predictButtonClick(ActionEvent event) throws Exception {
        String filename = File.separator+"tmp";
        final  FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(Main.data_folder+"imgs/"));

        fc.setTitle("Select Image for HITS Algorithm");

        File selFile = fc.showOpenDialog(Main.main_stage);;
        String selectedFileName = selFile.getAbsolutePath();
        algo_test_imageView.setImage(new Image(new File(selectedFileName).toURI().toString()));
        
//        FinalPrediction algorithm = new FinalPrediction();
//        String details = algorithm.Process(selectedFileName);
//        chancesPerc_text.setText(details);
//        textMessage.setVisible(true);
//        chancesPerc_text.setVisible(true);
        
    }

    @FXML
    private void onExitClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/instagram_main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/values/main.css");
//        scene.setFill(Color.TRANSPARENT);
            main_stage.setMaximized(true);
            main_stage.setScene(scene);
            main_stage.initStyle(StageStyle.TRANSPARENT);
            main_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
