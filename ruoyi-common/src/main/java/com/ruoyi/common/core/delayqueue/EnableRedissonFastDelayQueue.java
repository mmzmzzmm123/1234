package com.ruoyi.common.core.delayqueue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RedissonFastQueueRegistrar.class)
public @interface EnableRedissonFastDelayQueue {

	/***
	 * title�� ��ȡ�������ݵ��߳���(ҵ��ִ�й���������΢����)
	 * 
	 * @return
	 */
	public int poll() default 2;

	/***
	 * title: ��ʱ���е� ������ (��ʱ��ƿ��ʱ ���ʵ�������������ǻ��redis cpu ����)
	 * 
	 * @return
	 */
	public int partition() default 3;

}
