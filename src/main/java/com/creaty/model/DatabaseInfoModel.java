package com.creaty.model;

import com.alibaba.fastjson.JSON;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatabaseInfoModel {

    private StringProperty databaseName = new SimpleStringProperty();
    private StringProperty databaseUrl = new SimpleStringProperty();
    private StringProperty databaseUserName = new SimpleStringProperty();
    private StringProperty databasePassword = new SimpleStringProperty();
    private StringProperty databaseDriver = new SimpleStringProperty();

    public String getDatabaseName() {
        return databaseName.get();
    }

    public StringProperty databaseNameProperty() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName.set(databaseName);
    }

    public String getDatabaseUrl() {
        return databaseUrl.get();
    }

    public StringProperty databaseUrlProperty() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl.set(databaseUrl);
    }

    public String getDatabaseUserName() {
        return databaseUserName.get();
    }

    public StringProperty databaseUserNameProperty() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName.set(databaseUserName);
    }

    public String getDatabasePassword() {
        return databasePassword.get();
    }

    public StringProperty databasePasswordProperty() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword.set(databasePassword);
    }

    public String getDatabaseDriver() {
        return databaseDriver.get();
    }

    public StringProperty databaseDriverProperty() {
        return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver.set(databaseDriver);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
