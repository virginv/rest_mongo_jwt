package com.base.mongo.services;

import java.util.List;


public interface IGlobalService<T> {
	
	List<T> findAll();
	T save(T t);
	T update(T t);
	T delete(T t);
	T findById(String id);
	
}
