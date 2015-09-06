package com.jala.tool.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.common.jdbc.ScriptRunner;
import com.ibatis.common.resources.Resources;
import com.jala.tool.dao.DatabaseDao;
import com.jala.tool.db2o.Db2oMapping;
import com.jala.tool.db2o.Db2oSupport;
import com.jala.tool.entity.BuildParameter;
import com.jala.tool.entity.Column;
import com.jala.tool.entity.ColumnInfo;
import com.jala.tool.entity.TableInfo;
import com.jala.tool.utils.ExcelHelper;
import com.jala.tool.utils.FileHelper;
import com.jala.tool.utils.VelocitySupport;


public class BuildServiceImpl implements BuildService {

	private static Log log = LogFactory.getLog(BuildServiceImpl.class);

	private DatabaseDao databaseDao = null;
	private Map<String, Db2oMapping> typeMappings = null;
	private VelocitySupport velocitySupport;


	/**
	 * 不同db中，获取当前时间的函数名
	 */
	private Map<String, String> dbGetTimeMethedNameMapping;

	private Db2oSupport db2oSupport;

	public Db2oSupport getDb2oSupport() {
		return db2oSupport;
	}

	public void setDb2oSupport(Db2oSupport db2oSupport) {
		this.db2oSupport = db2oSupport;
	}

	public Map<String, Db2oMapping> getTypeMappings() {
		return typeMappings;
	}

	public void setTypeMappings(Map<String, Db2oMapping> typeMappings) {
		this.typeMappings = typeMappings;
	}

	public Map<String, String> getDbGetTimeMethedNameMapping() {
		return dbGetTimeMethedNameMapping;
	}

	public void setDbGetTimeMethedNameMapping(
			Map<String, String> dbGetTimeMethedNameMapping) {
		this.dbGetTimeMethedNameMapping = dbGetTimeMethedNameMapping;
	}

	public DatabaseDao getDatabaseDao() {
		return databaseDao;
	}

	public void setDatabaseDao(DatabaseDao databaseDao) {
		this.databaseDao = databaseDao;
	}

	public VelocitySupport getVelocitySupport() {
		return velocitySupport;
	}

	public void setVelocitySupport(VelocitySupport velocitySupport) {
		this.velocitySupport = velocitySupport;
	}

	@Override
	public void build(BuildParameter param, String mappingKey) {

		//mysql2java,mssql2java,oracle2java,
		//excel2mysql,excel2mssql,excel2oracle
		//excel2mysql2java,excel2mssql2java,excel2oracle2java
		if (mappingKey.equals("oracle2java") || mappingKey.equals("mssql2java") || mappingKey.equals("mysql2java")) {
			Db2oMapping mapping = (Db2oMapping) typeMappings.get(mappingKey);
			if (mapping == null) {
				log.info("Can't find the mapping:" + mappingKey);
				return;
			}
			
			buildJava(param, mapping, mappingKey.substring(0, mappingKey.lastIndexOf("2")));//去掉2java字符
		} else if (mappingKey.equals("excel2mysql") || mappingKey.equals("excel2mssql") || mappingKey.equals("excel2oracle")) {
			buildSql(param, mappingKey);
		} else if (mappingKey.equals("excel2mysql2java") || mappingKey.equals("excel2mssql2java") || mappingKey.equals("excel2oracle2java")) {
			Db2oMapping mapping = (Db2oMapping) typeMappings.get(mappingKey.substring(mappingKey.indexOf("2")+1));//去掉excel2字符
			if (mapping == null) {
				log.info("Can't find the mapping:" + mappingKey);
				return;
			}
			
			String[] tableNames = buildSql(param, mappingKey.substring(0, mappingKey.lastIndexOf("2")));//去掉2java字符
			executeSql(param);
			
			param.setTableNames(tableNames);
			buildJava(param, mapping, mappingKey.substring(mappingKey.indexOf("2")+1, mappingKey.lastIndexOf("2")));//去掉excel2,2java字符
		} else {
			log.warn("暂不支持：" + mappingKey);
		}

	}
	
