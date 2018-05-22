package com.jurik99.repository;

import org.springframework.data.repository.Repository;

import com.jurik99.jpa.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepositoryQueryGenerationMethodNameStrategy extends Repository<Todo, Long> {

	// -------------------------------------- Creating Query Methods -------------------------------------- //

	/**
	 * Returns the found todo entry by using its id as search criteria.
	 * If no todo entry is found, this method returns null.
	 */
	Todo findById(final Long id);

	/**
	 * Returns an Optional which contains the found todo entry by using its id as search criteria.
	 * If no todo entry is found, this method returns an "empty Optional"
	 */
	Optional<Todo> getById(final Long id);

	/**
	 * Returns the found todo entry whose title or description is given as a method parameter.
	 * If no todo entry is found, this method returns an empty list.
	 */
	List<Todo> findByTitleOrDescription(final String title, final String description);

	/**
	 * Returns the number of todo entry whose title is given as a method parameter.
	 */
	long countByTitle(final String title);

	/**
	 * Returns the distinct todo entries whose title is given as a method parameter.
	 * If no todo entries is found, this method returns an empty list.
	 */
	List<Todo> findDistinctByTitle(final String title);

	/**
	 * Returns the first 3 todo entries whose title is given as a method parameter.
	 * If no todo entries is found, this method returns an empty list.
	 */
	List<Todo> findFirst3ByTitleOrderByTitleAsc(final String title);

	/**
	 * Returns the first 3 todo entries whose title is given as a method parameter.
	 * If no todo entries is found, this method returns an empty list.
	 */
	List<Todo> findTop3ByTitleOrderByTitleAsc(final String title);


	// -------------------------------------- Implementing the Search Function -------------------------------------- //
	List<Todo> findByDescriptionContainsOrTitleContainsAllIgnoreCase(final String descriptionPart, final String titlePart);
}
