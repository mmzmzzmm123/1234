package com.ruoyi.common.core.delayqueue.context;

/**
 * 
 * title: ��ʱ����
 *
 * @author HadLuo
 * @date 2020-9-15 10:54:17
 */
public interface DelayQueue {
	/**
	 * 
	 * title: 放任�?
	 *
	 * @param      <T>
	 * @param task
	 * @author HadLuo 2020-9-15 10:48:01
	 */
	public <T> void put(Task task);

	/**
	 * 
	 * title: �?毁队�?
	 *
	 * @author HadLuo 2020-9-15 10:48:10
	 */
	public void destory();

}
