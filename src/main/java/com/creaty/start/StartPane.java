package com.creaty.start;

import com.creaty.controller.ColumnController;
import com.creaty.controller.DBEditDialogController;
import com.creaty.controller.StartController;
import com.creaty.controller.TableController;
import com.creaty.model.DatabaseInfoModel;
import com.creaty.model.TableModel;
import com.creaty.util.DBUtils;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class StartPane extends Application {

    private static final Logger log = Logger.getLogger("StartPane");

    private DBUtils dbUtils;
    private StartController startController;
    private DBEditDialogController dbEditDialogController;
    private TableController tableController;
    private ColumnController columnController;
    private Stage primaryStage;
    private AnchorPane start;
    private DatabaseInfoModel databaseInfoModel;
    private List<TableModel> tableModelList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/creaty/fxml/start.fxml"));
        this.start = fxmlLoader.load();
        startController = fxmlLoader.getController();
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resources/icon.png")));
        this.primaryStage = primaryStage;
        showStarPane();
        showTableList();
    }

    public void showStarPane() { //主界面-顶部菜单
        startController.setStartPane(this);
        startController.initView();
        primaryStage.setTitle("数据库表结构导入导出工具");
        primaryStage.setScene(new Scene(start, 800, 600));
        primaryStage.show();
    }

    public void showTableList() { //主界面-搜索表名的结果界面
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/creaty/fxml/table.fxml"));
            SplitPane splitPane = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            ObservableList startChildren = start.getChildren();
            startChildren.add(splitPane);
            tableController.setStartPane(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showColumnList() { //选定表的列显示界面
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/creaty/fxml/column.fxml"));
            AnchorPane dialogPane = fxmlLoader.load();
            columnController = fxmlLoader.getController();
            columnController.setStartPane(this);
            columnController.initView();
            ObservableList startChildren = start.getChildren();
            SplitPane splitPane = (SplitPane) startChildren.get(1);
            //AnchorPane anchorPane = (AnchorPane) splitPane.getItems().get(1);
            if (splitPane.getItems().size() > 1) {
                splitPane.getItems().remove(1);
            }
            splitPane.getItems().add(dialogPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showDBEditDialog() { //显示数据库配置界面
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/creaty/fxml/dbEditDialog.fxml"));
            AnchorPane dialogPane = fxmlLoader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("数据库添加");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(dialogPane, 380, 280));
            dbEditDialogController = fxmlLoader.getController();
            dbEditDialogController.initView();
            dbEditDialogController.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return dbEditDialogController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DatabaseInfoModel getDatabaseInfoModel() {
        return databaseInfoModel;
    }

    public void setDatabaseInfoModel(DatabaseInfoModel databaseInfoModel) {
        this.databaseInfoModel = databaseInfoModel;
    }

    public DBUtils getDbUtils() {
        return dbUtils;
    }

    public void setDbUtils(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public TableController getTableController() {
        return tableController;
    }

    public List<TableModel> getTableModelList() {
        return tableModelList;
    }

    public void setTableModelList(List<TableModel> tableModelList) {
        this.tableModelList = tableModelList;
    }

    public static void main(String[] args) { //启动
        launch(args);
    }
}
