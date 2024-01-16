package com.ruoyi.common.utils.spi;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/***
 * 
 * Copyright: Copyright (c) 2022 HadLuo(罗政)
 * 
 * @ClassName: Classes.java
 * @Description: 反射工具
 *
 * @version: v1.0.0
 * @author: HadLuo(罗政)
 * @date: 2022年2月24日 下午3:48:44
 *
 */
@Slf4j
public class Classes {

	private Classes() {
	}

	private static final String Alias = "[反射异常]";

	/**
	 * 
	 * @Description: 把map对象转换为javabean
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午3:50:37
	 *
	 */
	public static <T> T mapToJavaBean(Map<String, Object> map, Class<T> beantype) throws Exception {
		if (map == null || beantype == null) {
			return null;
		}
		T object = beantype.newInstance();// 创建对象
		// 获取类的属性描述器
		BeanInfo beaninfo = Introspector.getBeanInfo(beantype, Object.class);
		// 获取类的属性集
		PropertyDescriptor[] pro = beaninfo.getPropertyDescriptors();
		for (PropertyDescriptor property : pro) {
			// 获取属性的名字
			String name = property.getName();
			Object value = map.get(name);// 得到属性name在map中对应的value。
			Method set = property.getWriteMethod();// 得到属性的set方法
			// 接下来将map的value转换为属性的value
			set.invoke(object, value);// 执行set方法
		}
		return object;
	}

	/***
	 * 
	 * @Description: 根据名称加载class对象
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午3:53:57
	 *
	 */
	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (Throwable e) {
			log.error(Alias + "class for name error :{}", className, e);
		}
		return null;
	}

	/***
	 * 
	 * @Description: 实例化class
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午3:54:12
	 *
	 */
	public static <T> T newInstance(Class<T> clazz) {
		if (null == clazz) {
			return null;
		}
		try {
			return clazz.newInstance();
		} catch (Throwable e) {
			log.error(Alias + "class newInstance error :{}", clazz.getName(), e);
		}
		return null;
	}

	/***
	 * 
	 * @Description: 获取 对象 某名称字段的值 (包括从对象的父类中获取)
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午3:57:28
	 *
	 */
	public static Object resolveFieldValWithSuper(Object instance, String fName) {
		if (null == instance || StringUtils.isEmpty(fName)) {
			return null;
		}
		Class<?> tempClass = instance.getClass();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			try {
				Field field = tempClass.getDeclaredField(fName);
				if (field == null) {
					continue;
				}
				field.setAccessible(true);
				return field.get(instance);
			} catch (Exception e) {
			}
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}
		return null;
	}

	/***
	 * 
	 * @Description: 设置字段的值 (不包括从对象的父类中设置)
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午3:58:22
	 *
	 */
	public static void injectFieldVal(Object instance, String fName, Object fValue) {
		if (null == instance || StringUtils.isEmpty(fName)) {
			return;
		}
		Class<?> clazz = instance.getClass();
		try {
			Field field = clazz.getDeclaredField(fName);
			if (field == null) {
				return;
			}
			field.setAccessible(true);
			field.set(instance, fValue);
		} catch (Throwable e) {
		}
	}

	/***
	 * 
	 * @Description: 设置字段的值 (包括从对象的父类中设置)
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午3:59:32
	 *
	 */
	public static void injectFieldValWithSuper(Object instance, String fName, Object fValue) {
		Class<?> tempClass = instance.getClass();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			try {
				Field field = tempClass.getDeclaredField(fName);
				if (field == null) {
					continue;
				}
				field.setAccessible(true);
				field.set(instance, fValue);
				return;
			} catch (Exception e) {
			}
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}
	}

	/***
	 * 
	 * @Description: 查询某个 field ，没有则返回null (不包含父类)
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午3:59:48
	 *
	 */
	public static Field selectField(Class<?> clazz, String fName) {
		if (clazz == null || fName == null) {
			return null;
		}
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getName().equals(fName)) {
				return field;
			}
		}
		return null;
	}

	/***
	 * 
	 * @Description: 查询某个 field ，没有则返回null(包括从父类中查询)
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午4:00:02
	 *
	 */
	public static Field selectFieldWithSuper(Class<?> clazz, String fName) {
		if (clazz == null || fName == null) {
			return null;
		}
		Class<?> tempClass = clazz;
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			try {
				Field field = tempClass.getDeclaredField(fName);
				if (field != null) {
					return field;
				}
			} catch (Exception e) {
			}
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}
		return null;
	}

	public static Set<Class<?>> selectSuperClass(Class<?> clazz) {
		Class<?> tempClass = clazz;
		Set<Class<?>> results = new HashSet<>();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
			if (tempClass != null) {
				results.add(tempClass);
			}
		}
		return results;
	}

	/***
	 * 
	 * @Description: 融合两个bean （将source bean对象的字段A 拷贝到target
	 *               bean对象的字段A里面，前提是target里面有的字段 ）
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午4:00:47
	 *
	 */
	public static void mergeBean(Object source, Object target) {
		if (source == null || target == null) {
			return;
		}
		Field field = null;
		Class<?> targetClass = target.getClass();
		for (Field targetField : targetClass.getDeclaredFields()) {
			if (Modifier.isStatic(targetField.getModifiers())) {
				// 不支持静态 复制
				continue;
			}
			Object val = null;
			try {
				field = Classes.selectFieldWithSuper(source.getClass(), targetField.getName());
				if (field == null) {
					continue;
				}
				field.setAccessible(true);
				val = field.get(source);
				if (val == null) {
					// 值为空，
					continue;
				}
				Classes.injectFieldVal(target, targetField.getName(), val);
			} catch (Throwable e) {
			}
		}
	}

	/***
	 * 
	 * @Description: 拷贝一个bean对象（字段也全量复制）
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午4:01:36
	 *
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyBean(Object source) {
		if (source == null) {
			return null;
		}
		Object target = newInstance(source.getClass());
		BeanUtils.copyProperties(source, target);
		return (T) target;
	}

	/***
	 * 
	 * @Description: 查找 实现类的所有接口是否配置了指定注解
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @author: HadLuo(罗政) 2022年2月24日 下午4:02:43
	 *
	 */
	public static List<Class<?>> findInterfacesOfAnnotation(Class<?> impl, Class<? extends Annotation> anno) {
		if (impl == null) {
			return null;
		}
		Class<?>[] inters = impl.getInterfaces();
		if (inters == null || inters.length == 0) {
			return null;
		}
		List<Class<?>> classes = new ArrayList<>();
		for (Class<?> cls : inters) {
			if (anno != null && cls.isAnnotationPresent(anno)) {
				classes.add(cls);
			}
		}
		return classes;
	}

	public static <T> T copy(Object source, Class<T> clz) {
		T t = Classes.newInstance(clz);
		BeanUtils.copyProperties(source, t);
		return t;
	}

}
