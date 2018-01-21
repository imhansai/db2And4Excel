package com.creaty.controller;

import com.creaty.model.DatabaseInfoModel;
import com.creaty.model.DatabaseModel;
import com.creaty.start.StartPane;
import com.creaty.util.DBUtils;
import com.creaty.util.ReadJson;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.List;
import java.util.logging.Logger;


public class StartController {

    private static final Logger log = Logger.getLogger("StartController");

    private ReadJson readJson = new ReadJson();
    private StartPane startPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu OracleMenu;
    @FXML
    private Menu MySQLMenu;

    /**
     * 初始方法
     */
    public void initView() {
        // 读取json文件中的数据库配置
        List<DatabaseModel> databaseModelList = readJson.getFileJson();
        // 循环 然后根据数据库类型区分
        for (DatabaseModel databaseModel : databaseModelList) {
            Menu menu = "Oracle".equals(databaseModel.getDatabaseType()) ? OracleMenu : MySQLMenu;
            for (DatabaseInfoModel databaseInfoModel : databaseModel.getDatabaseInfoModelList()) { //循环 动态加载
                menu.getItems().add(newMenuItemOnAction(databaseInfoModel));
            }
        }
    }


    public void setStartPane(StartPane startPane) {
        this.startPane = startPane;
        menuBar.setUseSystemMenuBar(true);
    }

    @FXML
    private void handleNewDB() {
        startPane.showDBEditDialog();
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.NONE, "此工具由武杨开发！", ButtonType.CLOSE);
        alert.show();
    }

    /**
     * 动态加载菜单并添加事件
     *
     * @param databaseInfoModel
     * @return
     */
    private MenuItem newMenuItemOnAction(DatabaseInfoModel databaseInfoModel) {
        MenuItem menuItem = new MenuItem(databaseInfoModel.getDatabaseName());
        menuItem.setOnAction(ActionEvent -> {
            startPane.setDatabaseInfoModel(databaseInfoModel);
            startPane.setDbUtils(new DBUtils(databaseInfoModel));
            startPane.getTableController().getDbUrl().setText(databaseInfoModel.getDatabaseUrl());
        });
        return menuItem;
    }
}
