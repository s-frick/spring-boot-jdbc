package com.example.demo.books.web;

import com.example.demo.authors.domain.Author;
import com.example.demo.books.domain.Book;
import com.example.demo.books.domain.BookID;

record BookTO(String id, String title, String author, String isbn) implements BookResponse {
  static BookTO of(Book book) {
    return new BookTO(book.id().value(), book.title(), book.author().name(), book.isbn());
  }

  Book toDomain() {
    return new Book(
        new BookID(id),
        this.title(),
        new Author(null, author),
        isbn);
  }
}
