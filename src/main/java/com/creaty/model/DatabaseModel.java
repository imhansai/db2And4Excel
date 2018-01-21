package com.creaty.model;

import com.alibaba.fastjson.JSON;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class DatabaseModel {

    private StringProperty databaseType = new SimpleStringProperty();
    private List<DatabaseInfoModel> databaseInfoModelList = new ArrayList<DatabaseInfoModel>();


    public String getDatabaseType() {
        return databaseType.get();
    }

    public StringProperty databaseTypeProperty() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType.set(databaseType);
    }

    public List<DatabaseInfoModel> getDatabaseInfoModelList() {
        return databaseInfoModelList;
    }

    public void setDatabaseInfoModelList(List<DatabaseInfoModel> databaseInfoModelList) {
        this.databaseInfoModelList = databaseInfoModelList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
