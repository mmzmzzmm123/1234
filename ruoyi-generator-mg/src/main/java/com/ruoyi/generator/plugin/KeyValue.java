package com.ruoyi.generator.plugin;

public class KeyValue<K, V> {

	private K key;

	private V value;

	public static <K, V> KeyValue<K, V> create(K key, V value) {
		return new KeyValue<>(key, value);
	}

	public KeyValue(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	};

}
