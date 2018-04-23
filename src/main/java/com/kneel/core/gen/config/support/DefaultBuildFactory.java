package com.kneel.core.gen.config.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kneel.core.gen.config.Column;
import com.kneel.core.gen.config.Table;
import com.kneel.core.gen.config.csv.CSVBuildConfig;
import com.kneel.core.gen.config.type.SqlFormType;
import com.kneel.core.gen.config.type.SqlJavaType;
import com.kneel.core.utils.ESecurityUtils;
import com.kneel.core.utils.PropertiesUtils;
import com.kneel.core.utils.StringUtils;

@Component
public class DefaultBuildFactory {

	protected Log log = LogFactory.getLog(DefaultBuildFactory.class);
	public static final Boolean CLEAN_FLAG = true; 
	public static final String OVERRIDES ="overrides.properties";
	public static final String VELOCITY_PROPERTIES = "velocity.properties";
	
	public static final String DOT = ".";
	
	public static final String DOMAIN_SUFFIX = "DO";
	
	public static final String CSV = "csv";
	public static final String CSV_TEMPLATE = "gen/csvtemplate.vm";
	public static final String CSV_EXTEND  = "sql";
	 
	public static final String CSV_INSERTSQL  = "insertsqls";
	
	@Autowired
	private DataSource datasource;
	
	protected DefaultBuildFactory(){ 
		Properties vprops = PropertiesUtils.load(VELOCITY_PROPERTIES); 
		//prop.put("file.resource.loader.path", "C:\\eclipse\\workspace\\AppTools\\resources\\template\\macro,C:\\eclipse\\workspace\\AppTools\\resources\\template\\web");
		//vprops.put("velocimacro.library", "gen/v_public.vm");
		Velocity.init(vprops); 
	}
	
