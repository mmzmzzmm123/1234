package com.ruoyi.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.ResolvableType;

/***
 * 
 * 
 * Copyright: Copyright (c) 2022 HadLuo(罗政)
 * 
 * @ClassName: App
 * @Description: App spring 句柄方便获取
 *
 * @version: v1.0.0
 * @author: HadLuo(罗政)
 * @date: 2022年3月14日 下午7:20:41
 *
 */
public class App implements BeanFactoryAware, ApplicationContextAware, ApplicationEventPublisherAware {

	private static BeanFactory beanFactory;

	private static ApplicationContext applicationContext;

	private static ApplicationEventPublisher eventPublisher;

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static void register(BeanFactory beanFactory) {
		App.beanFactory = beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		App.beanFactory = beanFactory;
	}

	public static Object getBean(String name) throws BeansException {
		// TODO Auto-generated method stub
		return beanFactory.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		// TODO Auto-generated method stub
		return beanFactory.getBean(name, requiredType);
	}

	public static Object getBean(String name, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return beanFactory.getBean(name, args);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		// TODO Auto-generated method stub
		return beanFactory.getBean(requiredType);
	}

	public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return beanFactory.getBean(requiredType, args);
	}

	public boolean containsBean(String name) {
		// TODO Auto-generated method stub
		return beanFactory.containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return beanFactory.isSingleton(name);
	}

	public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return beanFactory.isPrototype(name);
	}

	public static boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return beanFactory.isTypeMatch(name, typeToMatch);
	}

	public static boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return beanFactory.isTypeMatch(name, typeToMatch);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return beanFactory.getType(name);
	}

	public static String[] getAliases(String name) {
		// TODO Auto-generated method stub
		return beanFactory.getAliases(name);
	}

	/***
	 * 
	 * title: 动态注入spring
	 *
	 * @param clazz
	 * @author HadLuo 2021-5-31 10:42:24
	 * @param <T>
	 */
	public static <T> T registerBeanDefinition(Class<T> clazz) {
		BeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName(clazz.getName());
		((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(clazz.getName(), beanDefinition);
		return beanFactory.getBean(clazz);
	}

	/***
	 * 
	 * title: 动态注入spring
	 *
	 * @param clazz
	 * @author HadLuo 2021-5-31 10:42:24
	 * @param <T>
	 */
	public static <T> T registerBeanDefinition(Class<T> clazz, BeanFactory _beanFactory) {
		BeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName(clazz.getName());
		((DefaultListableBeanFactory) _beanFactory).registerBeanDefinition(clazz.getName(), beanDefinition);
		return _beanFactory.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		App.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static String getRootPackagePath() {
		return applicationContext.getBeansWithAnnotation(SpringBootApplication.class).values().iterator().next()
				.getClass().getPackage().getName();
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		App.eventPublisher = applicationEventPublisher;
	}

	public static void publishApplicationEvent(ApplicationEvent event) {
		App.eventPublisher.publishEvent(event);
	}

}
