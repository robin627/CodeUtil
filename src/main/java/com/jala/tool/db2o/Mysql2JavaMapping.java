package com.jala.tool.db2o;

import java.util.Map;

import com.jala.tool.entity.ColumnInfo;

public class Mysql2JavaMapping implements Db2oMapping {

	private Map<?, ?> typeMapping;

	public Map<?, ?> getTypeMapping() {
		return typeMapping;
	}

	public void setTypeMapping(Map<?, ?> typeMapping) {
		this.typeMapping = typeMapping;
	}
	
	public String getTypeName(ColumnInfo item) {
		String typename = (String) typeMapping.get(item.getColumnType().toLowerCase());
		if (typename == null || "".equals(typename)) {
			throw new IllegalArgumentException("Can't find the dbType mapping:" + item.getColumnType());
		}
		return typename;
	}
	
	public void preHandle(ColumnInfo item){
		return;
	}

	

}
