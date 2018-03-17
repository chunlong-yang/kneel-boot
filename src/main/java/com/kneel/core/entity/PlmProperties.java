package com.kneel.core.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@Entity
public class PlmProperties implements Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plm_propertiesseq")
    @SequenceGenerator(sequenceName = "plm_propertiesseq", allocationSize = 1, name = "plm_propertiesseq")
	private Long propertyid;
 
	private String property;
 
	private String value;
 
	private String env;
 
	private String propcategory;
 
	private String override;
 
	private Long priority;

	public Long getPropertyid() {
		return propertyid;
	}

	public void setPropertyid(Long propertyid) {
		this.propertyid = propertyid;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getPropcategory() {
		return propcategory;
	}

	public void setPropcategory(String propcategory) {
		this.propcategory = propcategory;
	}

	public String getOverride() {
		return override;
	}

	public void setOverride(String override) {
		this.override = override;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}
}