	public void buildJava(BuildParameter param, Db2oMapping mapping, String databaseType) {
		// Entity.java
		String entityTemplatePath = "vm/java/Entity.java";
		String entityDir = param.getRootDir()
				+ param.getEntityPackageName().replace(".", "/") + "/";

		// Dao.java
		String daoTemplatePath = "vm/java/Dao.java";
		String daoDir = param.getRootDir()
				+ param.getDaoPackageName().replace(".", "/") + "/";

		// AbstractDao.java
		String abstractDaoTemplatePath = "vm/java/AbstractDao.java";
		String abstractDaoImplDir = param.getRootDir()
				+ param.getDaoPackageName().replace(".", "/") + "/";

		// EntityDao.java
		String entityDaoTemplatePath = "vm/java/EntityDao.java";
		String entityDaoDir = param.getRootDir()
				+ param.getDaoPackageName().replace(".", "/") + "/";

		// EntityDaoImpl.java
		String entityDaoImplTemplatePath = "vm/java/EntityDaoImpl.java";
		String entityDaoImplDir = param.getRootDir()
				+ param.getDaoImplPackageName().replace(".", "/") + "/";
		
		// EntityService.java
		String entityServiceTemplatePath = "vm/java/EntityService.java";
		String entityServiceDir = param.getRootDir()
				+ param.getServicePackageName().replace(".", "/") + "/";
		
		// EntityServiceImpl.java
		String entityServiceImplTemplatePath = "vm/java/EntityServiceImpl.java";
		String entityServiceImplDir = param.getRootDir()
				+ param.getServiceImplPackageName().replace(".", "/") + "/";
		
		// EntityActionImpl.java
		String entityActionTemplatePath = "vm/java/EntityAction.java";
		String entityActionDir = param.getRootDir()
				+ param.getActionPackageName().replace(".", "/") + "/";

		// ibatis-dao-mapping.xml
		String ibatisDaoMappingTemplatePath = "vm/mapping/ibatis-dao-mapping.xml";
		String ibatisDaoMappingDir = param.getIbatisMappingDir();

		// ibatis-daoimpl-mapping.xml
		String ibatisDaoImplMappingTemplatePath = "vm/mapping/ibatis-daoimpl-mapping.xml";
		String ibatisDaoImplMappingDir = param.getIbatisMappingDir();
		
		// ibatis-sql-mapdemo.xml
		String ibatisSqlMapDemoTemplatePath = "vm/mapping/ibatis-sql-mapdemo.xml";
		String ibatisSqlMapDemoDir = param.getIbatisMappingDir();
		
		// mybatis-dao-mapping.xml
		String mybatisDaoMappingTemplatePath = "vm/mapping/mybatis-dao-mapping.xml";
		String mybatisDaoMappingDir = param.getMybatisMappingDir();
		
		// mybatis-daoimpl-mapping.xml
		String mybatisDaoImplMappingTemplatePath = "vm/mapping/mybatis-daoimpl-mapping.xml";
		String mybatisDaoImplMappingDir = param.getMybatisMappingDir();
		
		// mybatis-sql-mapdemo.xml
		String mybatisSqlMapDemoTemplatePath = "vm/mapping/mybatis-sql-mapdemo.xml";
		String mybatisSqlMapDemoDir = param.getIbatisMappingDir();

		// 数据库获取当前时间的方法
		String dbGetTimeMethedName = dbGetTimeMethedNameMapping.get(databaseType.toLowerCase());

		/*
		 * 循环每一张表
		 */
		for (String tableName : param.getTableNames()) {
			try {
				// 1,获取表信息
				log.info("获取数据表信息 begin....." + tableName);
				TableInfo table = databaseDao.findTableInfo(databaseType,
						param.getDatabaseOrSchema(), tableName);
				if (table == null) {
					log.info("获取表信息 end..... 数据表不存在");
					continue;
				}
				log.info("表名：" + table.getTableName() + ",描述："
						+ table.getTableComment());

				// 2，获取列信息
				log.info("获取列信息 begin....." + param.getTableNames());
				List<ColumnInfo> columns = databaseDao.findColumnsInfo(
						databaseType, param.getDatabaseOrSchema(), tableName);
				if (columns == null || columns.size() == 0) {
					log.info("获取列信息 end.....无法找到数据列信息");
				}
				log.info("获取列信息 end.....一共有" + String.valueOf(columns.size())
						+ "列");

				// 3，将数据库信息转为目标语言
				/*
				 * a,转化表名为类名 b,转换列名为类属性 c,转换数据列类型为目标语言类型
				 */
				db2oSupport.prepare(table, columns, mapping,
						param.getTablePrefix());

				for (ColumnInfo column : columns) {
					log.debug(column.getColumnName() + ","
							+ column.getColumnType() + ","
							+ column.getPropertyTypeName());
				}

				if (param.isCreateEntity()) {
					// 3,生成Entity
					velocitySupport.init(entityTemplatePath);
					velocitySupport.put("packageName",
							param.getEntityPackageName());
					velocitySupport.put("table", table);
					velocitySupport.put("columns", columns);
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());

					String entityContent = velocitySupport.Build();
					// log.debug(entityContent);
					String entityPath = entityDir + table.getEntityName()
							+ ".java";
					FileHelper.createFile(entityPath, entityContent);
				}

				if (param.isCreateDao()) {
					// 4.生成Dao
					velocitySupport.init(daoTemplatePath);
					velocitySupport.put("packageName",
							param.getDaoPackageName());
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());
					String daoContent = velocitySupport.Build();
					// log.debug(daoContent);
					String daoPath = daoDir + "Dao.java";
					FileHelper.createFile(daoPath, daoContent);
				}

				if (param.isCreateAbstractDao()) {
					// 5.生成AbstractDao
					velocitySupport.init(abstractDaoTemplatePath);
					velocitySupport.put("packageName",
							param.getDaoPackageName());
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());
					String abstractDaoContent = velocitySupport.Build();
					log.debug(abstractDaoContent);
					String abstractDaoPath = abstractDaoImplDir
							+ "AbstractDao.java";
					FileHelper.createFile(abstractDaoPath, abstractDaoContent);
				}

