package com.jurik99.repository;

import com.jurik99.jpa.Todo;

/**
 * This repository provides CRUD operations as well as {@link com.jurik99.repository.TodoCRUDRepository}.
 */
public interface TodoBASERepository extends BaseRepository<Todo, Long> {}
