package com.jala.tool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jala.tool.entity.Column;
import com.jala.tool.entity.TableInfo;

/**
 * Excel工具类<br>包括得到所有表及字段<br>
 * */
public class ExcelHelper {
	private static Log log = LogFactory.getLog(ExcelHelper.class);
	
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String POINT = ".";
	public static String FILE_SUFFIX = "";
	
	public static List<TableInfo> readExcel(File file) throws IOException {
		FILE_SUFFIX = getPostfix(file.getName());
		if (!FILE_SUFFIX.equals(null)) {
			if (OFFICE_EXCEL_2003_POSTFIX.equals(FILE_SUFFIX)) {
				log.info("文件：" + file.getName() + "，执行getAllTablesHSSF()");
				return getAllTablesHSSF(file);
			} else if (OFFICE_EXCEL_2010_POSTFIX.equals(FILE_SUFFIX)) {
				log.info("文件：" + file.getName() + "，执行getAllTablesXSSF()");
				return getAllTablesXSSF(file);
			} else {
				log.error("文件：" + file.getName() + "，格式不对！");
			}
		} else {
			log.error("文件：" + file.getName() + "，格式不对！");
		}
		return null;
	}

	/**
	 * 
	 * @Description 从Excel中得到表
	 * @author Robin
	 * @param file 文件
	 * @param readColumn 是否读取列信息
	 * @param hssfWorkbook Excel_2003-=
	 * @return
	 * @throws IOException 
	 */
	public static List<TableInfo> getAllTablesHSSF(File file) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);//构造HSSFWorkbook（工作薄）对象
		
		List<TableInfo> tables = new ArrayList<TableInfo>();
		
		//获得了HSSFWorkbook对象之后，就可以通过它得到Sheet对象数
		int hssfSheetNum = hssfWorkbook.getNumberOfSheets();
		
		//读取第一张表
		HSSFSheet firSheet = hssfWorkbook.getSheetAt(0);
		if (firSheet == null) {
			log.error("第" + 1 + "页，数据为空！");
		    return null;
		}
		for (int i = 1; i <= firSheet.getLastRowNum(); i++) {//从第2张开始
			HSSFRow firfRow = firSheet.getRow(i);
			if (firfRow == null) {
				log.error("第" + 1 + "页第" + i + "行，数据为空！");
			    continue;
			}
			
			//构建一张表
			TableInfo table = new TableInfo(getValue(firfRow.getCell(0)), getValue(firfRow.getCell(1)));
			
			for (int j = 1; j < hssfSheetNum; j++) {// 找出对应的表
				String tn = "";
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(j);
				if (firSheet == null) {
					log.error("第" + (j + 1) + "页，数据为空！");
					return null;
				}

				for (int k = 1; k < hssfSheet.getLastRowNum(); k++) {
					HSSFRow hssfRow = hssfSheet.getRow(k);
					if (hssfRow == null) {
						continue;
					}

					HSSFCell hssfCell = hssfRow.getCell(0);
					if (hssfCell == null) {
						continue;
					}
					tn = getValue(hssfCell);// 得到表名
					break;
				}
				if (table.getTableName().toLowerCase().equals(tn.toLowerCase())) {// 找到对应的表后，读取列信息
					table.setColumns(getColumnsByTable(hssfSheet));
				}
			}
			tables.add(table);//键为小写
		}
		inputStream.close();//最后关闭资源，释放内存
		return tables;
	}
	
	/**
	 * 
	 * @Description 从Excel中得到表
	 * @author Robin
	 * @param file 文件
	 * @param readColumn 是否读取列信息
	 * @param hssfWorkbook Excel_2007+=
	 * @return
	 * @throws IOException 
	 */
	public static List<TableInfo> getAllTablesXSSF(File file) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);//构造XSSFWorkbook（工作薄）对象
		
		List<TableInfo> tables = new ArrayList<TableInfo>();
		
		//获得了XSSFWorkbook对象之后，就可以通过它得到Sheet对象数
		int xssfSheetNum = xssfWorkbook.getNumberOfSheets();
		
		//读取第一张表
		XSSFSheet firSheet = xssfWorkbook.getSheetAt(0);
		if (firSheet == null) {
			log.error("第" + 1 + "页，数据为空！");
		    return null;
		}
		for (int i = 1; i <= firSheet.getLastRowNum(); i++) {//从第2张开始
			XSSFRow firfRow = firSheet.getRow(i);
			if (firfRow == null) {
				log.error("第" + 1 + "页第" + i + "行，数据为空！");
			    continue;
			}
			
			//构建一张表
			TableInfo table = new TableInfo(getValue(firfRow.getCell(0)), getValue(firfRow.getCell(1)));
			
			for (int j = 1; j < xssfSheetNum; j++) {// 找出对应的表
				String tn = "";
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(j);
				if (firSheet == null) {
					log.error("第" + (j + 1) + "页，数据为空！");
					return null;
				}

				for (int k = 1; k < xssfSheet.getLastRowNum(); k++) {
					XSSFRow xssfRow = xssfSheet.getRow(k);
					if (xssfRow == null) {
						continue;
					}

					XSSFCell xssfCell = xssfRow.getCell(0);
					if (xssfCell == null) {
						continue;
					}
					tn = getValue(xssfCell);// 得到表名
					break;
				}
				if (table.getTableName().toLowerCase().equals(tn.toLowerCase())) {// 找到对应的表后，读取列信息
					table.setColumns(getColumnsByTable(xssfSheet));
					break;
				}
			}
			tables.add(table);
		}
		inputStream.close();//最后关闭资源，释放内存
		return tables;
	}
	
	/**
	 * 
	 * @Description 得到某表的所有字段
	 * @author Robin
	 * @param file 文件
	 * @param tableName 表名
	 * @param hssfWorkbook
	 * @return
	 * @throws IOException
	 */
	public static List<Column> getColumnsByTableHSSF(File file, String tableName) throws IOException {
		List<Column> columns = new ArrayList<Column>();
		InputStream inputStream = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);//构造HSSFWorkbook对象

		//获得了HSSFWorkbook对象之后，就可以通过它得到Sheet对象数
		int hssfSheetNum = hssfWorkbook.getNumberOfSheets();

		//对每个工作表进行循环,找出对应表
		for (int i = 1; i < hssfSheetNum; i++) {
			String tn = "";
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
			for (int j = 1; j < hssfSheet.getLastRowNum(); j++ ) {
				HSSFRow hssfRow = hssfSheet.getRow(j);
				if (hssfRow == null) {
				    continue;
				}
				
				HSSFCell hssfCell = hssfRow.getCell(0);
				if (hssfCell == null) {
				    continue;
				}
				tn = getValue(hssfCell);//得到表名
				break;
			}
			if (tableName.toLowerCase().equals(tn.toLowerCase())) {//找到对应的表
				columns = getColumnsByTable(hssfSheet);
			}
		}
		inputStream.close();//最后关闭资源，释放内存
		return columns;
	}
	
	/**
	 * 
	 * @Description 得到某表的所有字段
	 * @author Robin
	 * @param file 文件
	 * @param tableName 表名
	 * @param xssfWorkbook 
	 * @return
	 * @throws IOException
	 */
	public static List<Column> getColumnsByTableXSSF(File file, String tableName) throws IOException {
		List<Column> columns = new ArrayList<Column>();
		InputStream inputStream = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);//构造XSSFWorkbook对象
		
		//获得了XSSFWorkbook对象之后，就可以通过它得到Sheet对象数
		int xssfSheetNum = xssfWorkbook.getNumberOfSheets();
		
		//对每个工作表进行循环,找出对应表
		for (int i = 1; i < xssfSheetNum; i++) {
			String tn = "";
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
			for (int j = 1; j < xssfSheet.getLastRowNum(); j++ ) {
				XSSFRow xssfRow = xssfSheet.getRow(j);
				if (xssfRow == null) {
					continue;
				}
				
				XSSFCell hssfCell = xssfRow.getCell(0);
				if (hssfCell == null) {
					continue;
				}
				tn = getValue(hssfCell);//得到表名
				break;
			}
			if (tableName.toLowerCase().equals(tn.toLowerCase())) {//找到对应的表
				columns = getColumnsByTable(xssfSheet);
			}
		}
		inputStream.close();//最后关闭资源，释放内存
		return columns;
	}


	/**
	 * 根据表名得到某一张表
	 * @param tableName 表名
	 * @param readColumn 是否需要读取列信息
	 * @throws IOException 
	 */
	public static TableInfo getTableHSSF(File file, String tableName, boolean readColumn) throws IOException {
		TableInfo table = new TableInfo();
		InputStream inputStream = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);//构造Workbook对象

		//读取第一张表
		HSSFSheet firSheet = hssfWorkbook.getSheetAt(0);
		if (firSheet == null) {
			log.error("第" + 1 + "页，数据为空！");
		    return null;
		}
		for (int i = 1; i < firSheet.getLastRowNum(); i++) {
			String tn = "";
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
			for (int j = 1; j < hssfSheet.getLastRowNum(); j++ ) {
				HSSFRow hssfRow = hssfSheet.getRow(j);
				if (hssfRow == null) {
					continue;
				}
				
				HSSFCell hssfCell = hssfRow.getCell(2);
				if (hssfCell == null) {
					continue;
				}
				tn = getValue(hssfCell);//得到表名
				break;
			}
			//找到对应的表
			if (tableName.toLowerCase().equals(tn.toLowerCase())) {
				HSSFRow firfRow = firSheet.getRow(i);
				if (firfRow == null) {
					log.error("第" + 1 + "页第" + i + "行，数据为空！");
				    continue;
				}
				table = new TableInfo(getValue(firfRow.getCell(0)), getValue(firfRow.getCell(1)));
				if (readColumn) {
					table.setColumns(getColumnsByTableHSSF(file, tableName));
				}
				break;
			}
		}
		inputStream.close();//最后关闭资源，释放内存
		return table;
	}
	
	/**
	 * 根据表名得到某一张表
	 * @param tableName 表名
	 * @param readColumn 是否需要读取列信息
	 * @throws IOException 
	 */
	public static TableInfo getTableXSSF(File file, String tableName, boolean readColumn) throws IOException {
		TableInfo table = new TableInfo();
		InputStream inputStream = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);//构造Workbook对象
		
		//读取第一张表
		XSSFSheet firSheet = xssfWorkbook.getSheetAt(0);
		if (firSheet == null) {
			log.error("第" + 1 + "页，数据为空！");
			return null;
		}
		for (int i = 1; i < firSheet.getLastRowNum(); i++) {
			String tn = "";
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
			for (int j = 1; j < xssfSheet.getLastRowNum(); j++ ) {
				XSSFRow xssfRow = xssfSheet.getRow(j);
				if (xssfRow == null) {
					continue;
				}
				
				XSSFCell xssfCell = xssfRow.getCell(2);
				if (xssfCell == null) {
					continue;
				}
				tn = getValue(xssfCell);//得到表名
				break;
			}
			//找到对应的表
			if (tableName.toLowerCase().equals(tn.toLowerCase())) {
				XSSFRow firfRow = firSheet.getRow(i);
				if (firfRow == null) {
					log.error("第" + 1 + "页第" + i + "行，数据为空！");
					continue;
				}
				table = new TableInfo(getValue(firfRow.getCell(0)), getValue(firfRow.getCell(1)));
				if (readColumn) {
					table.setColumns(getColumnsByTableXSSF(file, tableName));
				}
				break;
			}
		}
		inputStream.close();//最后关闭资源，释放内存
		return table;
	}

	/**
	 * 将excel中sheet的每一行封装为Column
	 * */
	private static List<Column> getColumnsByTable(HSSFSheet hssfSheet) {
		List<Column> columns = new ArrayList<Column>();
		for (int i = 1; i < hssfSheet.getLastRowNum(); i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			if (hssfRow == null) {
			    continue;
			}
			Column column = new Column();
			column.setTableName(getValue(hssfRow.getCell(0)));// 表名
			column.setFiledNameEn(getValue(hssfRow.getCell(1)));// 字段名
			column.setFiledNameCn(getValue(hssfRow.getCell(2)));//中文字段名
			column.setSqlType(getValue(hssfRow.getCell(3)));//数据类型
			column.setIsPrimaryKey(Boolean.parseBoolean(getValue(hssfRow.getCell(4))));// 是否为主键
			column.setIsIdentity(Boolean.parseBoolean(getValue(hssfRow.getCell(5))));// 是否标识
			column.setIsCanNull(Boolean.parseBoolean(getValue(hssfRow.getCell(6))));// 是否允许为空
			column.setDeVlaue(getValue(hssfRow.getCell(7)));//默认值
			column.setDesc(getValue(hssfRow.getCell(8)));//备注

			// 是否为主键(默认id为主键)
			if ("id".equals(column.getFiledNameEn().toLowerCase())) {
				column.setIsPrimaryKey(true);
			} else {
				column.setIsPrimaryKey(false);
			}

			columns.add(column);//键为小写
		}
		return columns;
	}
	
	/**
	 * 将excel中sheet的每一行封装为Column
	 * */
	private static List<Column> getColumnsByTable(XSSFSheet xssfSheet) {
		List<Column> columns = new ArrayList<Column>();
		for (int i = 1; i < xssfSheet.getLastRowNum(); i++) {
			XSSFRow xssfRow = xssfSheet.getRow(i);
			if (xssfRow == null) {
				continue;
				
			}
			Column column = new Column();
			column.setTableName(getValue(xssfRow.getCell(0)));// 表名
			column.setFiledNameEn(getValue(xssfRow.getCell(1)));// 字段名
			column.setFiledNameCn(getValue(xssfRow.getCell(2)));//中文字段名
			column.setSqlType(getValue(xssfRow.getCell(3)));//数据类型
			column.setIsPrimaryKey(Boolean.parseBoolean(getValue(xssfRow.getCell(4))));// 是否为主键
			column.setIsIdentity(Boolean.parseBoolean(getValue(xssfRow.getCell(5))));// 是否标识
			column.setIsCanNull(Boolean.parseBoolean(getValue(xssfRow.getCell(6))));// 是否允许为空
			column.setDeVlaue(getValue(xssfRow.getCell(7)));//默认值
			column.setDesc(getValue(xssfRow.getCell(8)));//备注
			
			columns.add(column);//键为小写
		}
		return columns;
	}
	
	/**
	 * 
	 * @Description 根据Excel不同数据类型获取相应数据
	 * @author Robin
	 * @param xssfRow
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfRow) {
		if (hssfRow == null) {
			return null;
		}
		if (hssfRow.getCellType() == hssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfRow.getBooleanCellValue());
		} else if (hssfRow.getCellType() == hssfRow.CELL_TYPE_NUMERIC) {
			//判断是否为日期类型
			if(HSSFDateUtil.isCellDateFormatted(hssfRow)){
				//用于转化为日期格式
				Date d = hssfRow.getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return formater.format(d);
			}else{
				// 用于格式化数字，只保留数字的整数部分
				DecimalFormat df = new DecimalFormat("########");
				return df.format(hssfRow.getNumericCellValue());
			}
		} else {
			return hssfRow.getStringCellValue().trim();
		}
	}
	
	/**
	 * 
	 * @Description 根据Excel不同数据类型获取相应数据
	 * @author Robin
	 * @param xssfRow
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow == null) {
			return null;
		}
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			//判断是否为日期类型
			if(HSSFDateUtil.isCellDateFormatted(xssfRow)){
				//用于转化为日期格式
				Date d = xssfRow.getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return formater.format(d);
			}else{
				// 用于格式化数字，只保留数字的整数部分
				DecimalFormat df = new DecimalFormat("########");
				return df.format(xssfRow.getNumericCellValue());
			}
		} else {
			return xssfRow.getStringCellValue().trim();
		}
	}
	
	/**
	 * 
	 * @Description 获取文件尾缀
	 * @author Robin
	 * @param fileName
	 * @return
	 */
	public static String getPostfix(String fileName) {
		if (fileName == null || "".equals(fileName.trim())) {
			FILE_SUFFIX = null;
		}
		if (fileName.contains(POINT)) {
			FILE_SUFFIX = fileName.substring(fileName.lastIndexOf(POINT) + 1,
					fileName.length());
		}
		return FILE_SUFFIX;
	}
}