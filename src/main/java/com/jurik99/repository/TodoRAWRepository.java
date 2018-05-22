package com.jurik99.repository;

import org.springframework.data.repository.Repository;

import com.jurik99.jpa.Todo;

import java.util.List;

/**
 * This repository provides ONLY one method listed below:
 * {@link TodoRAWRepository#findAll()}
 */
public interface TodoRAWRepository extends Repository<Todo, Long> {

	List<Todo> findAll();
}
