package client;

import TCP.TCPConnection;
import TCP.TCPConnectionListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

public class Controller implements TCPConnectionListener {

    int message;
    TCPConnection connection;
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
    private Text Text;

    @FXML
    private ToggleGroup engine;

    @FXML
    private ComboBox<String> getComboBox;

    @FXML
    private Button Start;

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

    public Controller() throws IOException {
    }

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

    public void onToSendClick(MouseEvent mouseEvent) {
        if (isInputValid()) {
            LocalDate date = LocalDate.now();
            if (getRadioButton1.isSelected()) {
                connection.sendMessage(Message(date, "null"));
                Info.setVisible(true);
                Info2.setVisible(false);
                Info1.setVisible(false);
                //Info.setText("Сумма таможенной пошлины составит: " + message + " € (" + (int) (message / well) + " $)");
            } else {
                if (getRadioButton3.isSelected()) {
                    connection.sendMessage(Message(date, "petrol"));
                } else if (getRadioButton4.isSelected()) {
                    connection.sendMessage(Message(date, "diesel"));
                }
                Info.setVisible(true);
                Info2.setVisible(true);
                Info1.setVisible(true);
            }
        }
    }

    public void onExitClicked(MouseEvent mouseEvent) {
        try {
            connection = new TCPConnection(this, "127.0.0.1", 8189);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Start.setVisible(false);
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
    }

    private String Message(LocalDate date, String engineType) {

        int cost = (getComboBox.getValue() == "$") ? (int) (Integer.parseInt(getTextFild2.getText()) * well) : Integer.parseInt(getTextFild2.getText());
        float age = date.getYear() - Integer.parseInt(getYear.getValue()) + (float) (date.getMonthValue() - intMonth(getMonth.getValue())) / 12;
        int volume = Integer.parseInt(getTextFild1.getText());
        return engineType + "-" + cost + "-" + age + "-" + volume;

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
            } else errorMessage += "Не указана тип двигателя!\n";

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

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {

    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {

        message = Integer.parseInt(value);
        if (getRadioButton1.isSelected()) {
            Info.setText("Сумма таможенной пошлины составит: " + message + " € (" + (int) (message / well) + " $)");
        } else {
            int price = (getComboBox.getValue() == "$") ? (int) (Integer.parseInt(getTextFild2.getText()) * well) : Integer.parseInt(getTextFild2.getText());
            int NDS = (int) ((message + price) * 0.2);
            Info.setText("Сумма таможенной пошлины составит: " + message + " € (" + (int) (message / well) + " $)");
            Info1.setText("Сумма НДС: " + NDS + " € (" + (int) (NDS / well) + " $)");
            Info2.setText("Итого: " + (message + NDS) + " € (" + (int) ((message + NDS) / well) + " $)");
        }

    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {

    }

    @Override
    public void onExeption(TCPConnection tcpConnection, Exception e) {

    }
}





