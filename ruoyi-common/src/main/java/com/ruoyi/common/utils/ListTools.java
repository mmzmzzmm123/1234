package com.ruoyi.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.compress.utils.Lists;
import org.springframework.util.CollectionUtils;
import lombok.Data;

public class ListTools {

	public static interface SelectOne<T, R> {
		public R select(T item);
	}

	public static <T> List<T> emptyArrayList() {
		return new ArrayList<>(0);
	}

	/***
	 * 将集合中的每个元素 丢到lambada进行处理，返回每个处理结果的集合汇总
	 * 
	 * @param source
	 * @param lambada
	 * @return
	 */
	public static <T, I> List<T> map(List<I> source, SelectOne<I, List<T>> lambada) {
		if (CollectionUtils.isEmpty(source) || null == lambada) {
			return emptyArrayList();
		}
		List<T> results = emptyArrayList();
		for (I input : source) {
			List<T> datas = lambada.select(input);
			if (!CollectionUtils.isEmpty(datas)) {
				results.addAll(datas);
			}
		}
		return results;
	}

	@SafeVarargs
	public static <T> List<T> newArrayList(T... initValues) {
		if (initValues == null || initValues.length == 0) {
			return new ArrayList<>();
		}
		ArrayList<T> datas = new ArrayList<>();

		for (T data : initValues) {
			datas.add(data);
		}
		return datas;
	}

	public static <T> Set<T> newHashSet(Collection<T> list) {
		if (list == null || list.size() == 0) {
			return new HashSet<>();
		}
		return new HashSet<>(list);
	}

	/***
	 * 
	 * title: 给定集合A ，集合B， 排除在集合A内，又在集合B中的元素 生成一个新集合
	 *
	 * @param          <T>
	 * @param resource
	 * @param notIn
	 * @return
	 * @author HadLuo 2020-10-27 13:33:06
	 */
	public static <T> List<T> exclude(List<T> resource, List<T> notIn) {
		if (CollectionUtils.isEmpty(notIn)) {
			return resource;
		}
		List<T> results = Lists.newArrayList();
		for (T t : resource) {
			if (!notIn.contains(t)) {
				results.add(t);
			}
		}
		return results;
	}

	/***
	 * title ： 将 集合 按照特定的字段值分组成map
	 * 
	 * @param           <R>
	 * @param           <T>
	 * @param sources
	 * @param selectOne
	 * @return
	 */
	public static <R, T> Map<R, List<T>> group(List<T> sources, SelectOne<T, R> selectOne) {
		if (CollectionUtils.isEmpty(sources)) {
			return new HashMap<R, List<T>>(0);
		}
		Map<R, List<T>> map = new HashMap<>();
		for (T item : sources) {
			R key = selectOne.select(item);
			List<T> val = map.get(key);
			if (val == null) {
				val = Lists.newArrayList();
				map.put(key, val);
			}
			val.add(item);
		}
		return map;
	}

	/**
	 * 抽取集合对象中 某字段 成为另外一个集合
	 * 
	 * @param           <T>
	 * @param           <R>
	 * @param sources
	 * @param selectOne
	 * @return
	 */
	public static <T, R> List<R> extract(List<T> sources, SelectOne<T, R> selectOne) {
		if (CollectionUtils.isEmpty(sources)) {
			return Collections.emptyList();
		}
		List<R> list = Lists.newArrayList();
		for (T item : sources) {
			list.add(selectOne.select(item));
		}
		return list;
	}

	/**
	 * 
	 * title: 从集合中找出 符合特定规则 的 某一项
	 *
	 * @param sources
	 * @param itemExpectVal 集合中预期的值
	 * @param selectOne
	 * @return
	 * @author HadLuo 2020-9-18 15:41:02
	 * @param <R>
	 */
	public static <T, R> T selectOne(List<T> sources, Object itemExpectVal, SelectOne<T, R> selectOne) {
		if (CollectionUtils.isEmpty(sources)) {
			return null;
		}
		if (selectOne == null) {
			return null;
		}
		for (T item : sources) {
			Object v = selectOne.select(item);
			if (v == null && itemExpectVal == null) {
				return item;
			}
			if (v != null && v.equals(itemExpectVal)) {
				return item;
			}
		}
		return null;
	}

	public static <T, R> List<T> distinct(List<T> sources, SelectOne<T, R> selectOne) {
		if (CollectionUtils.isEmpty(sources)) {
			return sources;
		}
		List<T> results = new ArrayList<>();
		List<R> dis = new ArrayList<>();
		sources.forEach(item -> {
			R r = selectOne.select(item);
			if (!dis.contains(r)) {
				results.add(item);
				dis.add(r);
			}
		});
		return results;
	}

