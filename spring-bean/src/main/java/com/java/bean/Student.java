package com.java.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Student {
	private Map<Object, Object> map;
	private List<Object> list;
	private Set<Object> set;
	private Object[] array;
	


	public Map<Object, Object> getMap() {
		return map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public Set<Object> getSet() {
		return set;
	}

	public void setSet(Set<Object> set) {
		this.set = set;
	}

	public Object[] getArray() {
		return array;
	}

	public void setArray(Object[] array) {
		this.array = array;
	}

}
