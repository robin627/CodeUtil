package com.jala.tool.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jala.tool.entity.BuildParameter;
import com.jala.tool.service.BuildService;

public class ExcelToJavaMain {
	private static Log log = LogFactory.getLog(TableToJavaMain.class);;

	public static void main(String[] args) {
		PropertyConfigurator.configure("src/main/resources/properties/log4j.properties");

		/**
		 * 说明： 
		 * 1.在service.build(param, "excel2oracle2java")中 配置 数据库类型与目标语言类型 
		 * 
		 * 约束： 
		 * 1，数据表必须有主键，且不是复合主键 
		 * 3，支持去除表前缀 setTablePrefix
		 */
		
		
		BuildService service = getBuildService();

		BuildParameter param = CreateParam_Test();		
		
		//excel2mysql2java,excel2mssql2java,excel2oracle2java  参数类型
		service.build(param, "excel2mysql2java");

	}


	private static BuildParameter CreateParam_Test() {

		BuildParameter param = new BuildParameter();
		// User
		param.setAuthor("robin");

		// 数据库名
		param.setDatabaseOrSchema("test");

		// 需要去除的表前缀
		param.setTablePrefix("T_PRM_");

		// 实体包名
		param.setEntityPackageName("com.jala.crm.customer.entity");
		// Action包名
		param.setActionPackageName("com.jala.crm.customer.action");
		// dao包名
		param.setDaoPackageName("com.jala.crm.customer.dao");
		// dao实现类包名
		param.setDaoImplPackageName("com.jala.crm.customer.dao.impl");
		// Service包名
		param.setServicePackageName("com.jala.crm.customer.service");
		// Service实现类包名
		param.setServiceImplPackageName("com.jala.crm.customer.service.impl");

		// 文件根目录
		param.setRootDir("E:/workspace/test/src/main/java/");

		// ibatis配置文件目录
		param.setIbatisMappingDir("E:/workspace/test/src/main/resources/ibatis/");
		// mybatis配置文件目录
		param.setMybatisMappingDir("E:/workspace/test/src/main/resources/mybatis/");
		// sql配置文件目录
		param.setSqlDir("E:/workspace/test/src/main/resources/sql/");
		
		// excel文件路径
		param.setExcelDir("E:/workspace/CodeUtil/db/test.xls");

		//是否第一次生成
		boolean firstGenerator = true;

		if (firstGenerator) {
			// 以下第一次生成时设为true,否则设为fase;
			param.setCreateDao(true);
			param.setCreateAbstractDao(true);

			param.setCreateEntity(true);
			
			param.setCreateEntityAction(true);

			param.setCreateEntityDao(true);
			param.setCreateEntityDaoImpl(true);
			
			param.setCreateEntityService(true);
			param.setCreateEntityServiceImpl(true);

			param.setCreateIbatisMapping(true);
			param.setCreateIbatisImplMapping(true);
			
			param.setCreateMybatisMapping(true);
			param.setCreateMybatisImplMapping(true);
			
			param.setCreateEntitySql(true);
			param.setExecuteSql(true);

		} else {
			param.setCreateDao(false);
			param.setCreateAbstractDao(false);

			param.setCreateEntity(true);// 重新生成
			
			param.setCreateEntityAction(false);

			param.setCreateEntityDao(false);
			param.setCreateEntityDaoImpl(false);
			
			param.setCreateEntityService(false);
			param.setCreateEntityServiceImpl(false);

			param.setCreateIbatisMapping(true);// 重新生成
			param.setCreateIbatisImplMapping(false);
			
			param.setCreateMybatisMapping(true);// 重新生成
			param.setCreateMybatisImplMapping(false);
			
			param.setCreateEntitySql(false);
			param.setExecuteSql(false);
		}

		return param;

	}

	private static BuildService getBuildService() {

		String[] contextFileArr = { "classpath:spring/application.xml" };
		String beanName = "buildService";

		BuildService buildService = null;
		try {
			ApplicationContext appCont = new ClassPathXmlApplicationContext(
					contextFileArr);
			buildService = (BuildService) appCont.getBean(beanName);
		} catch (Throwable e) {
			log.fatal("获取service异常", e);
		}
		if (buildService == null) {
			log.error("无法获取:" + beanName);
		}
		return buildService;
	}

}
