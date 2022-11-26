package client;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import client.resources.Сalculations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private Text Info;

    @FXML
    private Text Info1;

    @FXML
    private Text Info2;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text Text;

    @FXML
    private ToggleGroup engine;

    @FXML
    private ComboBox<String> getComboBox;

    @FXML
    private Button getData;

    @FXML
    private DatePicker getDatePicker;

    @FXML
    private RadioButton getRadioButton1;

    @FXML
    private RadioButton getRadioButton2;

    @FXML
    private RadioButton getRadioButton3;

    @FXML
    private RadioButton getRadioButton4;

    @FXML
    private NumberTextField getTextFild1;

    @FXML
    private NumberTextField getTextFild2;

    @FXML
    private ToggleGroup human;

    @FXML
    void getEngine(ActionEvent event) {

    }

    @FXML
    void getHuman(ActionEvent event) {

        if(getRadioButton1.isSelected()){
            getRadioButton3.setVisible(false);
            getRadioButton4.setVisible(false);
            Text.setVisible(false);
        } else if (getRadioButton2.isSelected()) {
            getRadioButton3.setVisible(true);
            getRadioButton4.setVisible(true);
            Text.setVisible(true);
        }
    }
    @FXML
    void initialize() {
        getComboBox.setItems(FXCollections.observableArrayList("$","€"));
        getData.setOnAction(actionEvent -> {
            if (isInputValid()) {

                if (getRadioButton1.isSelected()) {
                    Сalculations calc = new Сalculations(
                            Integer.parseInt(getTextFild1.getText()),
                            getDatePicker.getValue(),
                            Integer.parseInt(getTextFild2.getText()),
                            getComboBox.getValue()
                    );
                    System.out.println(calc.PhysicalCalc());
                } else {
                    int text = Integer.parseInt(getTextFild2.getText());
                    System.out.println(text);

                }
            }
        });
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (getTextFild1.getText() == null || getTextFild1.getText().length() == 0) {
            errorMessage += "Не указан объем двигателя!\n";
        }
        if (getTextFild2.getText() == null || getTextFild2.getText().length() == 0) {
            errorMessage += "Не указана стоимость транспортного средства!\n";
        }
        if (getComboBox.getValue() == null ) {
            errorMessage += "Не указана валюта!\n";
        }
        LocalDate date = getDatePicker.getValue();
        if ( date == null ) {
            errorMessage += "Не указана дата выпуска!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Недопустимые поля");
            alert.setHeaderText("Пожалуйста, исправьте неверные поля");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}





