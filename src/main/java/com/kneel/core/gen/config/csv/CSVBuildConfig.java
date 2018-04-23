package com.kneel.core.gen.config.csv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;
import com.kneel.core.gen.config.Column;
import com.kneel.core.gen.config.Table;
import com.kneel.core.utils.NumberUtils;
import com.kneel.core.utils.StringUtils;

public class CSVBuildConfig {

//public static final String basePath="C:\\eclipse\\workspace\\spring-boot\\kneel-boot\\src\\test\\resources\\csv\\";
	
	private  Map<String,Column> columnMap = new HashMap<String,Column>();
	
	private String basePath;
	
	private String csvFile;
	
	/**
	 * clean Order, use CSV Order.
	 * 
	 * @param columns
	 */
	public CSVBuildConfig(Table table,String basePath){
		this.basePath=basePath;
		for(Column column:table.getColumns()){
			column.setOrder("");
			columnMap.put(column.getColumnName().toUpperCase(), column);
		}
		csvFile = table.getTableName().concat(".csv");
	}
	
	public Map<String,Column> getColumnMap(){
		return columnMap;
	}
	
	/**
	 * maybe not All columns, so return used sorted Column Map.
	 * so the return is The Excel's header there must be exists in Columns,
	 * but Columns may not be exists in Excel Header.
	 * so you can choose some of the columns. and the columns is Not Null in table.
	 * 
	 * @return
	 */
	public Map<String,Column> getUsedSortMap(){
		Map<String,Column> usedColumnMap = new HashMap<String,Column>();
		for (Map.Entry<String, Column> entity : columnMap.entrySet()) {
			if(StringUtils.isNotBlank(entity.getValue().getOrder())){
				usedColumnMap.put(entity.getKey(), entity.getValue());
			}
		}
		return sortByOrder(usedColumnMap);
	}

	/**
	 * sort this Map
	 * 
	 * @param usedColumnMap
	 * @return
	 */
	private Map<String, Column> sortByOrder(Map<String, Column> usedColumnMap) { 
		TreeMap<String,Column> tm = new TreeMap<String,Column>(); 
		for(Map.Entry<String,Column> entity:usedColumnMap.entrySet()){
			String nKey = entity.getValue().getOrder()+"_"+entity.getKey();
			tm.put(nKey, entity.getValue());
		} 
		return tm;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<List<String>> readCSVFile(){
		List<List<String>> lines = new ArrayList<List<String>>(); 
		try {
			boolean firstlineFlag = true; 
			CSVReaderBuilder builder = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(basePath+csvFile))); 
			builder.entryParser(new DefaultCSVEntryParser());
			builder.strategy(CSVStrategy.UK_DEFAULT);
			CSVReader<String[]> csvParser = builder.build();
			List<String[]> datas = csvParser.readAll();
			for (String[] data : datas) { 
				if(firstlineFlag){
					int index = 0;
					for(String headerKey:data){
						Column column = getColumnMap().get(headerKey);
						if(column == null){
							throw new RuntimeException("column name["+headerKey+"] not definition [" +getColumnMap()+"]");
						}
						column.setOrder(NumberUtils.fitDigit(index));
						index++;
					}
					firstlineFlag = false;
				}else{
					List<String> line = Arrays.asList(data);
					lines.add(line);
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
}
