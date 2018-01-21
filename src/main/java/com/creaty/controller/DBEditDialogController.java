package com.creaty.controller;

import com.creaty.model.DatabaseInfoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class DBEditDialogController {
    private static final Logger log = Logger.getLogger("DBEditDialogController");

    @FXML
    private TextField databaseName;
    @FXML
    private TextField databaseUrl;
    @FXML
    private TextField databaseUserName;
    @FXML
    private PasswordField databasePassword;
    @FXML
    private ComboBox databaseDriver;

    private Stage dialogStage;
    private DatabaseInfoModel databaseInfoModel = new DatabaseInfoModel();
    private boolean okClicked = false;

    public void initView() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("Oracle");
        observableList.add("MySQL");
        databaseDriver.setItems(observableList);
        databaseDriver.getSelectionModel().select(0); //默认选择第一个
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            databaseInfoModel.setDatabaseName(databaseName.getText());
            databaseInfoModel.setDatabaseUrl(databaseUrl.getText());
            databaseInfoModel.setDatabaseUserName(databaseUserName.getText());
            databaseInfoModel.setDatabasePassword(databasePassword.getText());
            databaseInfoModel.setDatabaseDriver(databaseDriver.getItems().get(0).equals("Oracle") ? "oracle.jdbc.driver.OracleDriver" : "com.mysql.jdbc.Driver");
            log.info(databaseInfoModel.toString());
            okClicked = true;
            Alert alert = new Alert(Alert.AlertType.NONE, "添加成功！", ButtonType.CLOSE);
            alert.show();
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (notNull(databaseName)) {
            errorMessage = "数据库名称必填!\n";
        } else if (notNull(databaseUrl)) {
            errorMessage = "URL必填!\n";
        } else if (notNull(databaseUserName)) {
            errorMessage = "用户名必填!\n";
        } else if (notNull(databasePassword)) {
            errorMessage = "密码必填!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.NONE, errorMessage, ButtonType.CLOSE);
            alert.show();
            return false;
        }
    }

    private boolean notNull(TextField textField) {
        if (textField.getText() == null || textField.getText().length() == 0) {
            return true;
        }
        return false;
    }
}
