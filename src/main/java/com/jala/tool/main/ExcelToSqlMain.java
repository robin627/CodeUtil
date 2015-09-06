package com.jala.tool.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jala.tool.entity.BuildParameter;
import com.jala.tool.service.BuildService;

public class ExcelToSqlMain {
	private static Log log = LogFactory.getLog(TableToJavaMain.class);;

	public static void main(String[] args) {
		PropertyConfigurator.configure("src/main/resources/properties/log4j.properties");
		/**
		 * 说明： 
		 * 1.在sys.properties 中 配置 数据库类型与目标语言类型 
		 * 2.生成成功后，需要手工在 sqlmap.xml增加新的mapping文件引用 
		 * 3.生成成功后，需要手工在 spring-dao.xml中增加新的dao类
		 * 
		 * 约束： 
		 * 1，数据表必须有主键，且不是复合主键 
		 * 3，支持去除表前缀 setTablePrefix
		 */

		BuildService service = getBuildService();

		BuildParameter param = CreateParam_Test();		
		
		//excel2mysql,excel2mssql,excel2oracle   参数类型
		service.build(param, "excel2mssql");

	}

	private static BuildParameter CreateParam_Test() {

		BuildParameter param = new BuildParameter();
		// User
		param.setAuthor("robin");

		// sql配置文件目录
		param.setSqlDir("E:/workspace/test/src/main/resources/sql/");
		
		// excel文件路径
		param.setExcelDir("E:/workspace/CodeUtil/db/test.xls");

		param.setCreateEntitySql(true);

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
