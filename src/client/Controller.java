package client;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
            if(getRadioButton1.isSelected()) {
                Сalculations calc = new Сalculations(
                        Integer.parseInt(getTextFild1.getText()),
                        getDatePicker.getValue(),
                        Integer.parseInt(getTextFild2.getText())
                );
                System.out.println(calc.PhysicalCalc());
            }
            else {
                int text = Integer.parseInt(getTextFild2.getText());
                System.out.println(text);

            }

        });
    }

}





