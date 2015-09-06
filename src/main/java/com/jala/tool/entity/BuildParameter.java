package com.jala.tool.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildParameter {

	protected String databaseOrSchema;// 数据库名

	protected String[] tableNames;// 表名

	protected String entityPackageName;// 实体包名
	protected String daoPackageName;// dao包名
	protected String daoImplPackageName;// dao实现类包名
	protected String servicePackageName;// service类包名
	protected String serviceImplPackageName;// service实现类包名
	protected String actionPackageName;// action类包名

	protected String rootDir;// 文件根目录
	protected String ibatisMappingDir;// ibatis配置文件目录
	protected String mybatisMappingDir;// mybatis配置文件目录
	protected String sqlDir;// sql配置文件目录
	protected String excelDir;//  excel文件路径

	protected String author;// 创建人
	protected String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	protected boolean createDao;
	protected boolean createAbstractDao;
	protected boolean createEntityDao;
	protected boolean createEntityDaoImpl;
	protected boolean createEntityService;
	protected boolean createEntityServiceImpl;
	protected boolean createEntityAction;
	protected boolean createIbatisMapping;
	protected boolean createIbatisImplMapping;
	protected boolean createMybatisMapping;
	protected boolean createMybatisImplMapping;
	protected boolean createEntity;
	protected boolean createEntitySql;
	protected boolean createIbatisConfig;
	protected boolean createMybatisConfig;
	protected boolean executeSql;

	/**
	 * 要去除的表前缀
	 */
	protected String tablePrefix;

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String getDatabaseOrSchema() {
		return databaseOrSchema;
	}

	public void setDatabaseOrSchema(String databaseOrSchema) {
		this.databaseOrSchema = databaseOrSchema;
	}

	public String[] getTableNames() {
		return tableNames;
	}

	public void setTableNames(String[] tableNames) {
		this.tableNames = tableNames;
	}

	public String getEntityPackageName() {
		return entityPackageName;
	}

	public void setEntityPackageName(String entityPackageName) {
		this.entityPackageName = entityPackageName;
	}

	public String getDaoPackageName() {
		return daoPackageName;
	}

	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public String getDaoImplPackageName() {
		return daoImplPackageName;
	}

	public void setDaoImplPackageName(String daoImplPackageName) {
		this.daoImplPackageName = daoImplPackageName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getServiceImplPackageName() {
		return serviceImplPackageName;
	}

	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}

	public String getActionPackageName() {
		return actionPackageName;
	}

	public void setActionPackageName(String actionPackageName) {
		this.actionPackageName = actionPackageName;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public String getIbatisMappingDir() {
		return ibatisMappingDir;
	}

	public void setIbatisMappingDir(String ibatisMappingDir) {
		this.ibatisMappingDir = ibatisMappingDir;
	}

	public String getMybatisMappingDir() {
		return mybatisMappingDir;
	}

	public void setMybatisMappingDir(String mybatisMappingDir) {
		this.mybatisMappingDir = mybatisMappingDir;
	}

	public String getSqlDir() {
		return sqlDir;
	}

	public void setSqlDir(String sqlDir) {
		this.sqlDir = sqlDir;
	}

	public String getExcelDir() {
		return excelDir;
	}

	public void setExcelDir(String excelDir) {
		this.excelDir = excelDir;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public boolean isCreateDao() {
		return createDao;
	}

	public void setCreateDao(boolean createDao) {
		this.createDao = createDao;
	}

	public boolean isCreateAbstractDao() {
		return createAbstractDao;
	}

	public void setCreateAbstractDao(boolean createAbstractDao) {
		this.createAbstractDao = createAbstractDao;
	}

	public boolean isCreateEntityDao() {
		return createEntityDao;
	}

	public void setCreateEntityDao(boolean createEntityDao) {
		this.createEntityDao = createEntityDao;
	}

	public boolean isCreateEntityDaoImpl() {
		return createEntityDaoImpl;
	}

	public void setCreateEntityDaoImpl(boolean createEntityDaoImpl) {
		this.createEntityDaoImpl = createEntityDaoImpl;
	}

	public boolean isCreateEntityService() {
		return createEntityService;
	}

	public void setCreateEntityService(boolean createEntityService) {
		this.createEntityService = createEntityService;
	}

	public boolean isCreateEntityServiceImpl() {
		return createEntityServiceImpl;
	}

	public void setCreateEntityServiceImpl(boolean createEntityServiceImpl) {
		this.createEntityServiceImpl = createEntityServiceImpl;
	}

	public boolean isCreateEntityAction() {
		return createEntityAction;
	}

	public void setCreateEntityAction(boolean createEntityAction) {
		this.createEntityAction = createEntityAction;
	}

	public boolean isCreateIbatisMapping() {
		return createIbatisMapping;
	}

	public void setCreateIbatisMapping(boolean createIbatisMapping) {
		this.createIbatisMapping = createIbatisMapping;
	}

	public boolean isCreateIbatisImplMapping() {
		return createIbatisImplMapping;
	}

	public void setCreateIbatisImplMapping(boolean createIbatisImplMapping) {
		this.createIbatisImplMapping = createIbatisImplMapping;
	}

	public boolean isCreateMybatisMapping() {
		return createMybatisMapping;
	}

	public void setCreateMybatisMapping(boolean createMybatisMapping) {
		this.createMybatisMapping = createMybatisMapping;
	}

	public boolean isCreateMybatisImplMapping() {
		return createMybatisImplMapping;
	}

	public void setCreateMybatisImplMapping(boolean createMybatisImplMapping) {
		this.createMybatisImplMapping = createMybatisImplMapping;
	}

	public boolean isCreateEntity() {
		return createEntity;
	}

	public void setCreateEntity(boolean createEntity) {
		this.createEntity = createEntity;
	}

	public boolean isCreateEntitySql() {
		return createEntitySql;
	}

	public void setCreateEntitySql(boolean createEntitySql) {
		this.createEntitySql = createEntitySql;
	}

	public boolean isCreateIbatisConfig() {
		return createIbatisConfig;
	}

	public void setCreateIbatisConfig(boolean createIbatisConfig) {
		this.createIbatisConfig = createIbatisConfig;
	}

	public boolean isCreateMybatisConfig() {
		return createMybatisConfig;
	}

	public void setCreateMybatisConfig(boolean createMybatisConfig) {
		this.createMybatisConfig = createMybatisConfig;
	}

	public boolean isExecuteSql() {
		return executeSql;
	}

	public void setExecuteSql(boolean executeSql) {
		this.executeSql = executeSql;
	}

}
