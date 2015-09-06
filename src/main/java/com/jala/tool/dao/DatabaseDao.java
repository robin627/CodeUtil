package com.jala.tool.dao;

import java.util.List;

import com.jala.tool.entity.ColumnInfo;
import com.jala.tool.entity.TableInfo;

public interface DatabaseDao {
	public TableInfo findTableInfo(String databaseType,String databaseOrSchema,String tablename);
	public List<ColumnInfo> findColumnsInfo(String databaseType,String databaseOrSchema,String tablename);
}
