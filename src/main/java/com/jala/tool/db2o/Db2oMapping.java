package com.jala.tool.db2o;

import com.jala.tool.entity.ColumnInfo;

public interface Db2oMapping {
	public String getTypeName(ColumnInfo item);
	
	//预处理
	public void preHandle(ColumnInfo item);
}
