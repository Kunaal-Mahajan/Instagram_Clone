/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db_conn.Db_Conn;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import static model.Main.main_stage;

/**
 * FXML Controller class
 *
 * @author Kunaal
 */
public class HitsalgoController implements Initializable {

    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private ScatterChart<String, Number> scatterchart;
    @FXML
    private AreaChart<String, Number> areachart;
    @FXML
    private Tab barcharttab;
    @FXML
    private Tab scattercharttab;
    @FXML
    private Tab areacharttab;
    @FXML
    private TabPane tabpane;
    @FXML
    private Tab piecharttab;
    @FXML
    private PieChart piechart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        barchart.getData().clear();
        barchart.setTitle("BARCHART - HashTags Analys(Using Hits Algorithm) - Top 20 HashTags");
        try {
            ResultSet rs = new Db_Conn().getTag();
            while (rs.next()) {
                XYChart.Series barchartdata = new XYChart.Series();
                barchartdata.setName(rs.getString("hashtags"));
                barchartdata.getData().add(new XYChart.Data(rs.getString("hashtags"), Integer.parseInt(rs.getString("tags_count"))));
                barchart.getData().addAll(barchartdata);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HitsalgoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue.equals(barcharttab)) {
                    barchart.getData().clear();
                    try {
                        ResultSet rs = new Db_Conn().getTag();
                        while (rs.next()) {
                            XYChart.Series barchartdata = new XYChart.Series();
                            barchartdata.setName(rs.getString("hashtags"));
                            barchartdata.getData().add(new XYChart.Data(rs.getString("hashtags"), Integer.parseInt(rs.getString("tags_count"))));
                            barchart.getData().addAll(barchartdata);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(HitsalgoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (newValue.equals(scattercharttab)) {

                    try {
                        scatterchart.getData().clear();

                        scatterchart.setTitle("SCATTERCHART - HashTags Analys(Using Hits Algorithm) - Top 20 HashTags");
                        scatterchart.setStyle("");
                        ResultSet rs = new Db_Conn().getTag();
                        while (rs.next()) {
                            XYChart.Series seriesApril = new XYChart.Series();
                            seriesApril.setName(rs.getString("hashtags"));
                            seriesApril.getData().add(new XYChart.Data(rs.getString("hashtags"), Integer.parseInt(rs.getString("tags_count"))));
                            scatterchart.getData().add(seriesApril);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(HitsalgoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (newValue.equals(piecharttab)) {
                    piechart.getData().clear();
                    try {
                        piechart.setTitle("PIECHART - HashTags Analys(Using Hits Algorithm) - Top 20 HashTags");
                        ResultSet rs = new Db_Conn().getTag();
                        ObservableList<PieChart.Data> pieChartData
                                = FXCollections.observableArrayList();
                        while (rs.next()) {
                            pieChartData.add(new PieChart.Data(rs.getString("hashtags"), Integer.parseInt(rs.getString("tags_count"))));
                        }
                        piechart.setData(pieChartData);
//                        piechart.setTitle("Hits");
                    } catch (SQLException ex) {
                        Logger.getLogger(HitsalgoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (newValue.equals(areacharttab)) {
                    try {
                        areachart.getData().clear();
                        areachart.setTitle("AREACHART - HashTags Analys(Using Hits Algorithm) - Top 20 HashTags");
                        ResultSet rs = new Db_Conn().getTag();
                        while (rs.next()) {
                            XYChart.Series seriesApril = new XYChart.Series();
                            seriesApril.setName(rs.getString("hashtags"));
                            seriesApril.getData().add(new XYChart.Data(rs.getString("hashtags"), Integer.parseInt(rs.getString("tags_count"))));
                            areachart.getData().add(seriesApril);
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(HitsalgoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

    }

    @FXML
    private void onCloseClick(MouseEvent event) {
        System.exit(0);
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

}
