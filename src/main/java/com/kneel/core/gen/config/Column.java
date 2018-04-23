package com.kneel.core.gen.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
 
@XmlType(name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
public class Column implements Comparable<Column>{ 
	
	@XmlAttribute
	private String order;  
	@XmlAttribute
	private String formType;
	@XmlAttribute
	private String javaType;
	@XmlAttribute
	private String columnType;
	@XmlAttribute
	private String desc;
	@XmlAttribute
	private String propertyName;
	@XmlAttribute
	private String columnName;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	 
	@Override
	public int compareTo(Column o) {  
		Double d1 = Double.valueOf(o.getOrder()); 
		Double v = Double.valueOf(this.getOrder())-d1; 
		if (v<0){
			return -1;
		}else if(v>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	
}
