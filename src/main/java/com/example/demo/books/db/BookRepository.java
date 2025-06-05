package com.example.demo.books.db;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.example.demo.books.domain.BookService;
import com.example.demo.Pageable;
import com.example.demo.authors.domain.AuthorService;
import com.example.demo.IDGenerator;
import com.example.demo.books.domain.Book;
import com.example.demo.books.domain.BookID;

@Component
public class BookRepository implements BookService {
  private static final Logger log = LoggerFactory.getLogger(BookRepository.class);

  private final JdbcClient client;
  private final IDGenerator<BookID, Book> idGenerator;
  private final AuthorService authorService;

  public BookRepository(AuthorService authorService, JdbcClient client, IDGenerator<BookID, Book> idGenerator) {
    this.authorService = authorService;
    this.client = client;
    this.idGenerator = idGenerator;
  }

  @Override
  public List<Book> findAll(Pageable page) {
    return client.sql("""
        SELECT id, title, author, isbn
        FROM books
        ORDER BY id
        LIMIT :limit OFFSET :offset
        """)
        .param("limit", page.size())
        .param("offset", page.page() * page.size())
        .query(Book.class)
        .list();
  }

  @Override
  public Optional<Book> findBookById(String id) {
    return client.sql("""
        SELECT id, title, author, isbn
        FROM books
        WHERE id = :id
        """)
        .param("id", id)
        .query(Book.class)
        .optional();
  }

  @Override
  public Book save(Book book) {
    var author = authorService.save(book.author());

    BookID key = idGenerator.generate(book);
    client.sql("""
        INSERT INTO books (id, title, isbn, author_id)
        VALUES (:id, :title, :isbn, :author_id)
        """)
        .param("id", key.value())
        .param("title", book.title())
        .param("author_id", author.id().value())
        .param("isbn", book.isbn())
        .update();

    return new Book(
        key,
        book.title(),
        author,
        book.isbn());
  }

  @Override
  public void deleteBook(String id) {
    client.sql("""
        DELETE FROM books
        WHERE id = :id
        """)
        .param("id", id)
        .update();
  }

}
