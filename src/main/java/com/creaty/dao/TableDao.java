package com.creaty.dao;

import com.creaty.model.ColumnModel;
import com.creaty.model.TableModel;
import com.creaty.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDao {

    private DBUtils dbUtils;

    public TableDao(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public List<TableModel> getTableNameOracle(String tableName) {

        List<TableModel> tableModelList = new ArrayList<TableModel>();
        tableName = tableName.toUpperCase();
        String sqlTable = "SELECT TABLE_NAME,COMMENTS FROM USER_TAB_COMMENTS t WHERE t.table_name LIKE ?";

        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlTable);
            preparedStatement.setString(1, "%" + tableName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TableModel tableModel = new TableModel();
                tableModel.setTableName(resultSet.getString("TABLE_NAME"));
                tableModel.setTableComment(resultSet.getString("COMMENTS"));
                tableModelList.add(tableModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModelList;
    }

    public List<ColumnModel> getTableColumnsOracle(List<TableModel> tableModelList) {
        StringBuffer tableNameStr = new StringBuffer();
        for (TableModel tableModel : tableModelList) {
            tableNameStr.append("'" + tableModel.getTableName() + "',");
        }
        String tableNameSearch = tableNameStr.substring(0, tableNameStr.length() - 1);
        List<ColumnModel> columnModelList = new ArrayList<ColumnModel>();
        String sqlColumn = "SELECT t.TABLE_NAME,t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH,t.NULLABLE,t.DATA_DEFAULT," +
                "c.COMMENTS FROM USER_TAB_COLUMNS t LEFT JOIN USER_COL_COMMENTS c ON t.COLUMN_NAME=c.COLUMN_NAME " +
                "AND t.TABLE_NAME=c.TABLE_NAME WHERE t.table_name IN (" + tableNameSearch + ")";
        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlColumn);
            //preparedStatement.setString(1, tableNameSearch);
            System.out.println(preparedStatement.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ColumnModel columnModel = new ColumnModel();
                columnModel.setTableName(resultSet.getString("TABLE_NAME"));
                columnModel.setColumnName(resultSet.getString("COLUMN_NAME"));
                columnModel.setDataType(resultSet.getString("DATA_TYPE"));
                columnModel.setDataLength(resultSet.getString("DATA_LENGTH"));
                columnModel.setNullAble(resultSet.getString("NULLABLE"));
                columnModel.setDataDefault(resultSet.getString("DATA_DEFAULT"));
                columnModel.setDataDefault(resultSet.getString("COMMENTS"));
                columnModelList.add(columnModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnModelList;
    }

    public List<TableModel> getTableNameMySQL(String tableName) {

        String databaseUrl = DBUtils.getURL();
        //DBUtils.setURL(databaseUrl + "?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        List<TableModel> tableModelList = new ArrayList<TableModel>();
        String databaseName = databaseUrl.split("//")[1].split("/")[1].split("\\?")[0];
        String sqlTable = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.tables WHERE table_schema = '" + databaseName + "' AND TABLE_NAME LIKE ?";

        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlTable);
            preparedStatement.setString(1, "%" + tableName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TableModel tableModel = new TableModel();
                tableModel.setTableName(resultSet.getString("TABLE_NAME"));
                tableModel.setTableComment(resultSet.getString("TABLE_COMMENT"));
                tableModelList.add(tableModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModelList;
    }

    public List<ColumnModel> getTableColumnsMySQL(List<TableModel> tableModelList) {

        String databaseUrl = DBUtils.getURL();
        //DBUtils.setURL(databaseUrl + "?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        String databaseName = databaseUrl.split("//")[1].split("/")[1].split("\\?")[0];

        StringBuffer tableNameStr = new StringBuffer();
        for (TableModel tableModel : tableModelList) {
            tableNameStr.append("'" + tableModel.getTableName() + "',");
        }
        String tableNameSearch = tableNameStr.substring(0, tableNameStr.length() - 1);
        List<ColumnModel> columnModelList = new ArrayList<ColumnModel>();

        String sqlColumn = "SELECT TABLE_NAME,COLUMN_NAME,COLUMN_TYPE,IS_NULLABLE,COLUMN_DEFAULT,COLUMN_COMMENT FROM information_schema.columns " +
                "WHERE table_schema ='" + databaseName + "' AND TABLE_NAME IN (" + tableNameSearch + ")";
        Connection connection = dbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlColumn);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ColumnModel columnModel = new ColumnModel();
                columnModel.setTableName(resultSet.getString("TABLE_NAME"));
                columnModel.setColumnName(resultSet.getString("COLUMN_NAME"));
                columnModel.setDataType(resultSet.getString("COLUMN_TYPE"));
                //columnModel.setDataLength(resultSet.getString("DATA_LENGTH"));
                columnModel.setNullAble(resultSet.getString("IS_NULLABLE"));
                columnModel.setDataDefault(resultSet.getString("COLUMN_DEFAULT"));
                columnModel.setComments(resultSet.getString("COLUMN_COMMENT"));
                columnModelList.add(columnModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnModelList;
    }

}
