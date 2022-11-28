package controller;

import com.jfoenix.controls.JFXButton;
import db_conn.Db_Conn;
import db_conn.UserPostData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import static model.Main.main_stage;

public class Main_postController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField commenttextfield;
    @FXML
    private Label usernamelabel1;
    @FXML
    private VBox commentviewvbox;
    @FXML
    private JFXButton postButton;
    @FXML
    private ImageView userimageview1;
    @FXML
    private ImageView userimageview2;
    @FXML
    private Label usernamelabel2;
    @FXML
    private ImageView userpostimg;
    @FXML
    private ImageView likeimgico;
    @FXML
    private ImageView messageico;
    @FXML
    private ImageView sentico;
    @FXML
    private AnchorPane single_post_AnchorPane;
    @FXML
    private TextArea postdesc;

    public static Node node;
    public static String postpath;
    public String POSTID;
    boolean like;
    @FXML
    private JFXButton hits_algo_button;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        scrollPane.setVisible(false);
        postdesc.setPrefHeight(890);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        POSTID = new Db_Conn().getpostID(postpath.substring(2));
        postdesc.setText(getPostDesc(POSTID));
        userimageview1.setImage(new Image(new File(Db_Conn.FileDrive + Db_Conn.PROFILE_URL).toURI().toString()));
        userimageview2.setImage(new Image(new File(Db_Conn.FileDrive + Db_Conn.PROFILE_URL).toURI().toString()));
        loadComment();

        System.out.println("Post Path = " + postpath);

        usernamelabel1.setText("@" + Db_Conn.USER_ID);
        usernamelabel2.setText("@" + Db_Conn.USER_ID);
        commenttextfield.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                postComment();
            }
        });
        try {
            likeimgico.setImage(new Image(new File("src/img/icons8-favorite-50.png").toURI().toString()));
            userpostimg.setImage(new Image(new FileInputStream(postpath), 680.5, 967, false, false));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        postdesc.setEditable(false);
    }

    @FXML
    private void onPostClick(ActionEvent event) {
        postComment();
    }

    private String getPostDesc(String post_id) {
        String desc = "";
        for (UserPostData userPostData : Db_Conn.user_post_url) {
            if (userPostData.getPost_id().equals(post_id)) {
                desc = userPostData.getPost_desc();
            }
        }
        return desc;
    }

    private void postComment() {
        try {
            scrollPane.setVisible(true);
            postdesc.setPrefHeight(400);
            node = (Node) FXMLLoader.load(getClass().getResource("/view/comment_view.fxml"));
            Comment_viewController.comment.setText(commenttextfield.getText());
            Comment_viewController.id.setText("@" + Db_Conn.USER_ID);
            Comment_viewController.proImg.setImage(new Image(new FileInputStream(Db_Conn.FileDrive + Db_Conn.PROFILE_URL), 51, 43, false, false));
            commentviewvbox.getChildren().add(node);
            new Db_Conn().setpostComment(Db_Conn.USER_ID, POSTID, commenttextfield.getText());
            commenttextfield.clear();
        } catch (IOException ex) {
            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadComment() {
        try {
            commentviewvbox.getChildren().clear();
            new Db_Conn().getComment(POSTID);
            for (int i = 0; i < Db_Conn.postcomment.size(); i++) {
                node = (Node) FXMLLoader.load(getClass().getResource("/view/comment_view.fxml"));
                Comment_viewController.comment.setText(Db_Conn.postcomment.get(i).getComment());
                Comment_viewController.id.setText("@" + Db_Conn.postcomment.get(i).getUser_id());
                ResultSet rs = new Db_Conn().getUserProfUrl(Db_Conn.postcomment.get(i).getUser_id());

                try {
                    while (rs.next()) {
                        Comment_viewController.proImg.setImage(new Image(new FileInputStream(Db_Conn.FileDrive + rs.getString("profileurl")), 51, 43, false, false));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
                }

                commentviewvbox.getChildren().add(node);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void onBackClick(MouseEvent event) {
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

    @FXML
    private void onCommentClick(MouseEvent event) {
        postdesc.setPrefHeight(400);
        scrollPane.setVisible(true);
    }

    @FXML
    private void hitsAlgoButtonClick(ActionEvent event){
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("py_script/runFile.bat", "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
        }

        writer.println("cd py_script");
        writer.println("python Hashtag_Final.py " + postpath);

        writer.close();
        
//        String prg = "import sys";
//        BufferedWriter out = null;
//        String py_Script_path = Main.data_folder + "Hashtag/Hashtag_Final.py ";
//        
//        try {
//            out = new BufferedWriter(new FileWriter(py_Script_path));
//            out.write(prg);
//            out.close();
//            Process p = Runtime.getRuntime().exec("python " + py_Script_path + postpath);
//            
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String ret = in.readLine();
//            System.out.println("value is : "+ret);
//        } catch (IOException ex) {
//            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
//        }




        try
        {
            String s = null;

            // python Hashtag.py "F:/HITS_Algo/imgs/589b214c2900002600f25141.jpg"
            Process p = Runtime.getRuntime().exec("py_script/runFile.bat");

            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            //System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            //System.exit(-1);
        }           





//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/view/HITSUserInput.fxml"));
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add("/values/main.css");
////        scene.setFill(Color.TRANSPARENT);
//            main_stage.setMaximized(true);
//            main_stage.setScene(scene);
//            main_stage.initStyle(StageStyle.TRANSPARENT);
//            main_stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(Main_postController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
}
