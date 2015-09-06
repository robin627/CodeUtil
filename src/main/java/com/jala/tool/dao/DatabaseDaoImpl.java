package com.jala.tool.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.jala.tool.entity.ColumnInfo;
import com.jala.tool.entity.TableInfo;

public class DatabaseDaoImpl extends SqlMapClientDaoSupport  implements DatabaseDao {


	public TableInfo findTableInfo(String databaseType,String databaseOrSchema,String tablename) {
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("databaseOrSchema", databaseOrSchema);
		parameter.put("tablename", tablename); 
		
		return (TableInfo) getSqlMapClientTemplate().queryForObject(databaseType.toLowerCase() + "-select-tableinfo", parameter);

	}

	@SuppressWarnings("unchecked")
	public List<ColumnInfo> findColumnsInfo(String databaseType,String databaseOrSchema,String tablename) {
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("databaseOrSchema", databaseOrSchema);
		parameter.put("tablename", tablename); 
		
		return getSqlMapClientTemplate().queryForList(databaseType.toLowerCase() + "-select-columninfo", parameter);
	}

}
