package com.kneel.core.gen.config.type;

import java.sql.Types;
import java.util.Map;

import com.kneel.core.utils.ClassUtils;

/**
 * via Database Data Type to decide Java Type
 * 
 * @author e557400
 *
 */
public class SqlJavaType {
	
	/**
	 * get SQL type from dbTypeName.
	 * 
	 * @param dbTypeName
	 * @return
	 */
	public static int getSQLTypeInt(String dbTypeName){
		Map<String, Object> map = ClassUtils.constantMap(Types.class);
		return (Integer)map.get(dbTypeName);
	}

	/**
	 * change dbType to Java Type.
	 * 
	 * { INTEGER=4, SQLXML=2009, BLOB=2004, VARBINARY=-3, OTHER=1111,
	 * DATALINK=70, LONGNVARCHAR=-16, NCHAR=-15, LONGVARBINARY=-4, NULL=0,
	 * CLOB=2005, CHAR=1, VARCHAR=12, STRUCT=2002, FLOAT=6, NUMERIC=2,
	 * NCLOB=2011, REF=2006, REAL=7, TIME=92, BOOLEAN=16, DECIMAL=3,
	 * LONGVARCHAR=-1, BIGINT=-5, JAVA_OBJECT=2000, ROWID=-8, TINYINT=-6,
	 * DOUBLE=8, BIT=-7, BINARY=-2, DATE=91, NVARCHAR=-9, DISTINCT=2001,
	 * TIMESTAMP=93, ARRAY=2003, SMALLINT=5}
	 * 
	 * @param dbTypeName
	 * @return
	 */
	public static String getJavaType(String dbTypeName) {
		Map<String, Object> map = ClassUtils.constantMap(Types.class);
		return getJavaType((Integer)map.get(dbTypeName));
	} 
	
	/**
	 * difference database return different column Type Name.
	 * we use JDK standard.
	 * 
	 * @param dbTypeId
	 * @return
	 */
	public static String getSqlType(int dbTypeId){
		Map<String, Object> map = ClassUtils.constantMap(Types.class);
		for(Map.Entry<String, Object> entity:map.entrySet()){
			Integer v = (Integer) entity.getValue();
			if(v == dbTypeId){
				return entity.getKey();
			}
		}
		return null;
	}

	/**
	 * get Java Type String By dbTypeid.
	 * this is better than we design yesterday night.
	 * 
	 * @param dbTypeId
	 * @return
	 */
	public static String getJavaType(int dbTypeId) {
		String javaType = "";
		switch (dbTypeId) { 
			case Types.TINYINT:
			case Types.BIT:
			case Types.SMALLINT:
				javaType = "Integer";
				break;
			case Types.INTEGER:
			case Types.NUMERIC:
			case Types.BIGINT:
				javaType = "Long";
				break;
			case Types.FLOAT:
				javaType = "Float";
				break; 
			case Types.DOUBLE:
				javaType = "Double";
				break;
			case Types.BOOLEAN:
				javaType = "Boolean";
				break; 
			case Types.VARCHAR:
			case Types.NVARCHAR: 
			case Types.CHAR:
				javaType = "String";
				break; 
			case Types.DATE:
			case Types.TIMESTAMP:
			case Types.TIME:
				//javaType = "Timestamp";
				//javaType = "Time";
				javaType = "Date";
				break;
			default:
				javaType = "String";
				break;
		}
	  return javaType;
	}
}
