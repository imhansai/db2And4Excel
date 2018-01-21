package com.creaty.controller;

import com.creaty.dao.TableDao;
import com.creaty.model.ColumnModel;
import com.creaty.model.DatabaseInfoModel;
import com.creaty.model.TableModel;
import com.creaty.start.StartPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ColumnController {

    private static final Logger log = Logger.getLogger("ColumnController");
    private StartPane startPane;
    @FXML
    private TableView<ColumnModel> columnShow;
    @FXML
    private TableColumn<ColumnModel, String> tableName;
    @FXML
    private TableColumn<ColumnModel, String> columnName;
    @FXML
    private TableColumn<ColumnModel, String> dataType;
    @FXML
    private TableColumn<ColumnModel, String> dataLength;
    @FXML
    private TableColumn<ColumnModel, String> nullAble;
    @FXML
    private TableColumn<ColumnModel, String> dataDefault;
    @FXML
    private TableColumn<ColumnModel, String> comments;

    public void initView() {
        log.info("列页面初始化");
        TableDao tableDao = new TableDao(startPane.getDbUtils());
        DatabaseInfoModel databaseInfoModel = startPane.getDatabaseInfoModel();
        List<ColumnModel> columnModelList = new ArrayList<ColumnModel>();
        if (databaseInfoModel != null) {
            List<TableModel> tableModelList = startPane.getTableModelList();
            if (databaseInfoModel.getDatabaseDriver().contains("mysql")) {
                columnModelList = tableDao.getTableColumnsMySQL(tableModelList);
            }
            if (databaseInfoModel.getDatabaseDriver().contains("oracle")) {
                columnModelList = tableDao.getTableColumnsOracle(tableModelList);
            }
            ObservableList<ColumnModel> observableList = FXCollections.observableArrayList();
            for (ColumnModel columnModel : columnModelList) {
                observableList.add(columnModel);
            }
            tableName.setCellValueFactory(new PropertyValueFactory("tableName"));
            columnName.setCellValueFactory(cellData -> cellData.getValue().columnNameProperty());
            dataType.setCellValueFactory(cellData -> cellData.getValue().dataTypeProperty());
            dataLength.setCellValueFactory(cellData -> cellData.getValue().dataLengthProperty());
            nullAble.setCellValueFactory(cellData -> cellData.getValue().nullAbleProperty());
            dataDefault.setCellValueFactory(cellData -> cellData.getValue().dataDefaultProperty());
            comments.setCellValueFactory(cellData -> cellData.getValue().commentsProperty());
            columnShow.setItems(observableList);
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "请选择/配置数据库连接！", ButtonType.CLOSE);
            alert.show();
        }
    }

    public void setStartPane(StartPane startPane) {
        this.startPane = startPane;
    }
}
