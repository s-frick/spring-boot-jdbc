package com.example.demo.authors.db;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.example.demo.authors.domain.Author;
import com.example.demo.authors.domain.AuthorID;
import com.example.demo.authors.domain.AuthorService;

@Component
public class AuthorRepository implements AuthorService {

  private final JdbcClient client;
  private final AuthorIDFactory idFactory;

  public AuthorRepository(AuthorIDFactory idFactory, JdbcClient client) {
    this.idFactory = idFactory;
    this.client = client;
  }

  @Override
  public Optional<Author> findById(AuthorID id) {
    return client.sql("""
        SELECT id, name
        FROM authors
        WHERE id = :id
        """)
        .param("id", id.value())
        .query(Author.class)
        .optional();
  }

  @Override
  public Optional<Author> findByName(String name) {
    return client.sql("""
        SELECT id, name
        FROM authors
        WHERE name = :name
        """)
        .param("name", name)
        .query(Author.class)
        .optional();
  }

  @Override
  public Author save(Author author) {
    AuthorID id = null;
    if (author.id() == null || author.id().value() == null) {
      id = findByName(author.name())
          .map(Author::id)
          .orElse(idFactory.generate(author));
    } else {
      id = author.id();
    }

    client.sql("""
        INSERT INTO authors (id, name)
        VALUES (:id, :name)
        ON CONFLICT (name) DO NOTHING
        """)
        .param("id", id.value())
        .param("name", author.name())
        .update();

    return new Author(id, author.name());
  }

  @Override
  public void deleteById(AuthorID id) {
    client.sql("""
        DELETE FROM authors
        WHERE id = :id
        """)
        .param("id", id.value())
        .update();
  }
}
