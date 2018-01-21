package com.creaty.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ColumnModel {

    private StringProperty tableName = new SimpleStringProperty();
    private StringProperty columnName = new SimpleStringProperty();
    private StringProperty dataType = new SimpleStringProperty();
    private StringProperty dataLength = new SimpleStringProperty();
    private StringProperty nullAble = new SimpleStringProperty();
    private StringProperty dataDefault = new SimpleStringProperty();
    private StringProperty comments = new SimpleStringProperty();

    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    public String getColumnName() {
        return columnName.get();
    }

    public StringProperty columnNameProperty() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    public String getDataType() {
        return dataType.get();
    }

    public StringProperty dataTypeProperty() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType.set(dataType);
    }

    public String getDataLength() {
        return dataLength.get();
    }

    public StringProperty dataLengthProperty() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength.set(dataLength);
    }

    public String getNullAble() {
        return nullAble.get();
    }

    public StringProperty nullAbleProperty() {
        return nullAble;
    }

    public void setNullAble(String nullAble) {
        this.nullAble.set(nullAble);
    }

    public String getDataDefault() {
        return dataDefault.get();
    }

    public StringProperty dataDefaultProperty() {
        return dataDefault;
    }

    public void setDataDefault(String dataDefault) {
        this.dataDefault.set(dataDefault);
    }

    public String getComments() {
        return comments.get();
    }

    public StringProperty commentsProperty() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments.set(comments);
    }
}
