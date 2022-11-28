package model;

import db_conn.Db_Conn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static String data_folder = Db_Conn.FileDrive + "/supportdata/"; 
    public static Stage main_stage;

    @Override
    public void start(Stage stage) throws Exception {
        main_stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/values/main.css");
//        scene.setFill(Color.TRANSPARENT);
        Main.main_stage.setMaximized(true);
        Main.main_stage.setScene(scene);
        Main.main_stage.initStyle(StageStyle.TRANSPARENT);
        Main.main_stage.show();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new LoadFeed());
        t1.start();
        launch(args);
    }

}

class LoadFeed implements Runnable {

    @Override
    public void run() {
        new Db_Conn().loadFeedPostData();
    }
}
