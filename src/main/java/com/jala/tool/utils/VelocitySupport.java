package com.jala.tool.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class VelocitySupport {
	private static final Log LOG = LogFactory.getLog(VelocitySupport.class);
	
	private VelocityEngine ve;
	private Map<String,Object> contextMap;
	private String templatePath;
	
	public VelocitySupport(){
		ve = new VelocityEngine();
		contextMap = new Hashtable<String, Object>();
	}


	public void init(String template){		
		contextMap.clear();
		
		InputStream in = VelocitySupport.class.getClassLoader()
				.getResourceAsStream("properties/velocity.properties");
		
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			LOG.error(e);
		}		
		ve.init(p);		
		
		templatePath = template;
		
		
	}
	
	public void put(String key, Object value) {
		contextMap.put(key, value);		
	}

	public String Build() {		
		return VelocityEngineUtils.mergeTemplateIntoString(ve, templatePath, "utf-8", contextMap);

	}
}
