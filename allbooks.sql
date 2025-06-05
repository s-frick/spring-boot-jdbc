SELECT books.title,books.isbn,authors.name,authors.id FROM books LEFT JOIN authors ON books.author_id = authors.id;
