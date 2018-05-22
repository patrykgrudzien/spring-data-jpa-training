package com.jurik99.repository;

import org.springframework.data.repository.CrudRepository;

import com.jurik99.jpa.Todo;

public interface TodoCRUDRepository extends CrudRepository<Todo, Long> {}
