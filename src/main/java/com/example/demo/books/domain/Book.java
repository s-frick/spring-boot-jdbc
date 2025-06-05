package com.example.demo.books.domain;

import com.example.demo.authors.domain.Author;

public record Book(BookID id, String title, Author author, String isbn) {
}
