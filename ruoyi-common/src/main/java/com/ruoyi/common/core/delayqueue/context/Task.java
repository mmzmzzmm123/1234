package com.ruoyi.common.core.delayqueue.context;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson2.JSON;

/**
 * 
 * title: ��ʱ ����
 *
 * @author HadLuo
 * @date 2020-9-15 10:44:40
 */
public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5743096866266963934L;

	/**
	 * ����
	 */
	private String data;

	/**
	 * ��ʱʱ��ֵ
	 */
	private long delay;

	private TimeUnit timeUnit;

	private String dataClass;

	private Task() {
	}

	/***
	 * title: ��������
	 * 
	 * @param data        �������
	 * @param delaySecond ��ʱ ִ�е�����
	 * @return
	 */
	public static Task newTask(Object data, long delaySecond) {
		Task task = new Task();
		task.setData(JSON.toJSONString(data));
		task.setDataClass(data.getClass().getName());
		task.setDelay(delaySecond);
		task.setTimeUnit(TimeUnit.SECONDS);
		return task;
	}

	/***
	 * title: ��������
	 * 
	 * @param data     �������
	 * @param delay    ��ʱ ִ�е�ֵ
	 * @param timeUnit ��ʱִ��ֵ�ĵ�λ
	 * @return
	 */
	public static Task newTask(Object data, long delay, TimeUnit timeUnit) {
		Task task = new Task();
		task.setData(JSON.toJSONString(data));
		task.setDataClass(data.getClass().getName());
		task.setDelay(delay);
		task.setTimeUnit(timeUnit);
		return task;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getDelay() {
		return delay;
	}

	public long getDelayMilliSecond() {
		if (timeUnit == TimeUnit.SECONDS) {
			return delay * 1000;
		}
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getDataClass() {
		return dataClass;
	}

	public void setDataClass(String dataClass) {
		this.dataClass = dataClass;
	}

}