	/**
	 * generator CSV file to insert SQL.
	 * 
	 * @param project
	 * @param res
	 * @param csvFileName
	 */
	public void buildCSVSql(String project,String res,String csvFileName){
		VelocityContext context = new VelocityContext();  
		 
		String basePath =  project+"/"+res+"/"+CSV+"/"+CSV_EXTEND;
		 
		String template = CSV_TEMPLATE;  
			
		String tableName = csvFileName.substring(0, csvFileName.lastIndexOf(DOT));
			
		//table
		Map<String, String> columnToPropertyOverrides = PropertiesUtils.loadForMap(OVERRIDES); 
		//read from Datebase
		Table table = getTable(tableName); 
		
		//Parsing csvFile and columns, return columnMap
		CSVBuildConfig ebc = new CSVBuildConfig(table,project+"/"+res+"/"+CSV+"/");
		List<List<String>> rows = ebc.readCSVFile(); 
		Map<String, Column> usedsortMap = ebc.getUsedSortMap();
			
		//build fileName
		String fileName = tableName.concat(DOT).concat(CSV_EXTEND);
			
		//index Map
		Map<Integer, Column> indexMap = new HashMap<Integer, Column>();
		//column Value
		Map<String,String> conMap = new HashMap<String,String>();
			
		//insertsqls
		Map<Integer,String> insertsqls = new HashMap<Integer,String>(); 
			
		int rowindex = 0;
		for (List<String> row : rows) {
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO ").append(tableName).append("(");
			for (Map.Entry<String, Column> entity : usedsortMap.entrySet()) {  
				sb.append(entity.getValue().getColumnName()).append(","); 
				indexMap.put(Integer.valueOf(entity.getValue().getOrder()), entity.getValue());
			}
			sb.deleteCharAt(sb.length() - 1).append(")");
			sb.append("VALUES(");
			int index = 0;
			String format = columnToPropertyOverrides.get("DATEFORMAT") == null? "yyyy-mm-dd":columnToPropertyOverrides.get("DATEFORMAT");
			for (String val : row) {
				Column column = indexMap.get(index++);
				if(column == null){
					continue;
				}
				String value = "";
				if (column.getJavaType().equals("String")) {
					value ="'"+ESecurityUtils.encodeForSQL(val)+"'";
				} else if (column.getJavaType().equals("Date")) {
					value = "to_date('" + val +"','"+format+"')";
				}else{
					value = val;
				}
				String repValue = columnToPropertyOverrides.get(column.getColumnName());
				if(repValue != null){
					value = repValue;
				}
				
				conMap.put(column.getColumnName(), value);
				sb.append(value).append(",");
			}
			sb.deleteCharAt(sb.length() - 1).append(");");
			String insertsql = sb.toString();
			insertsqls.put(rowindex, insertsql);
			sb.delete(0, sb.length()); 
			 
			rowindex++;
		} 
		
		context.put(CSV_INSERTSQL, insertsqls); 
		generator(basePath, fileName, template, context); 

	}
	
	/**
	 * get table from Database
	 * 
	 * @param tableName
	 * @return
	 */
	public Table getTable(String tableName){
		//table
		Map<String, String> columnToPropertyOverrides = PropertiesUtils.loadForMap(OVERRIDES); 
		Table table = new Table(tableName);
		String domainName = columnToPropertyOverrides.get(tableName);
		if (domainName == null) {
			domainName = StringUtils.camel(tableName);
		}
		table.setDomainName(domainName);
		//TableDO
		String tableDO = StringUtils.capitalize(domainName).concat(DOMAIN_SUFFIX); 
		
		table.setDesc(tableDO+" is generator by robot, don't make any changes");
		List<Column> columns = getColumns(tableName, columnToPropertyOverrides);
		table.setColumns(columns);
		return table;
	}
	
	/**
	 * get table columns from Database
	 * 
	 * @param tableName
	 * @param columnToPropertyOverrides
	 * @return
	 */
	private List<Column> getColumns(String tableName,
			Map<String, String> columnToPropertyOverrides) {
		List<Column> columns = new ArrayList<Column>();
		try { 
			Connection conn = datasource.getConnection();
			Statement smts = conn.createStatement();
			String sql = "SELECT * FROM " + tableName + " WHERE 1!=1";
			ResultSet rs = smts.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();

			for (int col = 1; col <= cols; col++) {
				String columnName = rsmd.getColumnLabel(col);
				if (null == columnName || 0 == columnName.length()) {
					columnName = rsmd.getColumnName(col);
				}
				String propertyName = columnToPropertyOverrides.get(columnName);
				if (propertyName == null) {
					propertyName = StringUtils.camel(columnName);
				}
				int dbTypeId = rsmd.getColumnType(col); 
				String columnType = SqlJavaType.getSqlType(dbTypeId);
				String javaType = SqlJavaType.getJavaType(dbTypeId); 
				String formType = SqlFormType.getFormType(columnType);
				Column column = new Column();
				column.setPropertyName(propertyName);
				column.setColumnName(columnName);
				column.setColumnType(columnType);
				column.setJavaType(javaType);
				column.setFormType(formType);
				column.setDesc(propertyName);
				column.setOrder(String.valueOf(col));
				columns.add(column);
			}
		} catch (Exception e) {
			log.error("can't init Table columns", e);
		}
		return columns;
	}
	
	/**
	 * public generator method 
	 * 
	 * @param basepkg
	 * @param pkg
	 * @param tableName
	 * @param suffix
	 * @param extend
	 * @param templateName
	 * @param context
	 */
	protected  void generator(String basePath,String fileName,String templateName,VelocityContext context) { 
		try {
			File filePath = new File(basePath); 
			if(!filePath.exists()){//file path not exit mkdirs
				filePath.mkdirs();
			}  
//			System.out.println(Velocity.getProperty("file.resource.loader.path"));
//			System.out.println(Velocity.getProperty("resource.loader"));
//			System.out.println(Velocity.getProperty("input.encoding"));
//			System.out.println(Velocity.getProperty("output.encoding"));
			Template template = Velocity.getTemplate(templateName);   
			
			File file = new File(filePath,fileName);
			String abspath = file.getAbsolutePath();
			if(!file.exists()){//file not exit create 
				file.createNewFile(); 
			}else{
				if(!CLEAN_FLAG){
					log.info("********** "+basePath+"/"+fileName+" is exists, not override**********");
					return;
				}
			}  
			
			//文件中的编码和Velocity输出的编码一致.
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(abspath), (String)Velocity.getProperty("output.encoding")
			));

			template.merge( context, out );
			out.flush();
			out.close();
			log.info("**********Generator "+basePath+"/"+fileName+" Success**********");
		} catch (Exception e) {
			log.error("can't init template", e);
		} 
	}
}
