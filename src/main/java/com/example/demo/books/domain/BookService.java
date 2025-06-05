package com.example.demo.books.domain;

import java.util.List;
import java.util.Optional;

import com.example.demo.Pageable;

public interface BookService {

  List<Book> findAll(Pageable page);

  Optional<Book> findBookById(String id);

  Book save(Book book);

  void deleteBook(String id);

}
