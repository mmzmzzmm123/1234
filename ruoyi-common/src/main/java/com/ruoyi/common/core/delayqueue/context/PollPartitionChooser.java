package com.ruoyi.common.core.delayqueue.context;

import java.util.concurrent.atomic.AtomicInteger;

/***
 * 
 * title:��ѯ
 *
 * @author HadLuo
 * @date 2020-12-24 15:43:55
 */
public class PollPartitionChooser implements PartitionChooser {

	private volatile AtomicInteger count = new AtomicInteger(0);

	private final int max;

	public PollPartitionChooser(int max) {
		this.max = max;
	}

	private int reset() {
		count.set(0);
		return 0;
	}

	@Override
	public synchronized int next() {
		// get volidate
		int cur = count.get();
		if (cur >= max) {
			cur = reset();
		} else {
			// incr
			count.incrementAndGet();
		}
		return cur;
	}
}