				if (param.isCreateEntityDao()) {
					// 6.生成EntityDao
					velocitySupport.init(entityDaoTemplatePath);
					velocitySupport.put("daoPackageName",
							param.getDaoPackageName());
					velocitySupport.put("entityPackageName",
							param.getEntityPackageName());
					velocitySupport.put("table", table);
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());
					String entityDaoContent = velocitySupport.Build();
					log.debug(entityDaoContent);
					String entityDaoPath = entityDaoDir + table.getEntityName()
							+ "Dao.java";
					FileHelper.createFile(entityDaoPath, entityDaoContent);
				}

				// 7.生成EntityDaoImpl
				if (param.isCreateEntityDaoImpl()) {

					velocitySupport.init(entityDaoImplTemplatePath);
					velocitySupport.put("daoPackageName",
							param.getDaoPackageName());
					velocitySupport.put("daoImplPackageName",
							param.getDaoImplPackageName());
					velocitySupport.put("entityPackageName",
							param.getEntityPackageName());
					velocitySupport.put("table", table);
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());
					String entityDaoImplContent = velocitySupport.Build();
					//log.debug(entityDaoImplContent);
					String entityDaoImplPath = entityDaoImplDir
							+ table.getEntityName() + "DaoImpl.java";
					FileHelper.createFile(entityDaoImplPath,
							entityDaoImplContent);
				}
				
				if (param.isCreateEntityService()) {
					// 8.生成EntityService
					velocitySupport.init(entityServiceTemplatePath);
					velocitySupport.put("servicePackageName",
							param.getServicePackageName());
					velocitySupport.put("entityPackageName",
							param.getEntityPackageName());
					velocitySupport.put("table", table);
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());
					String entityServiceContent = velocitySupport.Build();
					log.debug(entityServiceContent);
					String entityServicePath = entityServiceDir + table.getEntityName()
							+ "Service.java";
					FileHelper.createFile(entityServicePath, entityServiceContent);
				}
				
				// 9.生成EntityServiceImpl
				if (param.isCreateEntityServiceImpl()) {
					
					velocitySupport.init(entityServiceImplTemplatePath);
					velocitySupport.put("servicePackageName",
							param.getServicePackageName());
					velocitySupport.put("serviceImplPackageName",
							param.getServiceImplPackageName());
					velocitySupport.put("entityPackageName",
							param.getEntityPackageName());
					velocitySupport.put("table", table);
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());
					String entityServiceImplContent = velocitySupport.Build();
					//log.debug(entityServiceImplContent);
					String entityServiceImplPath = entityServiceImplDir
							+ table.getEntityName() + "ServiceImpl.java";
					FileHelper.createFile(entityServiceImplPath,
							entityServiceImplContent);
				}
				
				if (param.isCreateEntityAction()) {
					// 10.生成EntityAction
					velocitySupport.init(entityActionTemplatePath);
					velocitySupport.put("actionPackageName",
							param.getActionPackageName());
					velocitySupport.put("table", table);
					velocitySupport.put("author", param.getAuthor());
					velocitySupport.put("createTime", param.getCreateTime());
					String entityActionContent = velocitySupport.Build();
					log.debug(entityActionContent);
					String entityActionPath = entityActionDir + table.getEntityName()
							+ "Action.java";
					FileHelper.createFile(entityActionPath, entityActionContent);
				}

				if (param.isCreateIbatisMapping()) {
					// 11.生成ibatis-dao-mapping
					velocitySupport.init(ibatisDaoMappingTemplatePath);
					velocitySupport.put("table", table);
					velocitySupport.put("columns", columns);
					velocitySupport.put("entityPackageName",
							param.getEntityPackageName());
					velocitySupport.put("dbGetTimeMethedName",
							dbGetTimeMethedName);

					String ibatisDaoMappingContent = velocitySupport.Build();
					log.debug(ibatisDaoMappingContent);
					String ibatisDaoMappingPath = ibatisDaoMappingDir
							+ table.getEntityName().toLowerCase()
							+ "-mapping.xml";
					FileHelper.createFile(ibatisDaoMappingPath,
							ibatisDaoMappingContent);
				}

				if (param.isCreateIbatisImplMapping()) {
					// 12.生成ibatis-daoimpl-mapping
					velocitySupport.init(ibatisDaoImplMappingTemplatePath);
					velocitySupport.put("table", table);
					
					String ibatisDaoImplMappingContent = velocitySupport
							.Build();
					log.debug(ibatisDaoImplMappingContent);
					String ibatisDaoImplMappingPath = ibatisDaoImplMappingDir
							+ table.getEntityName().toLowerCase()
							+ "-impl-mapping.xml";
					FileHelper.createFile(ibatisDaoImplMappingPath,
							ibatisDaoImplMappingContent);
				}
				
				if (param.isCreateMybatisMapping()) {
					// 13.生成mybatis-dao-mapping
					velocitySupport.init(mybatisDaoMappingTemplatePath);
					velocitySupport.put("table", table);
					velocitySupport.put("columns", columns);
					velocitySupport.put("entityPackageName",
							param.getEntityPackageName());
					velocitySupport.put("dbGetTimeMethedName",
							dbGetTimeMethedName);
					
					String mybatisDaoMappingContent = velocitySupport.Build();
					log.debug(mybatisDaoMappingContent);
					String mybatisDaoMappingPath = mybatisDaoMappingDir
							+ table.getEntityName().toLowerCase()
							+ "-mapping.xml";
					FileHelper.createFile(mybatisDaoMappingPath,
							mybatisDaoMappingContent);
				}
				
				if (param.isCreateIbatisImplMapping()) {
					// 14.生成mybatis-daoimpl-mapping
					velocitySupport.init(mybatisDaoImplMappingTemplatePath);
					velocitySupport.put("table", table);
					
					String mybatisDaoImplMappingContent = velocitySupport
							.Build();
					log.debug(mybatisDaoImplMappingContent);
					String mybatisDaoImplMappingPath = mybatisDaoImplMappingDir
							+ table.getEntityName().toLowerCase()
							+ "-impl-mapping.xml";
					FileHelper.createFile(mybatisDaoImplMappingPath,
							mybatisDaoImplMappingContent);
				}
				
				log.info("java代码生成成功！");
			} catch (Exception e) {
				log.error("java代码生成出错！");
				log.error(e);
			}
		}
		try {
			if (param.isCreateIbatisConfig()) {
				List<TableInfo> tables = new ArrayList<TableInfo>();
				
				// 生成ibatis-sql-mapdemo.xml
				velocitySupport.init(ibatisSqlMapDemoTemplatePath);
				for (String tableName : param.getTableNames()) {
					TableInfo table = databaseDao.findTableInfo(databaseType,
							param.getDatabaseOrSchema(), tableName);
					table.setEntityName(db2oSupport.getEntityName(tableName, param.getTablePrefix()).toLowerCase());
					tables.add(table);
				}
				velocitySupport.put("tables", tables);
				
				String ibatisSqlMapDemoContent = velocitySupport
						.Build();
				log.debug(ibatisSqlMapDemoContent);
				String ibatisSqlMapDemoPath = ibatisSqlMapDemoDir
						+ "ibatis-sql-mapdemo.xml";
				FileHelper.createFile(ibatisSqlMapDemoPath,
						ibatisSqlMapDemoContent);
			}
			
			if (param.isCreateMybatisConfig()) {
				List<TableInfo> tables = new ArrayList<TableInfo>();
				
				// 生成mybatis-sql-mapdemo.xml
				velocitySupport.init(mybatisSqlMapDemoTemplatePath);
				for (String tableName : param.getTableNames()) {
					TableInfo table = databaseDao.findTableInfo(databaseType,
							param.getDatabaseOrSchema(), tableName);
					table.setEntityName(db2oSupport.getEntityName(tableName, param.getTablePrefix()).toLowerCase());
					tables.add(table);
				}
				
				velocitySupport.put("tables", tables);
				
				String mybatisSqlMapDemoContent = velocitySupport
						.Build();
				log.debug(mybatisSqlMapDemoContent);
				String mybatisSqlMapDemoPath = mybatisSqlMapDemoDir
						+ "mybatis-sql-mapdemo.xml";
				FileHelper.createFile(mybatisSqlMapDemoPath,
						mybatisSqlMapDemoContent);
			}

		} catch (Exception e) {
			log.error("sql-mapdemo.xml代码生成出错！");
			log.error(e);
		}
	}
	
	public String[] buildSql(BuildParameter param, String type) {
		List<Column> columns = new ArrayList<Column>();
		String[] tableNames = null;
		
		// sql
		String sqlTemplatePath = "vm/sql/" + type + ".sql";
		String sqlDir = param.getSqlDir();
		
		File file = new File(param.getExcelDir());;
		try {
			List<TableInfo> tables = ExcelHelper.readExcel(file );
			tableNames = new String[tables.size()];
			int i = 0;
			for(TableInfo table:tables){    
				columns = table.getColumns();
				
				velocitySupport.init(sqlTemplatePath);
				velocitySupport.put("table", table);
				velocitySupport.put("tableName", table.getTableName().toUpperCase());
				velocitySupport.put("author", param.getAuthor());
				velocitySupport.put("createTime", param.getCreateTime());
				velocitySupport.put("columns", columns);
					
				String sqlContent = velocitySupport.Build();
				// log.debug(entityContent);
				String sqlPath = sqlDir + table.getTableName()
						+ ".sql";
				FileHelper.createFile(sqlPath, sqlContent);
				
				tableNames[i] = table.getTableName();
				i++;
			}   
			log.info("sql生成成功！");
		} catch (Exception e) {
			log.error("文件："+param.getExcelDir()+"，生成出错！");
			log.error(e);
		}
		
		return tableNames;
		
	}

	public void executeSql(BuildParameter param) {
		try {
			if (param.isExecuteSql()) {
				Properties props = Resources.getResourceAsProperties("properties/sys.properties");
				String url = props.getProperty("jdbc.url");
				String driver = props.getProperty("jdbc.driverClassName");
				String username = props.getProperty("jdbc.username");
				String password = props.getProperty("jdbc.password");

				Class.forName(driver).newInstance();
				Connection conn = (Connection) DriverManager.getConnection(url,username, password);
				ScriptRunner runner = new ScriptRunner(conn, false, true);
				runner.setErrorLogWriter(null);
			    runner.setLogWriter(null);
				File file = new File(param.getSqlDir());
				if (file.isDirectory()) {
					String[] filelist = file.list();
					for (int i = 0; i < filelist.length; i++) {
						runner.runScript(new InputStreamReader(new FileInputStream(param.getSqlDir()+filelist[i]),"UTF-8"));//防止读取时中文乱码  
					}
				}
				log.error("sql脚本执行成功！");
			}
		} catch (Exception e) {
			log.error("sql脚本执行出错！");
			log.error(e);
		}
	}
	

}