	@SuppressWarnings("unchecked")
	public static <T> T sum(List<T> sources) {
		if (CollectionUtils.isEmpty(sources)) {
			return null;
		}
		Number ret = 0;
		for (T item : sources) {
			if (item == null) {
				continue;
			}
			Class<?> clazz = item.getClass();
			if (clazz == int.class || clazz == Integer.class) {
				ret = ret.intValue() + (Integer) item;
			}
			if (clazz == long.class || clazz == Long.class) {
				ret = ret.longValue() + (Long) item;
			}
			if (clazz == double.class || clazz == Double.class) {
				ret = ret.doubleValue() + (Double) item;
			}
			if (clazz == float.class || clazz == Float.class) {
				ret = ret.floatValue() + (Float) item;
			}
		}
		return (T) ret;
	}

	/***
	 * 
	 * @Description: 将集合 按 partitionCount大小分区，
	 *               <p>
	 *               比如有集合 ： [1,2,3,4,5] 参数partitionCount=2 ， 返回结果:[[1, 2],[3,
	 *               4],[5]]
	 *               </p>
	 *
	 * @param datas
	 * @param partitionCount
	 * @return
	 * @return List<List<T>>
	 * @throws
	 *
	 * HadLuo(罗政)         2022年3月30日 下午4:58:40
	 */
	public static <T> List<List<T>> partition(List<T> datas, int partitionCount) {
		List<List<T>> results = new ArrayList<>();
		if (CollectionUtils.isEmpty(datas) || datas.size() <= partitionCount) {
			results.add(datas);
			return results;
		}
		while (true) {
			List<T> segment = new ArrayList<>();
			for (T data : datas) {
				segment.add(data);
				if (segment.size() == partitionCount) {
					// 切换分区
					results.add(segment);
					segment = new ArrayList<>();
					continue;
				}
			}
			if (!CollectionUtils.isEmpty(segment)) {
				results.add(segment);
			}
			break;
		}
		return results;
	}

	/***
	 * 将 集合 List<R> 转成 List<T> 集合
	 * 
	 * @param source
	 * @param convert
	 * @return
	 */
	public static <R, O> List<O> convert(List<R> source, SelectOne<R, O> convert) {
		if (CollectionUtils.isEmpty(source)) {
			return newArrayList();
		}
		List<O> results = newArrayList();
		for (R r : source) {
			results.add(convert.select(r));
		}
		return results;
	}

	/***
	 * 随机 集合的 一个元素
	 * 
	 * @param resource
	 * @return
	 */
	public static <T> T randomOne(List<T> resource) {
		if (CollectionUtils.isEmpty(resource)) {
			return null;
		}
		if (resource.size() == 1) {
			return resource.get(0);
		}
		return resource.get(new Random().nextInt(resource.size()));
	}

	/***
	 * 随机集合 中 指定 个数 的元素
	 * 
	 * @param resource
	 * @param number
	 * @return
	 */
	public static <T> List<T> random(List<T> resource, int number) {
		if (CollectionUtils.isEmpty(resource)) {
			return Collections.emptyList();
		}
		if (number >= resource.size()) {
			return resource;
		}
		final List<T> resourceTemp = new ArrayList<>(resource);
		final List<T> results = new ArrayList<>();
		T current = null;
		while (results.size() < number) {
			// random
			current = randomOne(resourceTemp);
			results.add(current);
			resourceTemp.remove(current);
		}
		return results;
	}

	/***
	 * 随机集合 中 指定 个数 的元素 , 并且从元集合中移除
	 * 
	 * @param resource
	 * @param number
	 * @return
	 */
	public static <T> List<T> randomAndRemove(List<T> resource, int number) {
		if (CollectionUtils.isEmpty(resource)) {
			return Collections.emptyList();
		}
		final List<T> results = new ArrayList<>();

		if (number >= resource.size()) {
			results.addAll(resource);
			resource.clear();
			return results;
		}
		T current = null;
		while (results.size() < number) {
			// random
			current = randomOne(resource);
			results.add(current);
			resource.remove(current);
		}
		return results;
	}

	public static List<Integer> range(int start, int end) {
		if (end <= 0) {
			return new ArrayList<>();
		}
		List<Integer> list = new ArrayList<>();
		if (start >= end) {
			list.add(start);
		}
		for (int i = start; i < end; i++) {
			list.add(i);
		}
		return list;
	}

	/***
	 * 移除list的左边的前N个元素
	 * 
	 * @param   <T>
	 * 
	 * @param n
	 */
	public static <T> void removeLeft(List<T> data, int n) {
		if (CollectionUtils.isEmpty(data)) {
			return;
		}
		if (n <= 0) {
			return;
		}
		if (data.size() <= n) {
			data.clear();
		}
		int k = data.size();
		for (int i = 0; i < k - n; i++) {
			data.remove(0);
		}
	}

	public static interface Key<T> {
		public String key(T t);
	}

	@Data
	public static class PollWrapper<T> {
		T obj;
		int weight;

		public PollWrapper(T obj, int weight) {
			this.obj = obj;
			this.weight = weight;
		}
	}

}
