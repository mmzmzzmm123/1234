package com.ruoyi.common.core.delayqueue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonFastDelayQueueClient {

	/***
	 * ҵ���ʶ�� �浽reddison�����е�key ǰ׺
	 * 
	 * @return
	 */
	String businessKey() default "";

	/***
	 * title�� ��ȡ�������ݵ��߳���(ҵ��ִ�й���������΢����)
	 * 
	 * @return
	 */
	public int pollEventNumber() default 0;

	/***
	 * title: ��ʱ���е� ������ (��ʱ��ƿ��ʱ ���ʵ�������������ǻ��redis cpu ����)
	 * 
	 * @return
	 */
	public int partition() default 0;
}
