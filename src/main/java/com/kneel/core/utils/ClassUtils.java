package com.kneel.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassUtils {
	
	private static Log logger = LogFactory.getLog(ClassUtils.class); 

	/**
	 * loader this class to ClassLoader.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> classForName(String clazz) {
		try {
			return Class.forName(clazz);
		} catch (ClassNotFoundException e) { 
			throw new RuntimeException("Class Name("+clazz+") NOT FOUND!", e);
		} 
	}

	/**
	 * Factory method that returns a new instance of the given Class. This is
	 * called at the start of the bean creation process and may be overridden to
	 * provide custom behavior like returning a cached bean instance.
	 * 
	 * @param <T>
	 *            The type of object to create
	 * @param c
	 *            The Class to create an object from.
	 * @return A newly created object of the Class.
	 * @throws SQLException
	 *             if creation failed.
	 */
	public static <T> T newInstance(Class<T> c){
		try {
			return c.newInstance();

		} catch (InstantiationException e) {
			throw new RuntimeException("Cannot create " + c.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot create " + c.getName() + ": "
					+ e.getMessage());
		}
	}
	
	/**
	 * Transform constants to Map
	 * 
	 * if it is class Field, no need to pass instance.
	 * 
	 * @param c
	 * @return
	 */
	public static <T>Map<String,Object> constantMap(Class<T> c){
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = c.getDeclaredFields(); 
		for(Field field:fields){
			try {
				map.put(field.getName(),field.getInt(null));
			} catch (IllegalArgumentException e) {
				logger.error("Argument illeagal", e);
			} catch (IllegalAccessException e) {
				logger.error("Access illeagal", e);
			}
		} 
		return map;
	}
	
	/**
	 * Transform Object to Map
	 * 
	 * if it is class Field, no need to pass instance.
	 * 
	 * @param c
	 * @return
	 */
	public static Map<String,Object> domainMap(Object o){
		Map<String,Object> map = new HashMap<String,Object>();
		PropertyDescriptor[] pds = propertyDescriptors(o.getClass());
		for(PropertyDescriptor pd:pds){
			try {
				if("class".equals(pd.getName())){
					continue;
				}
				Object value = invokeGetter(o,pd);
				if(value != null){
					map.put(pd.getName(),value);
				} 
			} catch (IllegalArgumentException e) {
				logger.error("Argument illeagal", e);
			} 
		} 
		return map;
	}

	/**
	 * Returns a PropertyDescriptor[] for the given Class.
	 * 
	 * @param c
	 *            The Class to retrieve PropertyDescriptors for.
	 * @return A PropertyDescriptor[] describing the Class.
	 * @throws SQLException
	 *             if introspection failed.
	 */
	public static PropertyDescriptor[] propertyDescriptors(Class<?> c){
		// Introspector caches BeanInfo classes for better performance
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(c);

		} catch (IntrospectionException e) {
			throw new RuntimeException("Bean introspection failed: "
					+ e.getMessage());
		}

		return beanInfo.getPropertyDescriptors();
	}

	/**
	 * Calls the setter method on the target object for the given property. If
	 * no setter method exists for the property, this method does nothing.
	 * 
	 * @see invoke(target, getter)
	 * 
	 * @param target
	 *            The object to set the property on.
	 * @param prop
	 *            The property to set.
	 * @param value
	 *            The value to pass into the setter.
	 * @throws SQLException
	 *             if an error occurs setting the property.
	 */
	public static Object invokeGetter(Object target, PropertyDescriptor prop) {
		Method getter = prop.getReadMethod();
		if (getter == null) {
			return null;
		}
		return invoke(target, getter);

	}

	/** 
	 * 
	 * Calls the getter method on the target object for the given property. If
	 * no setter method exists for the property, this method does nothing.
	 * 
	 * @see invokeGetter(target, pd)
	 * 
	 * @param target
	 * @param property
	 * @return
	 * @throws SQLException
	 */
	public static Object invokeGetter(Object target, String property){
		Object value = null;
		try {
			PropertyDescriptor[] pds = propertyDescriptors(target.getClass());
			for (PropertyDescriptor pd : pds) {
				if (pd.getName().equalsIgnoreCase(property)) {
					value = invokeGetter(target, pd);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot get " + property + ": "
					+ e.getMessage());
		}
		return value;
	}

	/**
	 * Calls the setter method on the target object for the given property. If
	 * no setter method exists for the property, this method does nothing.
	 * 
	 * @see invokeSetter(target, pd, ovalue)
	 * 
	 * @param target
	 *            The object to set the property on.
	 * @param prop
	 *            The property to set.
	 * @param value
	 *            The value to pass into the setter.
	 * @throws SQLException
	 *             if an error occurs setting the property.
	 * @throws ParseException
	 */
	public static void invokeSetter(Object target, String property, Object obj){
		try {
			PropertyDescriptor[] pds = propertyDescriptors(target.getClass());
			for (PropertyDescriptor pd : pds) {
				if (pd.getName().equalsIgnoreCase(property)) {
					Object ovalue = null;
					Class<?> propType = pd.getPropertyType();
					if(obj instanceof String){
						String value = (String)obj;
						if (propType.isAssignableFrom(Long.class)) {
							ovalue = Long.valueOf(value);
						} else if (propType.isAssignableFrom(Date.class)) {
							if(StringUtils.isNotBlank(value)){
								ovalue = new SimpleDateFormat("MM/dd/yyyy")
								.parse(value);
							}
						} else if (propType.isAssignableFrom(Integer.class)) {
							ovalue = Integer.valueOf(value);
						} else if (propType.isAssignableFrom(Boolean.class)) {
							ovalue = Boolean.valueOf(value);
						} else if (propType.isAssignableFrom(String.class)) {
							ovalue = value;
						}
						invokeSetter(target, pd, ovalue);
					}else{
						invokeSetter(target, pd, obj);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot get " + property + ": "
					+ e.getMessage());
		}
	}

	/**
	 * Calls the setter method on the target object for the given property. If
	 * no setter method exists for the property, this method does nothing.
	 * 
	 * @see invoke(target, setter, new Object[] { value })
	 * 
	 * @param target
	 *            The object to set the property on.
	 * @param prop
	 *            The property to set.
	 * @param value
	 *            The value to pass into the setter.
	 * @throws SQLException
	 *             if an error occurs setting the property.
	 */
	@SuppressWarnings("unchecked")
	public static void invokeSetter(Object target, PropertyDescriptor prop,
			Object value){

		Method setter = prop.getWriteMethod();

		if (setter == null) {
			return;
		}

		Class<?>[] params = setter.getParameterTypes();
		// convert types for some popular ones
		if (value instanceof java.util.Date) {
			final String targetType = params[0].getName();
			if ("java.sql.Date".equals(targetType)) {
				value = new java.sql.Date(((java.util.Date) value).getTime());
			} else if ("java.sql.Time".equals(targetType)) {
				value = new java.sql.Time(((java.util.Date) value).getTime());
			} else if ("java.sql.Timestamp".equals(targetType)) {
				Timestamp tsValue = (Timestamp) value;
				int nanos = tsValue.getNanos();
				value = new java.sql.Timestamp(tsValue.getTime());
				((Timestamp) value).setNanos(nanos);
			}
		} else {
			if (value instanceof String && params[0].isEnum()) {
				value = Enum.valueOf(params[0].asSubclass(Enum.class),
						(String) value);
			}
		}

		// Don't call setter if the value object isn't the right type
		if (isCompatibleType(value, params[0])) {
			invoke(target, setter, new Object[] { value });
		} else {
			throw new RuntimeException("Cannot set " + prop.getName()
					+ ": incompatible types, cannot convert "
					+ value.getClass().getName() + " to " + params[0].getName());
			// value cannot be null here because isCompatibleType allows null
		}

	}

	/**
	 * ResultSet.getObject() returns an Integer object for an INT column. The
	 * setter method for the property might take an Integer or a primitive int.
	 * This method returns true if the value can be successfully passed into the
	 * setter method. Remember, Method.invoke() handles the unwrapping of
	 * Integer into an int.
	 * 
	 * @param value
	 *            The value to be passed into the setter method.
	 * @param type
	 *            The setter's parameter type (non-null)
	 * @return boolean True if the value is compatible (null => true)
	 */
	public static boolean isCompatibleType(Object value, Class<?> type) {
		// Do object check first, then primitives
		if (value == null || type.isInstance(value)) {
			return true;

		} else if (type.equals(Integer.TYPE) && value instanceof Integer) {
			return true;

		} else if (type.equals(Long.TYPE) && value instanceof Long) {
			return true;

		} else if (type.equals(Double.TYPE) && value instanceof Double) {
			return true;

		} else if (type.equals(Float.TYPE) && value instanceof Float) {
			return true;

		} else if (type.equals(Short.TYPE) && value instanceof Short) {
			return true;

		} else if (type.equals(Byte.TYPE) && value instanceof Byte) {
			return true;

		} else if (type.equals(Character.TYPE) && value instanceof Character) {
			return true;

		} else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
			return true;

		}
		return false;

	}

	/**
	 * invoke bean method
	 * 
	 * @see invoke(bean,method,new Object[]{}).
	 * 
	 * @param bean
	 * @param method
	 * @return
	 */
	public static Object invoke(Object bean, Method method) {
		return invoke(bean,method,new Object[]{});
	}
	
	/**
	 * real invoke bean, method, params
	 * 
	 * @param bean
	 * @param method
	 * @param params
	 * @return
	 */
	public static Object invoke(Object bean, Method method, Object[] params) {
		Object re = null;
		try {
			method.setAccessible(true);// can invoke private method
			re = method.invoke(bean, params);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Cannot invoke " + method.getName() + "( "
					+ Arrays.toString(params) +")",e);		
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot invoke " + method.getName() + "( "
					+ Arrays.toString(params) +")",e);		
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot invoke " + method.getName() + "( "
					+ Arrays.toString(params) +")",e);		
		}
		return re;
	}
	
	/**
	 * this invoke not based on property, just use method name.
	 * 
	 * @param bean
	 * @param method
	 * @return
	 */
	public static Object invoke(Object bean, String method) {
		return invoke(bean,method,new Object[]{});
	}

	/**
	 * this invoke not based on property, just use method name.
	 * 
	 * 1. first find method
	 * 2. invoke method.
	 * 
	 * @param bean
	 * @param method
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object invoke(Object bean, String method, Object[] params) {
		Object re = null;
		Class[] parameterTypes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			parameterTypes[i] = params[i].getClass();
		}
		try {
			Method inmethod =findMethod(bean,method,parameterTypes); 
			re = invoke(bean,inmethod,params); 
		} catch (SecurityException e) {
			throw new RuntimeException(bean + " Cannot invoke " + method + "( "
					+ Arrays.toString(params) +")"+ e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(bean + " Cannot invoke " + method + "( "
					+ Arrays.toString(params) +")"+ e.getMessage());
		}
		return re;
	}
	
	/**
	 * All target properties, iterator source object, to get value and set to owner.
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyProps(Object source,Object target){
		PropertyDescriptor[] sourcepds = ClassUtils.propertyDescriptors(source.getClass());
		PropertyDescriptor[] targetpds = ClassUtils.propertyDescriptors(target.getClass());
		for(PropertyDescriptor targetpd:targetpds){
			for(PropertyDescriptor sourcepd:sourcepds){
				if(targetpd.equals(sourcepd)){
					Object obj = ClassUtils.invokeGetter(source, sourcepd);
					ClassUtils.invokeSetter(target, targetpd, obj);
				}
			}
		} 
	}
	
	/**
	 * find method in class and super class
	 * 
	 * @param bean
	 * @param method
	 * @param parameterTypes
	 * @return
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("rawtypes")
	public static Method findMethod(Object bean, String method,Class[] parameterTypes){
		Method inmethod = null;
		try {
			inmethod = bean.getClass().getDeclaredMethod(method,
					parameterTypes); 
		} catch (SecurityException e) {
			throw new RuntimeException(bean + " Cannot find " + method + "( "
					+ Arrays.toString(parameterTypes) +")"+ e.getMessage());
		} catch (NoSuchMethodException e) { 
			try {
				inmethod = bean.getClass().getSuperclass().getDeclaredMethod(method,
						parameterTypes);
			} catch (SecurityException e1) {
				throw new RuntimeException(bean + " Cannot find " + method + "( "
						+ Arrays.toString(parameterTypes) +")"+ e.getMessage());
			} catch (NoSuchMethodException e1) {
				throw new RuntimeException(bean + " Cannot find " + method + "( "
						+ Arrays.toString(parameterTypes) +")"+ e.getMessage());
			}
		}
		return inmethod;
		
	} 
}
