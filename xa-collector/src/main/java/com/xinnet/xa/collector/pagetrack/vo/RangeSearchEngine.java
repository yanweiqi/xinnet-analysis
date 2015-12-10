package com.xinnet.xa.collector.pagetrack.vo;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RangeSearchEngine<T extends Comparable, V> {
	protected  TreeMap<T, RangeRow<T, V>> cache = new TreeMap<T, RangeRow<T, V>>();

	@SuppressWarnings("unchecked")
	public V getValue(T key) {
		//
		RangeRow<T, V> row = getRow(key);
		if (row == null) {
			return null;
		}

		//
		T end = row.getEnd();
		if (end == null) {
			return null;
		}
		if (end.compareTo(key) > 0) {
			return row.getValue();
		}

		return null;
	}

	public RangeRow<T, V> getRow(T key) {
		if (key == null) {
			return null;
		}
		//
		Entry<T, RangeRow<T, V>> entry = cache.floorEntry(key);
		if (entry == null || entry.getValue() == null) {
			return null;
		}
		//
		return entry.getValue();
	}

	public void putAll(Map<T, RangeRow<T, V>> map) {
		cache.putAll(map);
	}

	public RangeRow<T, V> put(RangeRow<T, V> row) {
		return cache.put(row.getBegin(), row);
	}

	public RangeRow<T, V> remove(T key) {
		return cache.remove(key);
	}

	public void clear() {
		cache.clear();
	}

	public TreeMap<T, RangeRow<T, V>> getCache() {
		return cache;
	}

	public TreeMap<T, RangeRow<T, V>> setCache(TreeMap<T, RangeRow<T, V>> newCache) {
		if (newCache == null) {
			throw new NullPointerException();
		}
		//
		TreeMap<T, RangeRow<T, V>> before = cache;
		cache = newCache;
		//
		return before;
	}

}
