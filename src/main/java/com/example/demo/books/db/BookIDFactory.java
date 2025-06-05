package com.example.demo.books.db;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.IDGenerator;
import com.example.demo.books.domain.Book;
import com.example.demo.books.domain.BookID;

@Component
public class BookIDFactory implements IDGenerator<BookID, Book> {

  @Override
  public BookID generate(Book s) {
    return new BookID(UUID.randomUUID().toString());
  }

}
