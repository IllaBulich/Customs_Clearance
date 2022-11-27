package client;

import client.resources.Сalculations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller {
    Сalculations calc;
    float well = (float) 0.9688;
    String[] list = new String[]{
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"};
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
    private ComboBox<String> getMonth;
    @FXML
    private ComboBox<String> getYear;

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

        if (getRadioButton1.isSelected()) {
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
        getComboBox.setItems(FXCollections.observableArrayList("$", "€"));
        getComboBox.setValue("€");

        getMonth.setItems(FXCollections.observableArrayList(list));
        getMonth.setValue("Январь");

        LocalDate date = LocalDate.now();

        for (int i = date.getYear(); i > 1951; i--) {
            getYear.getItems().add(String.valueOf(i));
        }
        getYear.setValue(String.valueOf(date.getYear()));
        getData.setOnAction(actionEvent -> {
            if (isInputValid()) {
                if (getRadioButton1.isSelected()) {
                    Physical(date);
                } else {
                    Legal(date);

                }
            }
        });
    }

    private void Physical(LocalDate date) {
        try {
            calc = new Сalculations(
                    (getComboBox.getValue() == "$") ? (int) (Integer.parseInt(getTextFild2.getText()) * well) : Integer.parseInt(getTextFild2.getText()),
                    date.getYear() - Integer.parseInt(getYear.getValue()) + (float) (date.getMonthValue() - intMonth(getMonth.getValue())) / 12,
                    Integer.parseInt(getTextFild1.getText())

            );

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        int cost = calc.PhysicalCalc();
        Info.setVisible(true);
        Info2.setVisible(false);
        Info1.setVisible(false);
        Info.setText("Сумма таможенной пошлины составит: " + cost + " € (" + (int) (cost / well) + " $)");
    }

    private void Legal(LocalDate date) {
        int price =(getComboBox.getValue() == "$") ? (int) (Integer.parseInt(getTextFild2.getText()) * well) : Integer.parseInt(getTextFild2.getText());
        try {
            calc = new Сalculations(
                    price,
                    date.getYear() - Integer.parseInt(getYear.getValue()) + (float) (date.getMonthValue() - intMonth(getMonth.getValue())) / 12,
                    Integer.parseInt(getTextFild1.getText())

            );

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        int cost=0;
        if (getRadioButton3.isSelected()) {
            cost = calc.LegalPetrol("petrol");
        } else if (getRadioButton4.isSelected()) {
            cost = calc.LegalPetrol("diesel");
        }
        int NDS = (int) ((cost + price)*0.2);
        Info.setVisible(true);
        Info2.setVisible(true);
        Info1.setVisible(true);
        Info.setText("Сумма таможенной пошлины составит: " + cost + " € (" + (int) (cost / well) + " $)");
        Info1.setText("Сумма НДС: " + NDS +" € (" + (int) (NDS / well) + " $)");
        Info2.setText("Итого: " + (cost+NDS) + " € (" + (int) ((cost+NDS) / well) + " $)");

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (getTextFild1.getText() == null || getTextFild1.getText().length() == 0) {
            errorMessage += "Не указан объем двигателя!\n";
        }
        if (getTextFild2.getText() == null || getTextFild2.getText().length() == 0) {
            errorMessage += "Не указана стоимость транспортного средства!\n";
        }
        if (getComboBox.getValue() == null) {
            errorMessage += "Не указана валюта!\n";
        }

        if (getMonth.getValue() == null || getYear.getValue() == null) {
            errorMessage += "Не указана дата выпуска!\n";
        }
        if (getRadioButton2.isSelected()) {
            if (getRadioButton3.isSelected() || getRadioButton4.isSelected()) {
            } else errorMessage += "1Не указана тип двигателя!\n";

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

    private int intMonth(String month) {

        for (int i = 0; i < 12; i++)
            if (month == list[i])
                return i + 1;
        return 1;
    }

}





