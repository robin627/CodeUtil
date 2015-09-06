package com.jala.tool.entity;

public class Column {
	private int id;// id
	private String tableName;// 表名
	private String filedNameEn;// 英文字段名
	private String filedNameCn;// 中文字段名
	private String sqlType;// sql类型
	private String javaType;// java类型
	private int filedLength;// 长度
	private int precision;// 精度
	private String deVlaue;// 默认值
	private boolean hasLength;// 是否有长度
	private boolean hasPrecision;// 是否有精度
	private boolean isPrimaryKey;// 是否是主键
	private boolean isCanNull;// 是否可以为空
	private boolean isIdentity;// 是否是标识
	private String desc;// 字段说明
	private String foreignKey;// 外键

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFiledNameEn() {
		return filedNameEn;
	}

	public void setFiledNameEn(String filedNameEn) {
		this.filedNameEn = filedNameEn;
	}

	public String getFiledNameCn() {
		return filedNameCn;
	}

	public void setFiledNameCn(String filedNameCn) {
		this.filedNameCn = filedNameCn;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public int getFiledLength() {
		return filedLength;
	}

	public void setFiledLength(int filedLength) {
		this.filedLength = filedLength;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public String getDeVlaue() {
		return deVlaue;
	}

	public void setDeVlaue(String deVlaue) {
		this.deVlaue = deVlaue;
	}

	public boolean getHasLength() {
		return hasLength;
	}

	public void setHasLength(boolean hasLength) {
		this.hasLength = hasLength;
	}

	public boolean getHasPrecision() {
		return hasPrecision;
	}

	public void setHasPrecision(boolean hasPrecision) {
		this.hasPrecision = hasPrecision;
	}

	public boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean getIsCanNull() {
		return isCanNull;
	}

	public void setIsCanNull(boolean isCanNull) {
		this.isCanNull = isCanNull;
	}

	public boolean getIsIdentity() {
		return isIdentity;
	}

	public void setIsIdentity(boolean isIdentity) {
		this.isIdentity = isIdentity;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

}