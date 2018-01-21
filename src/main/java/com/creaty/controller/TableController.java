package com.creaty.controller;

import com.creaty.dao.TableDao;
import com.creaty.model.ColumnModel;
import com.creaty.model.DatabaseInfoModel;
import com.creaty.model.TableModel;
import com.creaty.start.StartPane;
import com.creaty.util.ReadExcel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class TableController {

    private static final Logger log = Logger.getLogger("TableController");

    private StartPane startPane;
    private String databaseType;
    @FXML
    private Text dbUrl;
    @FXML
    private TextField tableNameText;
    @FXML
    private Button tableNextbtn;
    @FXML
    private Button excel2dbbtn;
    @FXML
    private Button searchbtn;

    @FXML
    private TableView<TableModel> tableShow;
    @FXML
    private TableColumn<TableModel, CheckBox> checkList;
    @FXML
    private TableColumn<TableModel, String> tableName;
    @FXML
    private TableColumn<TableModel, String> tableComment;

    public void initView() {

    }

    @FXML
    private void handleSearch() {
        String searchName = tableNameText.getText();
        TableDao tableDao = new TableDao(startPane.getDbUtils());
        DatabaseInfoModel databaseInfoModel = startPane.getDatabaseInfoModel();
        if (databaseInfoModel != null) {
            List<TableModel> tableModelList = new ArrayList<TableModel>();

            if (databaseInfoModel.getDatabaseDriver().contains("mysql")) {
                tableModelList = tableDao.getTableNameMySQL(searchName);
            }
            if (databaseInfoModel.getDatabaseDriver().contains("oracle")) {
                tableModelList = tableDao.getTableNameOracle(searchName);
            }

            ObservableList<TableModel> tableModelObservableList = FXCollections.observableArrayList();
            for (TableModel tableModel : tableModelList) {
                tableModelObservableList.add(tableModel);
            }

            checkList.setCellValueFactory(new PropertyValueFactory("checkList"));
            tableName.setCellValueFactory(new PropertyValueFactory("tableName"));
            tableComment.setCellValueFactory(new PropertyValueFactory("tableComment"));
            tableShow.setItems(tableModelObservableList);
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "请选择/配置数据库连接！", ButtonType.CLOSE);
            alert.show();
        }
    }

    private void sendError() {
        Alert alert = new Alert(Alert.AlertType.NONE, "请选择/配置数据库连接！", ButtonType.CLOSE);
        alert.show();
    }

    @FXML
    private void handleTableNext() {
        List<TableModel> tableModelList = new ArrayList<TableModel>();
        ObservableList<TableModel> tableModelObservableList = tableShow.getItems();
        if (null == tableModelObservableList) {
            sendError();
            return;
        }
        for (TableModel tableModel : tableModelObservableList) {
            if (tableModel.getCheckList().isSelected()) {
                tableModelList.add(tableModel);
            }
        }
        if (tableModelList.size() < 1) {
            sendError();
            return;
        }
        TableDao tableDao = new TableDao(startPane.getDbUtils());
        DatabaseInfoModel databaseInfoModel = startPane.getDatabaseInfoModel();
        List<ColumnModel> columnModelList = new ArrayList<ColumnModel>();
        if (databaseInfoModel != null) {
            if (databaseInfoModel.getDatabaseDriver().contains("mysql")) {
                columnModelList = tableDao.getTableColumnsMySQL(tableModelList);
            }
            if (databaseInfoModel.getDatabaseDriver().contains("oracle")) {
                columnModelList = tableDao.getTableColumnsOracle(tableModelList);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "请选择/配置数据库连接！", ButtonType.CLOSE);
            alert.show();
            return;
        }
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("new.xlsx");
        fileChooser.setTitle("请选择文件保存的位置");
        file = fileChooser.showSaveDialog(new Stage());
        if (null == file) {
            Alert alert = new Alert(Alert.AlertType.NONE, "请选择文件保存的位置", ButtonType.FINISH);
            alert.show();
            return;
        }
        for (TableModel tableModel : tableModelList) {
            List<ColumnModel> columnModel2List = new ArrayList<ColumnModel>();
            for (ColumnModel columnModel : columnModelList) {

                if (columnModel.getTableName().equals(tableModel.getTableName())) {
                    columnModel2List.add(columnModel);
                }
            }
            tableModel.setColList(columnModel2List);
        }
        ReadExcel readExcel = new ReadExcel();
        boolean success = false;
        try {
            success = readExcel.createExcelByTableModel(tableModelList, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (success) {
            Alert alert = new Alert(Alert.AlertType.NONE, "导出文件成功！", ButtonType.CLOSE);
            alert.show();
        }
    }


    public void setStartPane(StartPane startPane) {
        this.startPane = startPane;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public Text getDbUrl() {
        return dbUrl;
    }
}
