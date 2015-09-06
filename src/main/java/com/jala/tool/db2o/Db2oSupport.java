package com.jala.tool.db2o;

import java.util.List;

import org.jdom2.IllegalDataException;

import antlr.debug.NewLineEvent;

import com.jala.tool.entity.ColumnInfo;
import com.jala.tool.entity.TableInfo;

public class Db2oSupport {

	private String versionIDColumnName;
	private String versionTimeColumnName;

	public String getVersionIDColumnName() {
		return versionIDColumnName;
	}

	public void setVersionIDColumnName(String versionIDColumnName) {
		this.versionIDColumnName = versionIDColumnName;
	}

	public String getVersionTimeColumnName() {
		return versionTimeColumnName;
	}

	public void setVersionTimeColumnName(String versionTimeColumnName) {
		this.versionTimeColumnName = versionTimeColumnName;
	}

	public void prepare(TableInfo table, List<ColumnInfo> columns, Db2oMapping mapping, String tablePrefix) {

		if (tablePrefix != null && !"".equals(tablePrefix)) {
			int indexOf = table.getTableName().toLowerCase()
					.indexOf(tablePrefix.toLowerCase());
			if (indexOf == 0) {// 从零开始
				String tableName = table.getTableName().substring(
						tablePrefix.length());
				table.setEntityName(getPascalString(tableName));
			} else {
				table.setEntityName(getPascalString(table.getTableName()));
			}

		} else {
			table.setEntityName(getPascalString(table.getTableName()));
		}

		for (ColumnInfo item : columns) {

			// 预处理
			mapping.preHandle(item);

			item.setCamelPropertyName(getCamelString(item.getColumnName()));

			item.setPascalPropertyName(getPascalString(item.getColumnName()));

			// 映射为目标语言的类型
			item.setPropertyTypeName(mapping.getTypeName(item));

			// 将主键名称存入Table对象
			if (item.isColumnIsPrimaryKey()) {

				if (null != table.getPrimaryKeyName()
						&& !"".equals(table.getPrimaryKeyName())) {
					throw new IllegalDataException("不支持复合主键");
				}

				table.setPrimaryKeyName(getCamelString(item.getColumnName()));
				table.setPrimaryKeyColumnName(item.getColumnName());
			}
						

			// 版本控制字段
			if (item.getColumnName().toLowerCase()
					.equals(versionIDColumnName.toLowerCase())) {
				table.setVersionIDColumnName(item.getColumnName());
				table.setVersionIDPropertyName(getCamelString(item
						.getColumnName()));
			}

			// 版本控制字段
			if (item.getColumnName().toLowerCase()
					.equals(versionTimeColumnName.toLowerCase())) {
				table.setVersionTimeColumnName(item.getColumnName());
				table.setVersionTimePropertyName(getCamelString(item.getColumnName()));
			}
		}

		if (null == table.getPrimaryKeyName()
				|| "".equals(table.getPrimaryKeyName())) {
			throw new IllegalDataException("数据表中未找到主键定义，请添加主键字段");
		}

		if (!"".equals(versionIDColumnName)) {
			if (null == table.getVersionIDColumnName()
					|| "".equals(table.getVersionIDColumnName())) {
				throw new IllegalDataException("未找到用于版本控制的 VersionID字段");
			}
		}

		if(!"".equals(versionTimeColumnName)){
			if (null == table.getVersionTimeColumnName()
					|| "".equals(table.getVersionTimeColumnName())) {
				throw new IllegalDataException("未找到用于版本控制的 VersionTime字段");
			}
		}

	}

	/**
	 * ת转为首字母小写，去除 下划线
	 * 
	 * @param input
	 * @return
	 */
	public static String getCamelString(String input) {
		if (input == null)
			return "";

		String tempString = getPascalString(input);

		StringBuffer buf = new StringBuffer();

		return buf.append(
				String.valueOf(tempString.charAt(0)).toLowerCase()
						+ tempString.substring(1)).toString();

	}

	/**
	 * 转为首字母大写，去除 下划线
	 * 
	 * @param input
	 * @return
	 */
	public static String getPascalString(String input) {

		if (input == null)
			return "";

		String tempString = input;

		StringBuffer buf = new StringBuffer();

		// 如果有下划线，去除下划线
		if (tempString.indexOf("_") >= 0) {
			String[] strSections = tempString.split("_");
			for (String item : strSections) {
				if (item == null || "".equals(item)) {
					continue;
				}
				buf.append(String.valueOf(item.charAt(0)).toUpperCase()
						+ item.substring(1).toLowerCase());
			}
		} else {
			// 如果没有，直接将第一个字母改大写
			buf.append(String.valueOf(tempString.charAt(0)).toUpperCase()
					+ tempString.substring(1));
		}

		return buf.toString();
	}
	
	public String getEntityName(String tableName, String tablePrefix) {
		String entityName = tableName;
		if (tablePrefix != null && !"".equals(tablePrefix)) {
			int indexOf = tableName.toLowerCase().indexOf(tablePrefix.toLowerCase());
			if (indexOf == 0) {// 从零开始
				tableName = tableName.substring(tablePrefix.length());
				entityName = getPascalString(tableName);
			} else {
				entityName = getPascalString(tableName);
			}

		} else {
			entityName = getPascalString(tableName);
		}
		return entityName;
	}

	public static void main(String[] args) {
		System.out.println(new Db2oSupport().getEntityName("crm_customer","crm_"));
	}
}
