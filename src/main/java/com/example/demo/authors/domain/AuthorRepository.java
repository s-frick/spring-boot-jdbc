package com.example.demo.authors.domain;

import java.util.Optional;

public interface AuthorRepository {
  /**
   * Finds an author by their ID.
   *
   * @param id the ID of the author
   * @return the author if found, otherwise null
   */
  Optional<Author> findById(AuthorID id);

  /**
   * Finds an author by their name.
   *
   * @param name the name of the author
   * @return the author if found, otherwise null
   */
  Optional<Author> findByName(String name);

  /**
   * Saves a new author.
   *
   * @param author the author to save
   * @return the saved author
   */
  Author save(Author author);

  /**
   * Deletes an author by their ID.
   *
   * @param id the ID of the author to delete
   */
  void deleteById(AuthorID id);
}
