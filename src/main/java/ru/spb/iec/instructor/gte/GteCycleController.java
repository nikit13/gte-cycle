package ru.spb.iec.instructor.gte;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.spb.iec.instructor.gte.util.GteFunctions;

public class GteCycleController implements Initializable {

    private final double R = 287.5;

    private final double k = 1.4;

    private final double msa_0_0 = 288.15;

    private final double msa_0_1 = 1.01325;

    @FXML
    Stage mainStage;

    @FXML
    OutputFieldsGroup outputFields;

    @FXML
    Slider gasTemperatureSlider;

    @FXML
    Label pressureRatioLabel;

    @FXML
    Label gasTemperatureLabel;

    @FXML
    Slider pressureRatioSlider;

    @FXML
    LineChart<Double, Double> graphic;

    private double gasTemperature;

    private double pressureRatio;

    @FXML
    public void closeApplication() throws Exception {
        mainStage.close();
    }

    @FXML
    public void showAboutPage() throws IOException {
        // TODO доделать
        // ButtonBar.ButtonData.OK_DONE;
        Dialog<Object> dialog = new Dialog<>();
        dialog.initModality(Modality.WINDOW_MODAL);
        // dialog.initOwner(mainStage);
        dialog.setTitle("Информация");
        dialog.setResizable(true);
        dialog.setOnCloseRequest(e -> dialog.close());
        dialog.setDialogPane(FXMLLoader.load(getClass().getResource("AboutDialog.fxml")));
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/pictures/info28.png")));
        dialog.showAndWait();
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeGasTemperature(gasTemperatureSlider.valueProperty().get());
        this.gasTemperatureSlider.valueProperty()
                .addListener((observable, oldValue, newValue) -> changeGasTemperature(newValue.doubleValue()));
        changePressureRatio(pressureRatioSlider.valueProperty().get());
        this.pressureRatioSlider.valueProperty()
                .addListener((observable, oldValue, newValue) -> changePressureRatio(newValue.doubleValue()));
    }

    public ReadOnlyDoubleProperty sceneWidthProperty() {
        return mainStage.widthProperty();
    }

    public ReadOnlyDoubleProperty sceneHeightProperty() {
        return mainStage.heightProperty();
    }

    public double getSceneWidth() {
        return sceneWidthProperty().get();
    }

    public double getSceneHeight() {
        return sceneHeightProperty().get();
    }

    public void changeGasTemperature(double newValue) {
        this.gasTemperature = newValue;
        this.gasTemperatureLabel.textProperty().set("Tг = " + String.format("%.1f", newValue) + " K");
    }

    public void changePressureRatio(double newValue) {
        this.pressureRatio = newValue;
        this.pressureRatioLabel.textProperty().set("\u03C0k = " + String.format("%.1f", newValue));
    }

    public void setLoText(String text) {
        outputFields.loText.textProperty().set(text);
    }

    public void setQ1Text(String text) {
        outputFields.qOneText.textProperty().set(text);
    }

    public void setQ2Text(String text) {
        outputFields.qTwoText.textProperty().set(text);
    }

    public void setN(String text) {
        outputFields.nuText.textProperty().set(text);
    }

    @FXML
    public void calculateGte() {
        Series<Double, Double> resultSeries = new Series<>();

        final double T = msa_0_0;
        double p_i = msa_0_1;
        double v_i = (R * T) / (p_i * 1e5);
        double pv_k = p_i * 1e5 * Math.pow(v_i, k);

        for (double i = 1; i <= pressureRatio; i += 0.25) {
            p_i = i * 1e5;
            v_i = Math.pow(pv_k / p_i, 1 / k);
            double x1 = 0.5 * v_i;
            double y1 = i;

            resultSeries.getData().add(new Data<>(x1, y1));
        }

        double Tg = gasTemperature;
        v_i = 289 * Tg / p_i;
        pv_k = p_i * Math.pow(v_i, k);
        for (double i = p_i / 1e5; i >= 1; i -= 0.25) {
            p_i = i * 1e5;
            v_i = Math.pow(pv_k / p_i, 1 / k);
            double x1 = 0.5 * v_i;
            double y1 = i;
            resultSeries.getData().add(new Data<>(x1, y1));
        }

        // соединяем последнюю точку графика с первой
        if (!resultSeries.getData().isEmpty()) {
            Data<Double, Double> point = resultSeries.getData().get(0);
            resultSeries.getData().add(new Data<>(point.getXValue(), point.getYValue()));
        }

        resultSeries.setName(
                "T = " + String.format("%.1f", gasTemperature) + ", \u03C0k = " + String.format("%.1f", pressureRatio));
        graphic.getData().add(resultSeries);

        setLoText(String.format("%.3f", GteFunctions.calculateLc(pressureRatio, gasTemperature, k) / 1000.0));
        setQ1Text(String.format("%.3f", GteFunctions.calculateQ1(pressureRatio, gasTemperature, k) / 1000.0));
        setQ2Text(String.format("%.3f", GteFunctions.calculateQ2(pressureRatio, gasTemperature, k) / 1000.0));
        setN(String.format("%.3f", GteFunctions.calculateKpd(pressureRatio, k)));
    }

    @FXML
    public void clearGraphic() {
        graphic.getData().clear();
    }
}
