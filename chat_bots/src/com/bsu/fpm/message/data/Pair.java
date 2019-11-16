package com.bsu.fpm.message.data;

public class Pair<K,V> {

	private final K key;
	
	private final V value;
	
	public Pair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
}
