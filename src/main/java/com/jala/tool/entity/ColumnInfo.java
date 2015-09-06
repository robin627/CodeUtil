package com.jala.tool.entity;

public class ColumnInfo {

	protected String columnName;
	protected String columnType;
	protected boolean columnNullable;
	protected int columnCharLength;
	protected int columnNumberPrecision;
	protected int columnNumberScale;
	protected String columnKey;
	protected String columnComment;
	protected int columnPosition;
	protected boolean columnIsNumeric;
	protected boolean columnIsPrimaryKey;

	public boolean isColumnIsPrimaryKey() {
		return columnIsPrimaryKey;
	}

	public void setColumnIsPrimaryKey(boolean columnIsPrimaryKey) {
		this.columnIsPrimaryKey = columnIsPrimaryKey;
	}

	public boolean isColumnIsNumeric() {
		return columnIsNumeric;
	}

	public void setColumnIsNumeric(boolean columnIsNumeric) {
		this.columnIsNumeric = columnIsNumeric;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public boolean isColumnNullable() {
		return columnNullable;
	}

	public void setColumnNullable(boolean columnNullable) {
		this.columnNullable = columnNullable;
	}

	public int getColumnCharLength() {
		return columnCharLength;
	}

	public void setColumnCharLength(int columnCharLength) {
		this.columnCharLength = columnCharLength;
	}

	public int getColumnNumberPrecision() {
		return columnNumberPrecision;
	}

	public void setColumnNumberPrecision(int columnNumberPrecision) {
		this.columnNumberPrecision = columnNumberPrecision;
	}

	public int getColumnNumberScale() {
		return columnNumberScale;
	}

	public void setColumnNumberScale(int columnNumberScale) {
		this.columnNumberScale = columnNumberScale;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public int getColumnPosition() {
		return columnPosition;
	}

	public void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}


	public String getCamelPropertyName() {
		return camelPropertyName;
	}

	public void setCamelPropertyName(String camelPropertyName) {
		this.camelPropertyName = camelPropertyName;
	}

	public String getPascalPropertyName() {
		return pascalPropertyName;
	}

	public void setPascalPropertyName(String pascalPropertyName) {
		this.pascalPropertyName = pascalPropertyName;
	}

	public String getPropertyTypeName() {
		return propertyTypeName;
	}

	public void setPropertyTypeName(String propertyTypeName) {
		this.propertyTypeName = propertyTypeName;
	}

	protected String camelPropertyName;
	protected String pascalPropertyName;
	protected String propertyTypeName;

	
}
