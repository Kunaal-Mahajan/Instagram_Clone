/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import com.jfoenix.controls.JFXButton;
import db_conn.Db_Conn;
import db_conn.UserPostData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.Main;
import org.apache.commons.io.FileUtils;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class PhotosUploadController implements Initializable {

    @FXML
    private ImageView ico2;
    @FXML
    private ImageView ico1;
    @FXML
    private ImageView post_pic;
    @FXML
    private TextArea post_desc;
    @FXML
    private JFXButton fileselect;
    @FXML
    private JFXButton postuploadbullon;
    String FILEPATH = "", hashtags = "";
    File selectedFile = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectFile();
        FileChooser fileChooser = new FileChooser();
//        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
//Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
//        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG);
        fileselect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    selectedFile = fileChooser.showOpenDialog(Main.main_stage);
                    FILEPATH = selectedFile.getAbsolutePath();
                    postUpload();
                    post_pic.setImage(new Image(new FileInputStream(FILEPATH)));
                    centerImage(post_pic);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PhotosUploadController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        postuploadbullon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("pressed");
                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.POPUP;

                tray.setAnimationType(type);
                tray.setTitle("Alert..!");
                
                UserPostData userpost = new UserPostData();
                userpost.setPost_id("0");
                userpost.setUser_id(Db_Conn.USER_ID);
                userpost.setUser_post_url(FILEPATH);
                userpost.setPost_desc(post_desc.getText());
                userpost.setHashtags("#New");
                Db_Conn.user_post_url.add(userpost);
                Db_Conn.user_feed_url.add(userpost);

                File source = selectedFile; //new File(selectedFile);

                // Creating two stream
                // one input and other output
                FileInputStream fis = null;
                FileOutputStream fos = null;

                // Try block to check for exceptions
                try {

                    // Initializing both the streams with
                    // respective file directory on local machine
                    // Custom directory path on local machine
                    fis = new FileInputStream(source.getAbsoluteFile());

                    // Custom directory path on local machine
                    fos = new FileOutputStream(Main.data_folder + "post/" + selectedFile.getName());
                    
                    int c;

                    // Condition check
                    // Reading the input file till there is input
                    // present
                    while ((c = fis.read()) != -1) {

                        // Writing to output file of the specified
                        // directory
                        fos.write(c);
                    }

                    // By now writing to the file has ended, so
                    // Display message on the console
                    System.out.println(
                            "copied the file successfully");
                    File dest = new File(Main.data_folder + "post/" + selectedFile.getName());
//                    System.out.println(dest.getAbsolutePath());
                    new Db_Conn().upLoadPost(Db_Conn.USER_ID, dest.getAbsolutePath(), hashtags, post_desc.getText());

                } // Optional finally keyword but is good practice to
                catch (IOException ex) {
                    Logger.getLogger(PhotosUploadController.class.getName()).log(Level.SEVERE, null, ex);
                } // empty the occupied space is recommended whenever
                // closing files,connections,streams
                finally {

                    // Closing the streams
                    if (fis != null) {

                        try {
                            // Closing the fileInputStream
                            fis.close();
                        } catch (IOException ex) {
                            Logger.getLogger(PhotosUploadController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (fos != null) {

                        try {
                            // Closing the fileOutputStream
                            fos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(PhotosUploadController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                tray.setMessage("Post Uploaded Successfully Sucessfully...");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(3000));
                selectFile();

            }
        });

    }

    private static void copy(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
//        try {
        is = new FileInputStream(src);
        os = new FileOutputStream(dest);

        // buffer size 1K
        byte[] buf = new byte[1024];

        int bytesRead;
        while ((bytesRead = is.read(buf)) > 0) {
            os.write(buf, 0, bytesRead);
        }
//        } finally {
//            is.close();
//            os.close();
//        }
    }

    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }

    private void postUpload() {
        ico1.setVisible(false);
        ico2.setVisible(false);
        post_desc.setVisible(true);
        post_pic.setVisible(true);
        postuploadbullon.setVisible(true);
        fileselect.setVisible(false);
    }

    private void selectFile() {
        ico1.setVisible(true);
        ico2.setVisible(true);
        post_desc.setVisible(false);
        post_pic.setVisible(false);
        postuploadbullon.setVisible(false);
        fileselect.setVisible(true);
    }

}
