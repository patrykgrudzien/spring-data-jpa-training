package com.jurik99.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.jurik99.jpa.Todo;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * This repository provides ONLY one method listed below:
 * {@link TodoRAWRepository#findAll()}
 */
public interface TodoRAWRepository extends Repository<Todo, Long> {

	List<Todo> findAll();

	/**
	 * Query methods are methods that find information from the database and are declared on the repository interface. For
	 * example, if we want to create a database query that finds the "Todo" object that has a specific id, we can create the
	 * query method by adding the "findById()" method to the {@link TodoRAWRepository} interface.
	 */
	Todo findById(Long id);


	/**
	 * 1) Here are some examples of query methods that return only ONE result
	 */
	@Query("SELECT t.title FROM Todo t WHERE t.id = :id")
	String findTitleById(@Param("id") final Long id);

	@Query("SELECT t.description FROM Todo t WHERE t.id = :id")
	Optional<String> findDescriptionById(@Param("id") final Long id);

	 Optional<String> getHeaderById(Long id);


	/**
	 * 2) If we are writing  query method that should return more than one result, we can return "List" or "Stream"
 	 */
	List<Todo> findByTitle(final String title);
	Stream<Todo> findByHeader(final String header);


	/**
	 * 3) If we want that our query is executed asynchronously, we have to annotate it with the @Async annotation and return a
	 * "Future<T>" object.
	 */
	@Async
	@Query("SELECT t.header FROM Todo t WHERE t.id = :id")
	Future<String> findHeaderById(@Param("id") final Long id);


	/**
	 * 4) Passing Method Parameters to Query Methods
	 * In the example below, please pay attention to "nativeQuery=true" parameter.
	 * We use here exact (table name) and (columns names) as specified in Entity itself ->
	 * @Table(name = "todos") !!!
	 */
	@Query(value = "SELECT * FROM todos t where t.title = :title AND t.description = :description", nativeQuery = true)
	Optional<Todo> findByTitleAndDescription(@Param("title") final String title,
	                                         @Param("description") final String description);
}