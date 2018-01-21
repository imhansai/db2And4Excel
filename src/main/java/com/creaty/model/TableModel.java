package com.creaty.model;

import com.alibaba.fastjson.JSON;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.List;


public class TableModel {

    private CheckBox checkList = new CheckBox();
    private StringProperty tableName = new SimpleStringProperty();
    private StringProperty tableComment = new SimpleStringProperty();
    private List<ColumnModel> colList = new ArrayList<ColumnModel>();

    public CheckBox getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckBox checkList) {
        this.checkList = checkList;
    }


    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    public String getTableComment() {
        return tableComment.get();
    }

    public StringProperty tableCommentProperty() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment.set(tableComment);
    }

    public List<ColumnModel> getColList() {
        return colList;
    }

    public void setColList(List<ColumnModel> colList) {
        this.colList = colList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
