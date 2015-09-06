package com.jala.tool.entity;

import java.util.ArrayList;
import java.util.List;

public class TableInfo {
	protected String tableName;
	protected String tableNameCn;
	protected String tableComment;
	
	private List<Column> columns = new ArrayList<Column>();
	 
	public TableInfo() {
	}
	
	public TableInfo(String tableName, String tableNameCn) {
		super();
		this.tableName = tableName;
		this.tableNameCn = tableNameCn;
	}

	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableNameCn() {
		return tableNameCn;
	}

	public void setTableNameCn(String tableNameCn) {
		this.tableNameCn = tableNameCn;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	protected String entityName;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
	protected String primaryKeyName;


	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
	
	protected String primaryKeyColumnName;
	
	
	public String getPrimaryKeyColumnName() {
		return primaryKeyColumnName;
	}

	public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
		this.primaryKeyColumnName = primaryKeyColumnName;
	}
	
	protected String versionIDColumnName;
	protected String versionTimeColumnName;
	
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

	
	public String getVersionIDPropertyName() {
		return versionIDPropertyName;
	}

	public void setVersionIDPropertyName(String versionIDPropertyName) {
		this.versionIDPropertyName = versionIDPropertyName;
	}

	public String getVersionTimePropertyName() {
		return versionTimePropertyName;
	}

	public void setVersionTimePropertyName(String versionTimePropertyName) {
		this.versionTimePropertyName = versionTimePropertyName;
	}


	protected String versionIDPropertyName;
	protected String versionTimePropertyName;


}
