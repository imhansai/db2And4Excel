package com.creaty.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.creaty.model.DatabaseModel;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ReadJson {

    private static final Logger log = Logger.getLogger("ReadJson");

    public List<DatabaseModel> getFileJson() {
        List<DatabaseModel> databaseModelList = new ArrayList<DatabaseModel>();
        try {
            String jsonStr = IOUtils.toString(getClass().getResource("/databases.json"), "UTF-8"); //读取json文件
            JSONObject jsonObject = JSON.parseObject(jsonStr); //String2Json
            databaseModelList = JSON.parseArray(jsonObject.getString("databases"), DatabaseModel.class); //获取key="databases"的jsonString
        } catch (IOException e) {
            log.info("databases.json文件读取错误");
            e.printStackTrace();
        }
        return databaseModelList;
    }

    public static void main(String[] args) {
        ReadJson readJson = new ReadJson();
        System.out.println(readJson.getFileJson());
    }
}
