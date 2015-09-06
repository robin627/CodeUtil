package com.jala.tool.db2o;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jala.tool.entity.ColumnInfo;

public class Oracle2JavaMapping implements Db2oMapping {

	private static Log log = LogFactory.getLog(Oracle2JavaMapping.class);
	
	private Map<?, ?> typeMapping;

	public Map<?, ?> getTypeMapping() {
		return typeMapping;
	}

	public void setTypeMapping(Map<?, ?> typeMapping) {
		this.typeMapping = typeMapping;
	}

	public String getTypeName(ColumnInfo item) {
		String typename = (String) typeMapping.get(item.getColumnType()
				.toLowerCase());

		if (typename == null || "".equals(typename)) {
			throw new IllegalArgumentException("Can't find the dbType mapping:"
					+ item.getColumnType());
		}

		if (item.getColumnType().toLowerCase().startsWith("timestamp")) {
			typename = "Date";
		}

		if (typename.equals("number")) {

			log.info(item.getColumnName() + ","
					+ item.getColumnNumberPrecision() + ","
					+ item.getColumnNumberScale());

			if (item.getColumnNumberScale() == 0) {
				if (item.getColumnNumberPrecision() >= 19) { // number(19,0)
					typename = "long";
				} else {
					typename = "int";
				}
			} else {
				typename = "Double";
			}
		} else if (typename.equals("float")) {
			typename = "Double";
		}

		

		return typename;
	}

	public void preHandle(ColumnInfo item) {
		// TODO Auto-generated method stub
		// oracle字段都为大写，在生成类属性之前，先转为小写
		item.setColumnName(item.getColumnName().toLowerCase());
	}

}
