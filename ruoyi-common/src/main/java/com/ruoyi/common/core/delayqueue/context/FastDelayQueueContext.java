package com.ruoyi.common.core.delayqueue.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.util.StringUtils;

public class FastDelayQueueContext {

	private static final Map<String, DelayQueue> businessKeyInstance = new ConcurrentHashMap<>();

	private static final Map<Class<?>, String> classInstance = new ConcurrentHashMap<>();

	public static void putContext(Class<?> clazz, String businessKey, DelayQueue queue) {
		businessKeyInstance.putIfAbsent(businessKey, queue);
		classInstance.putIfAbsent(clazz, businessKey);
	}

	/***
	 * title: ����ʱ�������� ������ʱ����
	 * 
	 * @param receiveClientClass ��ʱ���񴥷�ִ�е� �ͻ���
	 * @param task
	 */
	public static void send(Class<?> receiveClientClass, Task task) {
		if (null == receiveClientClass) {
			return;
		}
		String businessKey = classInstance.get(receiveClientClass);

		if (StringUtils.isEmpty(businessKey)) {
			throw new RuntimeException(
					receiveClientClass.getName() + " ����һ�� ��ʱ���н��տͻ��ˣ�������@RedissonDelayFastQueueClient");
		}
		businessKeyInstance.get(businessKey).put(task);
	}

	/***
	 * title: ����ʱ�������� ������ʱ����
	 * 
	 * @param receiveClientClass ��ʱ���񴥷�ִ�е� �ͻ���
	 * @param data               ���ݶ���
	 * @param delaySecond        ��ʱ������
	 */
	public static void send(Class<?> receiveClientClass, Object data, long delaySecond) {
		if (null == receiveClientClass || data == null) {
			return;
		}
		String businessKey = classInstance.get(receiveClientClass);

		if (StringUtils.isEmpty(businessKey)) {
			throw new RuntimeException(
					receiveClientClass.getName() + " ����һ�� ��ʱ���н��տͻ��ˣ�������@RedissonDelayFastQueueClient");
		}
		businessKeyInstance.get(businessKey).put(Task.newTask(data, delaySecond));
	}

	/***
	 * title: ����ʱ�������� ������ʱ����
	 * 
	 * @param receiveClientClass ��ʱ���񴥷�ִ�е� �ͻ���
	 * @param data               ���ݶ���
	 * @param delay              ��ʱ����ֵ
	 * @param timeUnit           ��ʱ����ֵ��λ
	 */
	public static void send(Class<?> receiveClientClass, Object data, long delay, TimeUnit timeUnit) {
		if (null == receiveClientClass || data == null) {
			return;
		}
		String businessKey = classInstance.get(receiveClientClass);

		if (StringUtils.isEmpty(businessKey)) {
			throw new RuntimeException(
					receiveClientClass.getName() + " ����һ�� ��ʱ���н��տͻ��ˣ�������@RedissonDelayFastQueueClient");
		}
		businessKeyInstance.get(businessKey).put(Task.newTask(data, delay, timeUnit));
	}

}
