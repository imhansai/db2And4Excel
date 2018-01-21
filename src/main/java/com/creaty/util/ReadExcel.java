package com.creaty.util;

import com.creaty.model.ColumnModel;
import com.creaty.model.TableModel;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ReadExcel {

    private static final Logger log = Logger.getLogger("ReadExcel");

    public static List readExcelData(String url) throws Exception {

        List hospitalList = new ArrayList();
        InputStream inputStream = null;
        try {
            //读取文件输入流
            inputStream = IOUtils.toInputStream(url, "utf-8");
        } catch (IOException e) {
            log.warning("excel文件流读取错误");
            e.printStackTrace();
        }
        // 创建工作薄Workbook
        Workbook workBook = null;
        // 读取2007版，以.xlsx 结尾
        if (url.toLowerCase().endsWith("xlsx")) {
            try {
                workBook = new XSSFWorkbook(inputStream);
            } catch (IOException e) {
                log.warning("xlsx文件流读取错误");
                e.printStackTrace();
            }
        }
        // 读取2003版，以.xls 结尾
        else if (url.toLowerCase().endsWith("xls")) {
            try {
                workBook = new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                log.warning("xls文件流读取错误");
                e.printStackTrace();
            }
        }
        //获取sheet数
        int numberOfSheets = workBook.getNumberOfSheets();
        // 循环 numberOfSheets
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {
            // 得到 工作薄 的第 N个表
            Sheet sheet = workBook.getSheetAt(sheetNum);
            Row row;
            String cell;
            for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
                // 循环行数
                row = sheet.getRow(i);
                for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
                    // 循环列数
                    cell = row.getCell(j).toString();
                    hospitalList.add(cell);
                }
            }
        }
        return hospitalList;
    }

    public boolean createExcelByTableModel(List<TableModel> tableModelList, File file) throws Exception {
        // 创建工作薄
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 在工作薄中创建一工作表
        XSSFSheet sheet = workBook.createSheet();
        int j = 0;//定义行数，用于计算每个表所占行数
        for (int i = 0; i < tableModelList.size(); i++) {
            TableModel tableModel = tableModelList.get(i);
            // 在指定的索引处创建一行
            XSSFRow tableNameRow = sheet.createRow(1 + j);
            // 在指定的索引处创建一列（单元格）
            XSSFCell tableNameCell = tableNameRow.createCell(0);
            tableNameCell.setCellValue(tableModel.getTableComment() + "   " + tableModel.getTableName());
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(1 + j, 1 + j, 0, 5); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(cra);
            //创建列表头
            XSSFRow titleRow = sheet.createRow(2 + j);
            titleRow.createCell(0).setCellValue("字段");
            titleRow.createCell(1).setCellValue("类型");
            titleRow.createCell(2).setCellValue("长度");
            titleRow.createCell(3).setCellValue("是否为空");
            titleRow.createCell(4).setCellValue("默认值");
            titleRow.createCell(5).setCellValue("备注");
            j += 3;//将表头和列头加进去
            for (ColumnModel columnModel : tableModel.getColList()) {
                XSSFRow Row = sheet.createRow(j);
                Row.createCell(0).setCellValue(columnModel.getColumnName());
                Row.createCell(1).setCellValue(columnModel.getDataType());
                Row.createCell(2).setCellValue(columnModel.getDataLength());
                Row.createCell(3).setCellValue(columnModel.getNullAble());
                Row.createCell(4).setCellValue(columnModel.getDataDefault());
                Row.createCell(5).setCellValue(columnModel.getComments());
                j++;
            }
            // 使用RegionUtil类为合并后的单元格添加边框
            RegionUtil.setBorderBottom(1, cra, sheet); // 下边框
            RegionUtil.setBorderLeft(1, cra, sheet); // 左边框
            RegionUtil.setBorderRight(1, cra, sheet); // 有边框
            RegionUtil.setBorderTop(1, cra, sheet); // 上边框
        }
        // 新建一输出流并把相应的excel文件存盘
        FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
        workBook.write(fos);
        fos.flush();
        //操作结束，关闭流
        fos.close();
        log.info("文件生成");
        return true;
    }


    public static void main(String[] args) throws Exception {
        // 创建工作薄
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 在工作薄中创建一工作表
        XSSFSheet sheet = workBook.createSheet();
        // 在指定的索引处创建一行
        XSSFRow row = sheet.createRow(0);
        // 在指定的索引处创建一列（单元格）
        XSSFCell code = row.createCell(0);
        // 定义单元格为字符串类型
        code.setCellType(XSSFCell.CELL_TYPE_STRING);
        // 在单元格输入内容
        XSSFRichTextString codeContent = new XSSFRichTextString("医院编号");
        code.setCellValue(codeContent);
        XSSFCell city = row.createCell(1);
        city.setCellType(XSSFCell.CELL_TYPE_STRING);
        XSSFRichTextString cityContent = new XSSFRichTextString("城市");
        city.setCellValue(cityContent);
        // 新建一输出流并把相应的excel文件存盘
        FileOutputStream fos = new FileOutputStream("/Users/hansai/hos.xlsx");
        workBook.write(fos);
        fos.flush();
        //操作结束，关闭流
        fos.close();
        System.out.println("文件生成");
    }
}