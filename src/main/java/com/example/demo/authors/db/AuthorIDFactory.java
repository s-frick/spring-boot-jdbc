package com.example.demo.authors.db;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.IDGenerator;
import com.example.demo.authors.domain.Author;
import com.example.demo.authors.domain.AuthorID;

@Component
public class AuthorIDFactory implements IDGenerator<AuthorID, Author> {
  @Override
  public AuthorID generate(Author s) {
    return new AuthorID(UUID.randomUUID().toString());
  }
}
