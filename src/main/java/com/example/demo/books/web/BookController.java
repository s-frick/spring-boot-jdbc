package com.example.demo.books.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Pageable;
import com.example.demo.books.domain.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {
  private static final Logger log = LoggerFactory.getLogger(BookController.class);

  private final BookRepository bookrepo;

  public BookController(BookRepository bookService) {
    this.bookrepo = bookService;
  }

  private static <T> T log(T t) {
    log.debug("Log: {}", t);
    return t;
  }

  @GetMapping("/{id}")
  public ResponseEntity<? extends BookResponse> findById(@PathVariable String id) {
    return bookrepo.findBookById(id)
        .map(BookTO::of)
        .map(ResponseEntity::ok)
        .map(BookController::log)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("")
  public List<BookTO> findAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return bookrepo.findAll(new Pageable(0, 10)).stream().map(BookTO::of).toList();
  }

  @PostMapping
  public BookTO save(@RequestBody BookTO book) {
    return BookTO.of(
        bookrepo.save(book.toDomain()));
  }

  @DeleteMapping
  public void delete(String id) {
    bookrepo.deleteBook(id);
  }

}
