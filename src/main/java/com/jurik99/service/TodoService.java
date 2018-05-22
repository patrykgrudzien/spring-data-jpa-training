package com.jurik99.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jurik99.jpa.Todo;
import com.jurik99.repository.TodoBASERepository;
import com.jurik99.repository.TodoCRUDRepository;
import com.jurik99.repository.TodoRAWRepository;

import java.util.List;

@Service
public class TodoService {

	private TodoCRUDRepository todoCRUDRepository;
	private TodoBASERepository todoBASERepository;
	private TodoRAWRepository todoRAWRepository;

	@Autowired
	public TodoService(final TodoCRUDRepository todoCRUDRepository,
	                   final TodoBASERepository todoBASERepository,
	                   final TodoRAWRepository todoRAWRepository) {
		this.todoCRUDRepository = todoCRUDRepository;
		this.todoBASERepository = todoBASERepository;
		this.todoRAWRepository = todoRAWRepository;
	}

	public List<Todo> findAllTodosTest() {

		final Iterable<Todo> allCRUD = todoCRUDRepository.findAll();
		final List<Todo> allBASE = todoBASERepository.findAll();
		final List<Todo> allRAW = todoRAWRepository.findAll();
		
		return allRAW;
	}
}
